import Header from './components/Header';
import Menu from './components/Menu';

const Layout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="h-screen w-screen">
      <Header />
      <div className="flex gap-4">
        <Menu />
        {children}
      </div>
    </div>
  );
};

export default Layout;
