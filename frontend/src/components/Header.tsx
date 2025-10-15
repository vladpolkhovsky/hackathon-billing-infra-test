import { useUserStore } from '@/store';
import { Button } from '@headlessui/react';
import { useState } from 'react';
import AuthModal from './AuthModal';

const Header = () => {
  const { user } = useUserStore();
  const [isOpenedAuthModal, setIsOpenedAuthModal] = useState(false);
  return (
    <div className="h-12 p-2 border-b border-gray-300 bg-white flex items-center justify-between">
      Faas Billing System
      <div className="flex items-center gap-2">
        {!user && (
          <Button
            className="bg-gray-300 hover:bg-gray-400 px-4 py-2 rounded"
            onClick={() => setIsOpenedAuthModal(true)}
          >
            Sign In
          </Button>
        )}
        {user && user.username && <p className="text-lg font-bold">{user.username}</p>}
        {isOpenedAuthModal && <AuthModal onClose={() => setIsOpenedAuthModal(false)} />}
      </div>
    </div>
  );
};

export default Header;
