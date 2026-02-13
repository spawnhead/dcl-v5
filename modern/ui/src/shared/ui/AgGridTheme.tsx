import type { CSSProperties, ReactNode } from 'react';
import { useTheme } from '../context/ThemeContext';

const LIGHT_THEME = 'ag-theme-quartz';
const DARK_THEME = 'ag-theme-quartz-dark';

type AgGridThemeProps = {
  children: ReactNode;
  className?: string;
  style?: CSSProperties;
};

/**
 * Wrapper that applies AG Grid theme class based on global Light/Dark toggle.
 * Uses ag-theme-quartz (light) / ag-theme-quartz-dark (dark).
 */
export function AgGridTheme({ children, className, style }: AgGridThemeProps) {
  const { themeMode } = useTheme();
  const themeClass = themeMode === 'dark' ? DARK_THEME : LIGHT_THEME;
  const combinedClass = className ? `${themeClass} ${className}` : themeClass;

  return (
    <div className={combinedClass} style={style}>
      {children}
    </div>
  );
}
