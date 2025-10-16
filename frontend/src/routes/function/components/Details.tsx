import { BillingFunction, BillingFunctionDetails } from '@/types';

interface FunctionProps {
  activeFunction: BillingFunction;
  functionInfo: BillingFunctionDetails | null;
}

const Details = ({ activeFunction, functionInfo }: FunctionProps) => {
  if (!functionInfo) return <div>No data</div>;
  const { steps, ...displayData } = functionInfo;

  return (
    <div>
      {Object.entries(activeFunction).map(([key, value]) => (
        <div key={key}>
          <span className="text-sm font-semibold capitalize">{key.replace(/([A-Z])/g, ' $1').trim()}</span>
          <p className="text-xs text-gray-500">{value?.toString() || 'N/A'}</p>
        </div>
      ))}

      {Object.entries(displayData).map(([key, value]) => (
        <div key={key}>
          <span className="text-sm font-semibold capitalize">{key.replace(/([A-Z])/g, ' $1').trim()}</span>
          <p className="text-xs text-gray-500">
            {typeof value === 'object' ? JSON.stringify(value) : value?.toString() || 'N/A'}
          </p>
        </div>
      ))}
    </div>
  );
};

export default Details;
