/** DEV_DASHBOARD_SPEC: /api/dev/status contract */
export interface DevStatusResponse {
  profile: string;
  javaVersion: string;
  serverTime: string;
  db: {
    ok: boolean;
    product?: string;
    version?: string;
    url?: string;
    error?: string;
  };
  flyway: {
    ok: boolean;
    appliedMigrationsCount?: number;
    error?: string;
  };
  dataMode?: 'FAKE_SEEDED' | 'REAL' | 'EMPTY';
  authMode?: string;
  seedDataset?: string;
}

/** DEV_BYPASS: /api/me contract */
export interface CurrentUserResponse {
  id: string;
  name: string;
  roles: string[];
  department: { id: string; name: string };
  chiefDepartment: boolean;
  authMode: string;
}
