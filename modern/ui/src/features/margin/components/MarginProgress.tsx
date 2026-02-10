/**
 * Real progress UI for Margin: steps + optional percent bar. No fake timers.
 */
import { Alert, Progress, Space, Spin, Steps, Typography } from 'antd';
import type { MarginProgressState, StepStatus } from '../useMarginProgress';

interface MarginProgressProps extends MarginProgressState {
  onDismissError?: () => void;
}

const stepStatusMap: Record<StepStatus, 'wait' | 'process' | 'finish' | 'error'> = {
  pending: 'wait',
  active: 'process',
  done: 'finish',
  error: 'error',
};

export default function MarginProgressUI(props: MarginProgressProps) {
  const { loadingPhase, steps, progressPct, details, error, onDismissError } = props;

  if (loadingPhase === 'idle' && !error) return null;

  if (error) {
    return (
      <Alert
        type="error"
        message="Ошибка"
        description={error}
        showIcon
        closable
        onClose={onDismissError}
        style={{ marginBottom: 16 }}
      />
    );
  }

  const currentStep = steps.findIndex((s) => s.status === 'active');
  const stepItems = steps.map((s) => ({
    key: s.id,
    title: s.label,
    status: stepStatusMap[s.status],
  }));

  return (
    <div
      style={{
        marginBottom: 16,
        padding: 16,
        background: '#fafafa',
        borderRadius: 8,
        border: '1px solid #f0f0f0',
      }}
    >
      <Spin spinning={loadingPhase !== 'idle'} size="small">
        <Space direction="vertical" size="small" style={{ width: '100%' }}>
          {details && <Typography.Text type="secondary">{details}</Typography.Text>}
          <Steps size="small" current={currentStep >= 0 ? currentStep : steps.length} items={stepItems} />
          {progressPct != null && (
            <Progress percent={progressPct} size="small" status="active" style={{ maxWidth: 400 }} />
          )}
        </Space>
      </Spin>
    </div>
  );
}
