export interface SelectProps {
  value: string | number
  label: string
}

export const CategoryOptions = [
  { value: 'STUDY', label: '스터디' },
  { value: 'PROJECT', label: '프로젝트' },
]

export const PositionOptions: SelectProps[] = [
  { value: 'Backend', label: 'Backend' },
  { value: 'Frontend', label: 'Frontend' },
  { value: 'Designer', label: 'Designer' },
  { value: 'IOS', label: 'IOS' },
  { value: 'Android', label: 'Android' },
  { value: 'PM', label: 'PM' },
  { value: 'QA', label: 'QA' },
  { value: 'GameDev', label: 'GameDev' },
  { value: 'DevOps', label: 'DevOps' },
]

export const CareerOptions: SelectProps[] = [
  { value: 0, label: '0년' },
  { value: 1, label: '1년' },
  { value: 2, label: '2년' },
  { value: 3, label: '3년' },
  { value: 4, label: '4년' },
  { value: 5, label: '5년' },
  { value: 6, label: '6년' },
  { value: 7, label: '7년' },
  { value: 8, label: '8년' },
  { value: 9, label: '9년' },
  { value: 10, label: '10년 이상' },
]

export const PersonnelOptions: SelectProps[] = [
  { value: '인원 미정', label: '인원 미정' },
  { value: '1명', label: '1명' },
  { value: '2명', label: '2명' },
  { value: '3명', label: '3명' },
  { value: '4명', label: '4명' },
  { value: '5명', label: '5명' },
  { value: '6명', label: '6명' },
  { value: '7명', label: '7명' },
  { value: '8명', label: '8명' },
  { value: '9명', label: '9명' },
  { value: '10명 이상', label: '10명 이상' },
]

export const OnOfflineOptions: SelectProps[] = [
  { value: 'ONLINE', label: '온라인' },
  { value: 'OFFLINE', label: '오프라인' },
  { value: 'ONOFFLINE', label: '온/오프라인' },
]

export const PeriodOptions: SelectProps[] = [
  { value: 'TWO_MONTHS', label: '2개월' },
  { value: 'THREE_MONTHS', label: '3개월' },
  { value: 'FOUR_MONTHS', label: '4개월' },
  { value: 'FIVE_MONTHS', label: '5개월' },
  { value: 'SIX_MONTHS', label: '6개월' },
  { value: 'ONE_YEAR', label: '1년 미만' },
  { value: 'OVER_ONE_YEAR', label: '1년 이상' },
]

export const StackOptions: SelectProps[] = [
  { value: 'Java', label: 'Java' },
  { value: 'Spring', label: 'Spring' },
  { value: 'Node.js', label: 'Node.js' },
  { value: 'Kotlin', label: 'Kotlin' },
  { value: 'Python', label: 'Python' },
  { value: 'Django', label: 'Django' },
  { value: 'Mysql', label: 'Mysql' },
  { value: 'MongoDB', label: 'MongoDB' },
  { value: 'net', label: 'net' },
  { value: 'JavaScript', label: 'JavaScript' },
  { value: 'React', label: 'React' },
  { value: 'TypeScript', label: 'TypeScript' },
  { value: 'Next.js', label: 'Next.js' },
  { value: 'Flutter', label: 'Flutter' },
  { value: 'Swift', label: 'Swift' },
  { value: 'AWS', label: 'AWS' },
  { value: 'Docker', label: 'Docker' },
  { value: 'Figma', label: 'Figma' },
  { value: 'Git', label: 'Git' },
  { value: 'Jenkins', label: 'Jenkins' },
  { value: 'Jira', label: 'Jira' },
  { value: 'Unity', label: 'Unity' },
]
