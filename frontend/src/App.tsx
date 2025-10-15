import createClient from 'openapi-fetch';
import { paths } from './api';
import Layout from './layout';
import { useUserStore } from './store';
import { useEffect } from 'react';
import { Route, Routes } from 'react-router';
import TariffsRoute from './routes/tariffs';
import FunctionRoute from './routes/function';

function App() {
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  useEffect(() => {
    client.GET('/v1/auth/iam').then((res) => {
      setUser(res.data || null);
    });
  }, []);

  const { setUser } = useUserStore();

  return (
    <Layout>
      <Routes>
        <Route path="/app/tariffs" element={<TariffsRoute />} />
        <Route path="/app/functions" element={<div>Functions</div>} />
        <Route path="/app/tariffs/:tariffId" element={<div />} />
        <Route path="/app/functions/:functionId" element={<FunctionRoute />} />
      </Routes>
    </Layout>
  );
}

export default App;
