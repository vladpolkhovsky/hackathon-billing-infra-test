import { create } from 'zustand';

interface FunctionState {
  functions: any[];
  error: string | null;
  activeFunction: any | null;
  setFunctions: (functions: any[]) => void;
  setFunctionError: (error: string) => void;
  reset: () => void;
}

const useFunctionStore = create<FunctionState>()((set) => ({
  functions: [],
  error: null,
  activeFunction: null,
  setFunctions: (functions) => set({ functions }),
  setFunctionError: (error) => set({ error }),
  reset: () => set({ functions: [] }),
}));

export default useFunctionStore;
