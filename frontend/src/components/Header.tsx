import { useUserStore } from '@/store';
import AuthModal from './AuthModal';

const Header = () => {
  const { user } = useUserStore();
  return (
    <div className="h-12 p-2 border-b border-gray-300 bg-white flex items-center justify-between">
      Faas Billing System
      <div className="flex items-center gap-2">
        {user && user.username && <p className="text-lg font-bold">{user.username}</p>}
        {!user && <AuthModal />}
      </div>
    </div>
  );
};

export default Header;
