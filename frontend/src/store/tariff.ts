import { Tariff } from '@/types';
import { create } from 'zustand';

export interface TariffState {
  tariffs: Tariff[];
  error: string | null;
  activeTariff: Tariff | null;
  setTariffs: (tariffs: Tariff[]) => void;
  setTariffError: (error: string) => void;
  reset: () => void;
}

const useTariffStore = create<TariffState>()((set) => ({
  tariffs: [],
  error: null,
  activeTariff: null,
  setTariffs: (tariffs) => set({ tariffs }),
  setTariffError: (error) => set({ error }),
  reset: () => set({ tariffs: [] }),
}));

export default useTariffStore;
