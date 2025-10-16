import { Tab, TabGroup } from '@headlessui/react';
import { useState } from 'react';
import Details from './components/Details';
import Graphs from './components/Graphs';
import { BillingFunction, BillingFunctionDetails } from '@/types';

interface FunctionRouteProps {
  activeFunction: BillingFunction;
  setActiveFunction: (func: BillingFunction | null) => void;
  functionInfo: BillingFunctionDetails | null;
}

const FunctionRoute = ({ activeFunction, setActiveFunction, functionInfo }: FunctionRouteProps) => {
  const [activeTab, setActiveTab] = useState<'details' | 'graphs'>('details');
  return (
    <div className="w-full pr-4">
      <div className="flex justify-between items-center">
        <img src="back.svg" alt="close" className="w-6 h-6 cursor-pointer" onClick={() => setActiveFunction(null)} />
        <h1 className="text-3xl font-bold my-4">Function</h1>
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
      </div>
      {activeTab === 'details' && <Details activeFunction={activeFunction} functionInfo={functionInfo} />}
      {activeTab === 'graphs' && <Graphs activeFunction={activeFunction} functionInfo={functionInfo} />}
    </div>
  );
};

export default FunctionRoute;
