import { BillingFunction, BillingFunctionDetails } from '@/types';
import { format } from 'date-fns';

interface FunctionProps {
  activeFunction: BillingFunction;
  functionInfo: BillingFunctionDetails | null;
}

const Details = ({ activeFunction, functionInfo }: FunctionProps) => {
  if (!functionInfo) return <div>No data</div>;
  const { ...displayData } = functionInfo;
  const displayMemoryAmount = displayData.totalMemoryAmount / 1024 / 1024 / 1024;

  return (
    <div>
      <div>
        <span className="text-sm font-semibold capitalize">Id:</span>
        <p className="text-xs text-gray-500">{activeFunction.id?.toString() || 'N/A'}</p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Created:</span>
        <p className="text-xs text-gray-500">{format(activeFunction.createdAt || 0, 'dd/MM/yyyy HH:mm:ss') || 'N/A'}</p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Updated:</span>
        <p className="text-xs text-gray-500">{format(activeFunction.updatedAt || 0, 'dd/MM/yyyy HH:mm:ss') || 'N/A'}</p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Average CPU Usage:</span>
        <p className="text-xs text-gray-500">
          {displayData.totalCpuAmount ? displayData.totalCpuAmount?.toString() : '0'}
        </p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Average CPU Price:</span>
        <p className="text-xs text-gray-500">
          {displayData.totalCpuPrice ? displayData.totalCpuPrice.toFixed(8) : '0'}$
        </p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Average Memory Usage:</span>
        <p className="text-xs text-gray-500">{displayMemoryAmount || '0'} GB</p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Average Memory Price:</span>
        <p className="text-xs text-gray-500">
          {displayData.totalMemoryPrice ? displayData.totalMemoryPrice.toFixed(8) : '0'}$
        </p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Total Calls:</span>
        <p className="text-xs text-gray-500">
          {displayData.totalCallCount ? displayData.totalCallCount?.toString() : '0'}
        </p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Total Call Price:</span>
        <p className="text-xs text-gray-500">
          {displayData.totalCallPrice ? displayData.totalCallPrice.toFixed(8) : '0'}$
        </p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Total Price:</span>
        <p className="text-xs text-gray-500">
          {displayData.totalPrice ? Math.ceil(displayData.totalPrice * 100) / 100 : '0'}$
        </p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Billing from:</span>
        <p className="text-xs text-gray-500">{format(functionInfo.billingFrom, 'dd/MM/yyyy HH:mm:ss') || 'N/A'}</p>
      </div>
      <div>
        <span className="text-sm font-semibold capitalize">Billing to:</span>
        <p className="text-xs text-gray-500">{format(functionInfo.billingTo, 'dd/MM/yyyy HH:mm:ss') || 'N/A'}</p>
      </div>
    </div>
  );
};

export default Details;
