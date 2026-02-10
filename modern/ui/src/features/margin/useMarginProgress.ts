/**
 * Real progress state for Margin screen. No fake timers — only request/event-driven updates.
 */
import { useCallback, useState } from 'react';

export type StepStatus = 'pending' | 'active' | 'done' | 'error';

export interface ProgressStep {
  id: string;
  label: string;
  status: StepStatus;
}

export type LoadingPhase = 'idle' | 'initial' | 'generate' | 'export';

export interface MarginProgressState {
  loadingPhase: LoadingPhase;
  steps: ProgressStep[];
  progressPct: number | undefined;
  details: string | undefined;
  error: string | undefined;
}

const INITIAL_STEPS: ProgressStep[] = [
  { id: 'users', label: 'Пользователи', status: 'pending' },
  { id: 'departments', label: 'Отделы', status: 'pending' },
  { id: 'contractors', label: 'Контрагенты', status: 'pending' },
  { id: 'stuff', label: 'Категории', status: 'pending' },
  { id: 'routes', label: 'Маршруты', status: 'pending' },
];

const GENERATE_STEPS: ProgressStep[] = [
  { id: 'request', label: 'Отправка запроса', status: 'pending' },
  { id: 'response', label: 'Получение данных', status: 'pending' },
  { id: 'render', label: 'Отрисовка таблицы', status: 'pending' },
];

const EXPORT_STEPS: ProgressStep[] = [
  { id: 'request', label: 'Запрос отправлен', status: 'pending' },
  { id: 'download', label: 'Получение файла', status: 'pending' },
  { id: 'save', label: 'Сохранение', status: 'pending' },
];

export function useMarginProgress() {
  const [state, setState] = useState<MarginProgressState>({
    loadingPhase: 'idle',
    steps: [],
    progressPct: undefined,
    details: undefined,
    error: undefined,
  });

  const setStepStatus = useCallback((stepId: string, status: StepStatus) => {
    setState((prev) => ({
      ...prev,
      steps: prev.steps.map((s) => (s.id === stepId ? { ...s, status } : s)),
    }));
  }, []);

  const setProgressPct = useCallback((pct: number | undefined) => {
    setState((prev) => ({ ...prev, progressPct: pct }));
  }, []);

  const setDetails = useCallback((details: string | undefined) => {
    setState((prev) => ({ ...prev, details }));
  }, []);

  const startInitialLoadProgress = useCallback(() => {
    setState({
      loadingPhase: 'initial',
      steps: INITIAL_STEPS.map((s) => ({ ...s, status: 'pending' as StepStatus })),
      progressPct: undefined,
      details: 'Загрузка справочников…',
      error: undefined,
    });
  }, []);

  const startGenerateProgress = useCallback(() => {
    setState({
      loadingPhase: 'generate',
      steps: GENERATE_STEPS.map((s) => ({ ...s, status: 'pending' as StepStatus })),
      progressPct: undefined,
      details: 'Загрузка данных…',
      error: undefined,
    });
  }, []);

  const startExportProgress = useCallback(() => {
    setState({
      loadingPhase: 'export',
      steps: EXPORT_STEPS.map((s) => ({ ...s, status: 'pending' as StepStatus })),
      progressPct: undefined,
      details: 'Экспорт…',
      error: undefined,
    });
  }, []);

  const finishProgress = useCallback(() => {
    setState((prev) => ({
      ...prev,
      loadingPhase: 'idle',
      details: undefined,
      progressPct: undefined,
      error: undefined,
    }));
  }, []);

  const failProgress = useCallback((err: string) => {
    setState((prev) => ({
      ...prev,
      loadingPhase: 'idle',
      error: err,
      details: undefined,
      progressPct: undefined,
    }));
  }, []);

  const dismissError = useCallback(() => {
    setState((prev) => ({ ...prev, error: undefined }));
  }, []);

  return {
    ...state,
    setStepStatus,
    setProgressPct,
    setDetails,
    startInitialLoadProgress,
    startGenerateProgress,
    startExportProgress,
    finishProgress,
    failProgress,
    dismissError,
  };
}
