/**
 * API error normalization: 400 validation, 500, network errors.
 * Returns user-friendly message for showError().
 */
export interface NormalizedError {
  message: string;
  code?: string;
  fields?: Array<{ name: string; message: string }>;
  /** Tab key to switch to (e.g. contractor edit validation). */
  activeTab?: string;
}

export async function normalizeApiError(res: Response, json: unknown): Promise<NormalizedError> {
  if (res.status === 400) {
    const body = json as {
      error?: { code?: string; field?: string; message?: string; fields?: Array<{ name: string; message: string }> };
      errors?: Array<{ field: string; message: string }>;
      activeTab?: string;
    };
    const err = body?.error;
    const errorsArr = body?.errors;
    const fields = err?.fields ?? (errorsArr?.map((e) => ({ name: e.field, message: e.message })));
    if (fields && fields.length > 0) {
      const first = fields[0];
      return {
        message: `${first.name}: ${first.message}`,
        code: err?.code ?? 'VALIDATION_ERROR',
        fields,
        activeTab: body?.activeTab,
      };
    }
    if (err?.field != null && err?.message != null) {
      return { message: `${err.field}: ${err.message}`, code: err?.code, activeTab: body?.activeTab };
    }
    return { message: 'Ошибка валидации', code: err?.code, activeTab: body?.activeTab };
  }
  if (res.status >= 500) {
    const body = json as { error?: string; message?: string };
    return { message: body?.error ?? body?.message ?? `Ошибка сервера (${res.status})` };
  }
  return { message: `Ошибка ${res.status}` };
}

export async function fetchWithErrorHandling<T>(
  url: string,
  options: RequestInit
): Promise<{ ok: boolean; data?: T; error?: NormalizedError }> {
  try {
    const res = await fetch(url, options);
    const json = await res.json().catch(() => ({}));
    if (!res.ok) {
      const err = await normalizeApiError(res, json);
      return { ok: false, error: err };
    }
    return { ok: true, data: json as T };
  } catch (e) {
    const msg = e instanceof Error ? e.message : 'Сетевая ошибка';
    return { ok: false, error: { message: msg } };
  }
}
