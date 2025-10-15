import { paths } from '@/api';
import { useUserStore } from '@/store';
import { Button, Input } from '@headlessui/react';
import { EyeIcon, EyeSlashIcon } from '@heroicons/react/24/outline';
import createClient from 'openapi-fetch';
import { useEffect, useState } from 'react';

const AuthModal = ({ onClose }: { onClose: () => void }) => {
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  const [isPasswordShown, setIsPasswordShown] = useState(true);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setError(null);
  }, [username, password]);

  const { setUser } = useUserStore();

  const handleLogin = async () => {
    const { data, error } = await client.POST('/v1/auth/sign-in', { body: { username, password } });

    if (error) {
      setError(error.message || 'Something went wrong');
    } else {
      setUser(data);
      onClose();
    }
  };

  return (
    <div
      className="h-screen w-screen top-0 left-0 flex items-center justify-center backdrop-blur z-10 absolute bg-transparent"
      onClick={onClose}
    >
      <div
        className="bg-gray-100 border border-gray-300 rounded-lg p-4 w-full max-w-md"
        onClick={(e) => e.stopPropagation()}
      >
        <h1 className="text-xl font-bold">Sign In</h1>
        <div className="mt-4">
          <label htmlFor="username" className="block text-sm font-medium">
            Login
          </label>
          <Input
            type="text"
            name="username"
            id="username"
            className="h-8 mt-1 pl-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-lg bg-white"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="mt-4">
          <label htmlFor="password" className="block text-sm font-medium">
            Password
          </label>
          <div className="relative">
            <Input
              type={isPasswordShown ? 'text' : 'password'}
              name="password"
              id="password"
              className="h-8 mt-1 pl-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-lg bg-white"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            {isPasswordShown ? (
              <EyeSlashIcon
                className="h-5 w-5 cursor-pointer text-gray-400 transition-all hover:text-gray-500 absolute right-2 top-1/2 -translate-y-1/2"
                onClick={() => setIsPasswordShown(false)}
              />
            ) : (
              <EyeIcon
                className="h-5 w-5 cursor-pointer text-gray-400 transition-all hover:text-gray-500 absolute right-2 top-1/2 -translate-y-1/2"
                onClick={() => setIsPasswordShown(true)}
              />
            )}
          </div>
        </div>
        {error && <p className="text-sm text-red-500 my-2">{error}</p>}
        <div className="mt-4 flex items-center justify-between gap-4">
          <Button className="bg-gray-300 hover:bg-gray-400 px-4 py-2 rounded w-full cursor-pointer" onClick={onClose}>
            Cancel
          </Button>
          <Button
            className="bg-blue-300 hover:bg-blue-400 px-4 py-2 rounded w-full cursor-pointer"
            onClick={handleLogin}
          >
            Login
          </Button>
        </div>
      </div>
    </div>
  );
};

export default AuthModal;
