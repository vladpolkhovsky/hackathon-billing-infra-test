import { paths } from '@/api';
import useTariffStore from '@/store/tariff';
import { Button, Input } from '@headlessui/react';
import createClient from 'openapi-fetch';
import { useEffect, useState } from 'react';

const AddTariffModal = ({ onClose }: { onClose: () => void }) => {
  const [tariffName, setTariffName] = useState('');
  const [cpuPrice, setCpuPrice] = useState<string | null>(null);
  const [memoryPrice, setMemoryPrice] = useState<string | null>(null);
  const [callPrice, setCallPrice] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  const { tariffs, setTariffs } = useTariffStore();

  const handleAddTariff = async () => {
    const { data, error } = await client.POST('/v1/tariff', {
      body: {
        tariffName,
        cpuPrice: Number(cpuPrice),
        memoryPrice: Number(memoryPrice),
        callPrice: Number(callPrice),
      },
    });

    if (error) {
      setError(error.message || 'Something went wrong');
    } else {
      onClose();
      setTariffs([data, ...tariffs]);
    }
  };

  useEffect(() => {
    setError(null);
  }, [tariffName, cpuPrice, memoryPrice, callPrice]);

  return (
    <div
      className="h-screen w-screen top-0 left-0 flex items-center justify-center backdrop-blur z-10 absolute bg-transparent"
      onClick={onClose}
    >
      <div
        className="bg-gray-100 border border-gray-300 rounded-lg p-4 w-full max-w-md"
        onClick={(e) => e.stopPropagation()}
      >
        <h1 className="text-xl font-bold">Add tariff</h1>
        <div className="mt-4">
          <label htmlFor="username" className="block text-sm font-medium">
            Tariff name
          </label>
          <Input
            type="text"
            name="name"
            id="name"
            className="h-8 mt-1 pl-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-lg bg-white"
            value={tariffName}
            onChange={(e) => setTariffName(e.target.value)}
          />
        </div>
        <div className="mt-4">
          <label htmlFor="cpuPrice" className="block text-sm font-medium">
            CPU/hour price
          </label>
          <Input
            type="text"
            name="cpuPrice"
            id="cpuPrice"
            className="h-8 mt-1 pl-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-lg bg-white"
            value={cpuPrice || ''}
            onChange={(e) => setCpuPrice(e.target.value)}
          />
        </div>
        <div className="mt-4">
          <label htmlFor="memoryPrice" className="block text-sm font-medium">
            GB/hour price
          </label>
          <Input
            type="text"
            name="memoryPrice"
            id="memoryPrice"
            className="h-8 mt-1 pl-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-lg bg-white"
            value={memoryPrice || ''}
            onChange={(e) => setMemoryPrice(e.target.value)}
          />
        </div>
        <div className="mt-4">
          <label htmlFor="callPrice" className="block text-sm font-medium">
            Call price
          </label>
          <Input
            type="text"
            name="callPrice"
            id="callPrice"
            className="h-8 mt-1 pl-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-lg bg-white"
            value={callPrice || ''}
            onChange={(e) => setCallPrice(e.target.value)}
          />
        </div>
        {error && <p className="text-sm text-red-500 my-2">{error}</p>}
        <div className="mt-4 flex items-center justify-between gap-4">
          <Button className="bg-gray-300 hover:bg-gray-400 px-4 py-2 rounded w-full cursor-pointer" onClick={onClose}>
            Cancel
          </Button>
          <Button
            className="bg-blue-300 hover:bg-blue-400 px-4 py-2 rounded w-full cursor-pointer"
            onClick={handleAddTariff}
          >
            Add tariff
          </Button>
        </div>
      </div>
    </div>
  );
};

export default AddTariffModal;
