import createClient from 'openapi-fetch';
import { paths } from './api';
import Layout from './layout';
import { useUserStore } from './store';
import { useEffect } from 'react';
import { Route, Routes } from 'react-router';

function App() {
  const client = createClient<paths>({
    baseUrl: '/api/billing',
  });

  useEffect(() => {
    client.GET('/v1/auth/iam').then((res) => {
      setUser(res.data);
    });
  }, []);

  const { setUser } = useUserStore();

  return (
    <Layout>
      <Routes>
        <Route path="/app/dashboard" element={<div>Dashboard</div>} />
        <Route path="/app/projects/:id" element={<div>Project</div>} />
        <Route path="/app/settings" element={<div>Settings</div>} />
        <Route path="/app/signin" element={<div>Sign In</div>} />
        <Route path="/app/signup" element={<div>Sign Up</div>} />
      </Routes>
    </Layout>
  );
}

export default App;
