export interface DevStatusResponse {
  appName: string;
  version: string;
  activeProfiles: string[];
  javaVersion: string;
  db: {
    ok: boolean;
    url?: string;
    now?: string;
    error?: string;
  };
  flyway: {
    ok: boolean;
    migrationsAppliedCount?: number;
    currentVersion?: string;
    error?: string;
  };
  dataMode: 'FAKE_SEEDED' | 'REAL' | 'EMPTY';
  authMode: string;
}

export interface CurrentUserResponse {
  id: string;
  username: string;
  displayName: string;
  roles: string[];
}
