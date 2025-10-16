import { Tariff } from '@/types';
import { create } from 'zustand';

export interface TariffState {
  tariffs: Tariff[];
  error: string | null;
  activeTariff: Tariff | null;
  setTariffs: (tariffs: Tariff[]) => void;
  setTariffError: (error: string) => void;
  setActiveTariff: (tariff: Tariff | null) => void;
  reset: () => void;
}

const useTariffStore = create<TariffState>()((set) => ({
  tariffs: [],
  error: null,
  activeTariff: null,
  setTariffs: (tariffs) => set({ tariffs }),
  setTariffError: (error) => set({ error }),
  setActiveTariff: (tariff) => set({ activeTariff: tariff }),
  reset: () => set({ tariffs: [] }),
}));

export default useTariffStore;
