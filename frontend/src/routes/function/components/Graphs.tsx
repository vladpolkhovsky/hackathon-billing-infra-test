import { BillingFunction, BillingFunctionDetails } from '@/types';
import { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';
import { format } from 'date-fns';

interface FunctionProps {
  activeFunction: BillingFunction;
  functionInfo: BillingFunctionDetails | null;
}

const Graphs = ({ activeFunction, functionInfo }: FunctionProps) => {
  const cpuChartRef = useRef<HTMLCanvasElement>(null);
  const memoryChartRef = useRef<HTMLCanvasElement>(null);
  const priceChartRef = useRef<HTMLCanvasElement>(null);
  const callsChartRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    if (!functionInfo?.steps) return;

    const steps = Object.fromEntries(
      Object.entries(functionInfo.steps).map(([key, value]) => [format(key, 'yyyy-MM-dd HH:mm:ss'), value]),
    );
    const labels = Object.keys(steps).sort();

    const cpuData = labels.map((label) => steps[label].totalCpuAmount);
    const memoryData = labels.map((label) => steps[label].totalMemoryAmount);
    const priceData = labels.map((label) => steps[label].totalPrice);
    const callsData = labels.map((label) => steps[label].totalCallCount);

    const charts: Chart[] = [];

    //CPU
    if (cpuChartRef.current) {
      const cpuChart = new Chart(cpuChartRef.current, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [
            {
              label: 'CPU Usage',
              data: cpuData,
              borderColor: 'rgb(255, 99, 132)',
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              pointBorderWidth: 0,
              tension: 0.1,
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {
            title: {
              display: true,
              text: 'CPU Usage Over Time',
            },
          },
        },
      });
      charts.push(cpuChart);
    }

    //Memory
    if (memoryChartRef.current) {
      const memoryChart = new Chart(memoryChartRef.current, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [
            {
              label: 'Memory Usage',
              data: memoryData,
              borderColor: 'rgb(54, 162, 235)',
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              tension: 0.1,
              pointBorderWidth: 0,
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {
            title: {
              display: true,
              text: 'Memory Usage Over Time',
            },
          },
        },
      });
      charts.push(memoryChart);
    }

    //Price
    if (priceChartRef.current) {
      const priceChart = new Chart(priceChartRef.current, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [
            {
              label: 'Total Price',
              data: priceData,
              borderColor: 'rgb(75, 192, 192)',
              tension: 0.1,
              pointBorderWidth: 0,
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {},
        },
      });
      charts.push(priceChart);
    }

    //Calls
    if (callsChartRef.current) {
      const callsChart = new Chart(callsChartRef.current, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [
            {
              label: 'Function Calls',
              data: callsData,
              backgroundColor: 'rgba(153, 102, 255, 0.6)',
              borderColor: 'rgb(153, 102, 255)',
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {
            title: {
              display: true,
            },
          },
        },
      });
      charts.push(callsChart);
    }

    return () => {
      charts.forEach((chart) => chart.destroy());
    };
  }, [functionInfo]);

  if (!functionInfo) {
    return <div className="text-gray-500">No function data available</div>;
  }

  return (
    <div className="p-4">
      <div className="mb-6 p-4 bg-gray-50 rounded-lg">
        <h2 className="text-xl font-bold mb-4">Function: {activeFunction.name}</h2>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <span className="text-sm font-semibold">UUID</span>
            <p className="text-xs text-gray-500">{activeFunction.id}</p>
          </div>
          <div>
            <span className="text-sm font-semibold">Name</span>
            <p className="text-xs text-gray-500">{activeFunction.name}</p>
          </div>
          <div>
            <span className="text-sm font-semibold">Created At</span>
            <p className="text-xs text-gray-500">
              {format(activeFunction.createdAt || 0, 'dd/MM/yyyy HH:mm:ss') || 'N/A'}
            </p>
          </div>
          <div>
            <span className="text-sm font-semibold">Updated At</span>
            <p className="text-xs text-gray-500">
              {format(activeFunction.updatedAt || 0, 'dd/MM/yyyy HH:mm:ss') || 'N/A'}
            </p>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-4 rounded-lg shadow">
          <canvas ref={cpuChartRef} />
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <canvas ref={memoryChartRef} />
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <canvas ref={priceChartRef} />
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <canvas ref={callsChartRef} />
        </div>
      </div>
    </div>
  );
};

export default Graphs;
