export interface User {
  id?: string;
  username?: string;
  roles?: ('SYSTEM_ADMIN' | 'MANAGER' | 'VIEWER')[];
  credentialsNonExpired?: boolean;
  accountNonExpired?: boolean;
  accountNonLocked?: boolean;
  enabled?: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface Tariff {
  id?: string;
  name?: string;
  cpuPrice?: number;
  memoryPrice?: number;
  callPrice?: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: User;
}
