export interface PositonProps {
  readonly value: string;
  readonly label: string;
}

export const PositionOptions:PositonProps[] = [
  { value: 'Backend', label: 'Backend'},
  { value: 'Frontend', label: 'Frontend'},
  { value: 'Designer', label: 'Designer' },
  { value: 'IOS', label: 'IOS'},
  { value: 'Android', label: 'Android' },
  { value: 'PM', label: 'PM' },
  { value: 'QA', label: 'QA' },
  { value: 'GameDev', label: 'GameDev' },
  { value: 'DevOps', label: 'DevOps'},
];

export const CareerOptions = [
  { value: 0, label: "0년"},
  { value: 1, label: "1년"},
  { value: 2, label: "2년" },
  { value: 3, label: "3년"},
  { value: 4, label: "4년" },
  { value: 5, label: "5년" },
  { value: 6, label: "6년"},
  { value: 7, label: "7년" },
  { value: 8, label: "8년"},
  { value: 9, label: "9년"},
  { value: 10, label: "10년 이상"},
]