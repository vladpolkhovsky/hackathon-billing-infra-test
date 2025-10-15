export type MenuItem = {
  id: string;
  label: string;
  href?: string;
  icon?: React.ReactNode;
  children?: MenuItem[];
};

export const menu: MenuItem[] = [
  { id: 'dashboard', label: 'Dashboard', href: '/app/dashboard' },
  { id: 'settings', label: 'Settings', href: '/app/settings' },
  {
    id: 'projects',
    label: 'Projects',
    children: [
      { id: 'projects-1', label: 'Project 1', href: '/app/projects/1' },
      { id: 'projects-2', label: 'Project 2', href: '/app/projects/2' },
      { id: 'projects-3', label: 'Project 3', href: '/app/projects/3' },
    ],
  },
];
