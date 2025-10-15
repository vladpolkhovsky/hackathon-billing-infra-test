export type MenuItem = {
  id: string;
  label: string;
  href?: string;
  icon?: React.ReactNode;
  children?: MenuItem[];
};

export const menu: MenuItem[] = [
  { id: 'Tariffs', label: 'Tariffs', href: '/app/tariffs' },
  { id: 'functions', label: 'Functions', href: '/app/functions' },
];
