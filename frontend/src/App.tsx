import '@/App.css';
import createClient from 'openapi-fetch';
import { paths } from './api';
import { useState } from 'react';

function App() {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<any>(null);
  const [error, setError] = useState<any>(null);
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  const testApi = async () => {
    setLoading(true);
    const { data, error } = await client.GET('/v1/test-with-body');
    setData(data);
    setError(error);
    setLoading(false);
  };

  return (
    <>
      <h1>Billing API</h1>
      <h2>Curently only testing endpoint</h2>
      <div className="card">
        <button onClick={testApi}>{loading ? 'Loading...' : 'Test '}</button>
        {error && <p>Error: {error.message}</p>}
        {data && <p>Data: {JSON.stringify(data)}</p>}
      </div>
    </>
  );
}

export default App;
