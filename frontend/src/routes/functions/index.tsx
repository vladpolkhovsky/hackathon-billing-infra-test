import { useEffect, useState } from 'react';
import createClient from 'openapi-fetch';
import { paths } from '../../api';
import { BillingFunction, BillingFunctionDetails } from '@/types';
import FunctionRoute from '../function';

const FunctionsRoute = () => {
  const [functions, setFunctions] = useState<BillingFunction[]>([]);
  const [activeFunction, setActiveFunction] = useState<BillingFunction | null>(null);
  const [functionInfo, setFunctionInfo] = useState<BillingFunctionDetails | null>(null);

  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  useEffect(() => {
    client.GET('/v1/function').then((res) => {
      setFunctions(res.data?.content || []);
    });
  }, []);

  useEffect(() => {
    if (activeFunction && activeFunction.id) {
      client
        .GET('/v1/details', {
          params: {
            query: {
              functionId: activeFunction.id,
              tariffId: '0199ea25-117f-76d2-b3a8-a4a22958e3f1',
              period: 'MINUTE',
              from: 1760504471014,
              to: 1760574471014,
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
            <h1 className="text-3xl font-bold my-4">Functions</h1>
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
