import { create } from 'zustand';

interface UserState {
  user: any;
  setUser: (user: any) => void;
  reset: () => void;
}

export const useUserStore = create<UserState>((set) => ({
  user: null,
  setUser: (user) => set({ user }),
  reset: () => set({ user: null }),
}));
