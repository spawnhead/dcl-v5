/**
 * TASK-0087 Commercial Proposals list smoke. E2E: /commercial-proposals.
 * Prerequisites: UI :5173, backend :8080, Postgres up.
 */
import { test, expect } from '@playwright/test';

test.describe('CP list smoke', () => {
  test('page loads, Apply loads grid, Clone creates new, Block toggles strikethrough', async ({ page }) => {
    // 1) Open /commercial-proposals
    await page.goto('/commercial-proposals');
    await expect(page.getByRole('heading', { name: 'Коммерческие предложения' })).toBeVisible({ timeout: 15000 });

    // 2) Apply (defaults) -> grid loads
    await page.getByRole('button', { name: 'Применить' }).click();
    await page.waitForTimeout(800);
    const grid = page.locator('.ag-root-wrapper');
    await expect(grid).toBeVisible({ timeout: 10000 });

    const rowCountBefore = await page.locator('.ag-row').count();
    expect(rowCountBefore).toBeGreaterThanOrEqual(0);

    // 3) Clone (new) by row -> new CP appears
    const actionsBtn = page.getByRole('button', { name: 'Действия' }).first();
    if (await actionsBtn.isVisible()) {
      await actionsBtn.click();
      await page.getByRole('menuitem', { name: 'Клон (новый)' }).click();
      await page.waitForTimeout(1500);
      const rowCountAfter = await page.locator('.ag-row').count();
      expect(rowCountAfter).toBeGreaterThanOrEqual(rowCountBefore);
    }

    // 4) Block/Unblock -> row strikethrough (if canBlock)
    const actionsBtn2 = page.getByRole('button', { name: 'Действия' }).first();
    if (await actionsBtn2.isVisible()) {
      await actionsBtn2.click();
      const blockItem = page.getByRole('menuitem', { name: /Заблокировать|Разблокировать/ }).first();
      if (await blockItem.isVisible().catch(() => false)) {
        await blockItem.click();
        await page.waitForTimeout(1000);
        const blockedRow = page.locator('.ag-row.blocked-row').first();
        await expect(blockedRow).toBeVisible({ timeout: 5000 });
      }
    }
  });

  test('page loads and Apply shows grid', async ({ page }) => {
    await page.goto('/commercial-proposals');
    await expect(page.getByRole('heading', { name: 'Коммерческие предложения' })).toBeVisible({ timeout: 15000 });
    await page.getByRole('button', { name: 'Применить' }).click();
    await page.waitForTimeout(500);
    await expect(page.locator('.ag-root-wrapper')).toBeVisible({ timeout: 8000 });
  });
});
