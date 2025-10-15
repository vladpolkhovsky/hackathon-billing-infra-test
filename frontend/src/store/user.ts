import { User } from '@/types';
import { create } from 'zustand';

interface UserState {
  user: User | null;
  setUser: (user: User | null) => void;
  reset: () => void;
}

export const useUserStore = create<UserState>((set) => ({
  user: null,
  setUser: (user) => set({ user }),
  reset: () => set({ user: null }),
}));
