import type { CSSProperties, ReactNode } from 'react';
import { AgGridTheme } from './AgGridTheme';
import '../../ag-grid-dark-override.css';

const DEFAULT_CLASS = 'app-grid';

type AgGridShellProps = {
  children: ReactNode;
  className?: string;
  style?: CSSProperties;
};

/**
 * Standard AG Grid wrapper for list screens.
 * Applies theme (ag-theme-quartz / ag-theme-quartz-dark) from ThemeContext
 * and app-grid class for consistent styling.
 * Use this for all grid-based list screens - do not use raw AgGridReact without it.
 */
export function AgGridShell({ children, className, style }: AgGridShellProps) {
  const combinedClass = className ? `${DEFAULT_CLASS} ${className}` : DEFAULT_CLASS;
  return (
    <AgGridTheme className={combinedClass} style={style}>
      {children}
    </AgGridTheme>
  );
}
