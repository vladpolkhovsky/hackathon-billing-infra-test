import { useState } from 'react';
import { MenuItem, menu } from './config';
import { Link, useLocation } from 'react-router';

export default function SideMenu() {
  const [openIds, setOpenIds] = useState<Record<string, boolean>>({});

  const toggle = (id: string) => setOpenIds((s) => ({ ...s, [id]: !s[id] }));

  const location = useLocation();
  const isActive = (item: MenuItem) => (item.href ? location.pathname === item.href : false);

  return (
    <aside
      className={`bg-white border-r border-gray-200 w-64 h-[calc(100vh-48px)] flex flex-col transition-width duration-200`}
    >
      <div className="flex items-center justify-between p-4">
        <div className="text-lg font-semibold">App</div>
      </div>

      <nav className="flex-1 overflow-y-auto px-2 py-2">
        {menu.map((item) => (
          <div key={item.id} className="mb-1">
            <div
              className={`flex items-center gap-3 px-3 py-2 rounded-md cursor-pointer
                ${isActive(item) ? 'bg-blue-50 text-blue-600 ' : 'text-gray-700'}
                hover:bg-gray-100 `}
              onClick={() => (item.children ? toggle(item.id) : undefined)}
            >
              <span className="w-5 h-5 text-gray-400">{item.icon}</span>
              <>
                {item.href ? (
                  <Link to={item.href} className="flex-1">
                    {item.label}
                  </Link>
                ) : (
                  <span className="flex-1">{item.label}</span>
                )}
                {item.children && (
                  <svg
                    className={`w-4 h-4 transform ${openIds[item.id] ? 'rotate-90' : ''}`}
                    viewBox="0 0 20 20"
                    fill="none"
                  >
                    <path
                      d="M6 6L14 10L6 14"
                      stroke="currentColor"
                      strokeWidth="2"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                )}
              </>
            </div>

            {item.children && openIds[item.id] && (
              <div className="mt-1 ml-8">
                {item.children.map((child) => (
                  <Link
                    key={child.id}
                    to={child.href ?? '#'}
                    className={`block px-3 py-2 rounded-md text-sm
                      ${location.pathname === child.href ? 'bg-blue-50 text-blue-600' : 'text-gray-600 '}
                      hover:bg-gray-100 `}
                  >
                    {child.label}
                  </Link>
                ))}
              </div>
            )}
          </div>
        ))}
      </nav>

      <div className="px-4 py-3 border-t border-gray-200 ">
        <button className="w-full text-sm px-3 py-2 rounded-md bg-gray-100 hover:bg-gray-200">Logout</button>
      </div>
    </aside>
  );
}
