/**
 * TASK-0087 Orders smoke. E2E: /orders -> new -> fill -> save -> list -> edit.
 * Prerequisites: UI :5173, backend :8080, Postgres up.
 */
import { test, expect } from '@playwright/test';

test.describe('Orders smoke', () => {
  test('list loads and new button navigates to /orders/new', async ({ page }) => {
    await page.goto('/orders');
    await expect(page.getByRole('heading', { name: 'Заказы' })).toBeVisible({ timeout: 15000 });
    await page.getByRole('button', { name: 'Новый заказ' }).click();
    await expect(page).toHaveURL(/\/orders\/new/);
    await expect(page.getByRole('heading', { name: 'Новый заказ' })).toBeVisible({ timeout: 15000 });
  });

  test('full flow: new -> fill -> save -> list -> edit', async ({ page }) => {
    // 1) Open /orders/new
    await page.goto('/orders/new');
    await expect(page.getByRole('heading', { name: 'Новый заказ' })).toBeVisible({ timeout: 15000 });

    // 2) Wait for lookups (form visible)
    await expect(page.getByText('Основные данные')).toBeVisible({ timeout: 10000 });

    // 3) Fill required: number (max 15 chars), contractor, seller, currency (date has default)
    const ordNum = `SMK-${Date.now().toString().slice(-8)}`;
    await page.getByPlaceholder('Номер заказа').fill(ordNum);
    await page.locator('#contractorId').click();
    await page.keyboard.press('ArrowDown');
    await page.keyboard.press('ArrowDown');
    await page.keyboard.press('Enter');
    await page.locator('#sellerForWhoId').click();
    await page.keyboard.press('ArrowDown');
    await page.keyboard.press('Enter');
    await page.locator('#currencyId').click();
    await page.keyboard.press('ArrowDown');
    await page.keyboard.press('Enter');

    // 4) Add produce row
    await page.getByRole('button', { name: 'Добавить позицию' }).click();
    const produceInput = page.locator('.ant-card').filter({ hasText: 'Позиции' }).locator('input').first();
    await produceInput.fill('Smoke produce');

    // 5) Payments: add row
    await page.getByRole('button', { name: 'Добавить' }).first().click();

    // 6) PaySums: add row
    await page.getByRole('button', { name: 'Добавить' }).last().click();

    // 7) Save -> redirect to /orders
    await page.getByRole('button', { name: 'Сохранить' }).click();
    await expect(page).toHaveURL(/\/orders$/, { timeout: 20000 });

    // 8) List: apply filter and capture response for ord_id (exclude lookups: /api/orders? not /api/orders/lookups)
    const listResponse = page.waitForResponse(
      (r) => {
        const u = r.url();
        return u.includes('/api/orders') && !u.includes('/api/orders/lookups') && u.includes('page=') && r.request().method() === 'GET' && r.status() === 200;
      },
      { timeout: 10000 }
    );
    await page.getByRole('button', { name: 'Применить фильтр' }).click();
    const resp = await listResponse;
    const json = await resp.json();
    const items = json?.items ?? [];
    expect(items.length).toBeGreaterThan(0);

    const createdOrder = items.find((r: { ord_number: string }) => r.ord_number === ordNum);
    expect(createdOrder, `Order with number ${ordNum} should be in list`).toBeDefined();
    const ordId = createdOrder!.ord_id;

    // 9) Open edit
    await page.goto(`/orders/${ordId}/edit`);
    await expect(page).toHaveURL(new RegExp(`/orders/${ordId}/edit`));
    await expect(page.getByText('Основные данные')).toBeVisible({ timeout: 10000 });
    await expect(page.getByPlaceholder('Номер заказа')).toHaveValue(ordNum);
  });
});
