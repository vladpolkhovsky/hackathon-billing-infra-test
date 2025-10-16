import { paths } from '@/api';
import createClient from 'openapi-fetch';
import useTariffStore from '@/store/tariff';
import { Button } from '@headlessui/react';
import { useEffect, useState } from 'react';
import AddTariffModal from './components/AddTariffModal';

const TariffsRoute = () => {
  const { tariffs, setTariffs, setTariffError } = useTariffStore();
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });
  const [isOpenedAddTariffModal, setIsOpenedAddTariffModal] = useState(false);

  useEffect(() => {
    const getTariffs = async () => {
      const { data, error } = await client.GET('/v1/tariff');
      if (error) {
        setTariffError(error.message || 'Something went wrong');
      }
      setTariffs(data?.content || []);
    };
    getTariffs();
  }, []);

  return (
    <div className="w-full pr-4">
      {isOpenedAddTariffModal && <AddTariffModal onClose={() => setIsOpenedAddTariffModal(false)} />}
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold my-4">Tariffs</h1>
        <Button
          className="bg-blue-300 hover:bg-blue-400 px-4 py-2 rounded"
          onClick={() => setIsOpenedAddTariffModal(true)}
        >
          Add tariff
        </Button>
      </div>
      {tariffs.map((tariff) => (
        <div key={tariff.id} className="flex gap-4 py-4 mr-4 border-b last:border-b-0 border-gray-300 w-full">
          <h2>{tariff.name}</h2>
          <p>
            <span className="font-semibold mr-1">CPU/hour:</span> {tariff.cpuPrice}$
          </p>
          <p>
            <span className="font-semibold mr-1">1GB/hour:</span> {tariff.memoryPrice}$
          </p>
          <p>
            <span className="font-semibold mr-1">Call:</span> {tariff.callPrice}$
          </p>
        </div>
      ))}
    </div>
  );
};

export default TariffsRoute;
