/**
 * Screen-level loading: Skeleton or Spin (preloader) until data is ready.
 * Use when loading open/initial data.
 */
import { Skeleton, Spin } from 'antd';

interface ScreenLoaderProps {
  loading: boolean;
  children: React.ReactNode;
  /** Number of skeleton rows for form layout. Default 8. */
  rows?: number;
  /** 'skeleton' (default) or 'spin' (preloader). */
  variant?: 'skeleton' | 'spin';
}

export function ScreenLoader({ loading, children, rows = 8, variant = 'skeleton' }: ScreenLoaderProps) {
  if (!loading) return <>{children}</>;
  if (variant === 'spin') {
    return (
      <div style={{ minHeight: 280, padding: 16 }}>
        <Spin size="large" tip="Загрузка..." spinning>
          <div style={{ minHeight: 200 }} />
        </Spin>
      </div>
    );
  }
  return (
    <div style={{ padding: 16 }}>
      <Skeleton active paragraph={{ rows }} />
    </div>
  );
}
