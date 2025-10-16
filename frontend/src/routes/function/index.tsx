import { Select, Tab, TabGroup } from '@headlessui/react';
import { ChangeEvent, useEffect, useState } from 'react';
import Details from './components/Details';
import Graphs from './components/Graphs';
import { BillingFunction, BillingFunctionDetails } from '@/types';
import createClient from 'openapi-fetch';
import { paths } from '@/api';
import useTariffStore from '@/store/tariff';

interface FunctionRouteProps {
  activeFunction: BillingFunction;
  setActiveFunction: (func: BillingFunction | null) => void;
  functionInfo: BillingFunctionDetails | null;
}

const FunctionRoute = ({ activeFunction, setActiveFunction, functionInfo }: FunctionRouteProps) => {
  const [activeTab, setActiveTab] = useState<'details' | 'graphs'>('details');
  const { tariffs, activeTariff, setTariffs, setActiveTariff } = useTariffStore();
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  useEffect(() => {
    if (!tariffs.length) {
      client.GET('/v1/tariff').then((res) => {
        setTariffs(res.data?.content || []);
        setActiveTariff(res.data?.content?.[0] || null);
      });
    }
  }, [tariffs]);

  const handleTariffChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const value = e.target.value;
    console.log(value);
    setActiveTariff(tariffs.find((t: any) => t.id === value) || null);
  };
  return (
    <div className="w-full pr-4">
      <div className="flex justify-between items-center">
        <img src="back.svg" alt="close" className="w-6 h-6 cursor-pointer" onClick={() => setActiveFunction(null)} />
        <h1 className="text-3xl font-bold my-4">{activeFunction?.name}</h1>
        <TabGroup className="flex gap-2" selectedIndex={activeTab === 'details' ? 0 : 1}>
          <Tab
            className="bg-gray-300 px-4 py-2 rounded-full aria-selected:bg-blue-300 hover:opacity-75 outline-none"
            onClick={() => setActiveTab('details')}
          >
            Details
          </Tab>
          <Tab
            className="bg-gray-300 px-4 py-2 rounded-full aria-selected:bg-blue-300 hover:opacity-75 outline-none"
            onClick={() => setActiveTab('graphs')}
          >
            Graphs
          </Tab>
        </TabGroup>
        <Select
          value={activeTariff?.id}
          onChange={handleTariffChange}
          className="w-64 bg-gray-100 hover:bg-gray-300 px-4 py-2 rounded"
        >
          {tariffs.map((t) => (
            <option key={t.id} value={t.id}>
              {t.name}
            </option>
          ))}
        </Select>
      </div>
      {activeTab === 'details' && <Details activeFunction={activeFunction} functionInfo={functionInfo} />}
      {activeTab === 'graphs' && <Graphs activeFunction={activeFunction} functionInfo={functionInfo} />}
    </div>
  );
};

export default FunctionRoute;
