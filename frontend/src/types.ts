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

export interface BillingFunction {
  id?: string;
  name?: string;
  updatedAt?: string;
  createdAt?: string;
}

export interface BillingFunctionDetails {
  billingFrom: string;
  billingTo: string;
  function: BillingFunction;
  metricsRecordsCount: number;
  stepPeriod: string;
  steps: {
    [key: string]: {
      metricsRecordsCount: number;
      stepPeriod: string;
      stepTime: string;
      totalCallCount: number;
      totalCallPrice: number;
      totalCpuAmount: number;
      totalCpuPrice: number;
      totalMemoryAmount: number;
      totalMemoryPrice: number;
      totalPrice: number;
    };
  };
  stepsCount: number;
  tariff: Tariff;
  totalCallCount: number;
  totalCallPrice: number;
  totalCpuAmount: number;
  totalCpuPrice: number;
  totalMemoryAmount: number;
  totalMemoryPrice: number;
  totalPrice: number;
}