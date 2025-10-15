import { useUserStore } from '@/store';

const Header = () => {
  const { user } = useUserStore();
  return (
    <div className="h-12 p-2 border-b border-gray-300 bg-white flex items-center justify-between">
      Header
      <div className="flex items-center gap-2">
        {!user && (
          <>
            <a href="/app/signin">
              <button className="bg-gray-300 hover:bg-gray-400 px-4 py-2 rounded">Sign In</button>
            </a>
            <a href="/app/signup">
              <button className="bg-gray-300 hover:bg-gray-400 px-4 py-2 rounded">Sign Up</button>
            </a>
          </>
        )}
        {user && user.username && <p className="text-sm font-medium">{user.username}</p>}
      </div>
    </div>
  );
};

export default Header;
