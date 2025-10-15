import createClient from 'openapi-fetch';
import { paths } from './api';
import Layout from './layout';
import { useUserStore } from './store';
import { useEffect } from 'react';
import { Route, Routes } from 'react-router';
import Tariffs from './routes/tariffs';

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
        <Route path="/app/tariffs" element={<Tariffs />} />
        <Route path="/app/functions" element={<div>Functions</div>} />
      </Routes>
    </Layout>
  );
}

export default App;
