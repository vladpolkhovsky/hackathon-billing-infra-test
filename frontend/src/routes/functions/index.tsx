import { useEffect, useState } from 'react';
import createClient from 'openapi-fetch';
import { paths } from '../../api';
import { BillingFunction, BillingFunctionDetails } from '@/types';
import FunctionRoute from '../function';
import useTariffStore from '@/store/tariff';
import { Select } from '@headlessui/react';

const FunctionsRoute = () => {
  const [functions, setFunctions] = useState<BillingFunction[]>([]);
  const [activeFunction, setActiveFunction] = useState<BillingFunction | null>(null);
  const [functionInfo, setFunctionInfo] = useState<BillingFunctionDetails | null>(null);

  const { tariffs, activeTariff, setTariffs, setActiveTariff } = useTariffStore();

  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  useEffect(() => {
    client.GET('/v1/function').then((res) => {
      setFunctions(res.data?.content || []);
    });
  }, []);

  useEffect(() => {
    if (!tariffs.length) {
      client.GET('/v1/tariff').then((res) => {
        setTariffs(res.data?.content || []);
        setActiveTariff(res.data?.content?.[0] || null);
      });
    }
  }, [tariffs]);

  useEffect(() => {
    if (activeFunction && activeFunction.id && activeTariff && activeTariff.id) {
      client
        .GET('/v1/details', {
          params: {
            query: {
              functionId: activeFunction.id,
              tariffId: activeTariff.id,
              period: 'MINUTE',
              from: Date.now() - 1000 * 60 * 60 * 24 * 15,
              to: Date.now(),
            },
          },
        })
        .then((res) => {
          setFunctionInfo((res.data as BillingFunctionDetails) || null);
        });
    }
  }, [activeFunction]);

  return (
    <div className="w-full pr-4">
      <div className="items-center">
        {!activeFunction && (
          <>
            <div className="flex justify-between items-center">
              <h1 className="text-3xl font-bold my-4">Functions</h1>
              <Select
                value={activeTariff?.id}
                onChange={(value) => setActiveTariff(tariffs.find((t) => t.id === (value as any)) || null)}
                className="w-64 bg-gray-100 hover:bg-gray-300 px-4 py-2 rounded"
              >
                {tariffs.map((t) => (
                  <option key={t.id} value={t.id}>
                    {t.name}
                  </option>
                ))}
              </Select>
            </div>
            <div className="flex gap-2">
              {functions.map((f) => (
                <button
                  key={f.id}
                  className="bg-gray-300 hover:bg-gray-400 px-4 py-2 rounded"
                  onClick={() => setActiveFunction(f)}
                >
                  {f.name}
                </button>
              ))}
            </div>
          </>
        )}
        {activeFunction && (
          <FunctionRoute
            activeFunction={activeFunction}
            setActiveFunction={setActiveFunction}
            functionInfo={functionInfo}
          />
        )}
      </div>
    </div>
  );
};

export default FunctionsRoute;
