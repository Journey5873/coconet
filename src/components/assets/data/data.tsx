export interface SelectProps {
  value: string | number
  label: string
}

export const CategoryOptions = [
  { value: '스터디', label: '스터디' },
  { value: '프로젝트', label: '프로젝트' },
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
  { value: '전체', label: '전체' },
  { value: '온라인', label: '온라인' },
  { value: '오프라인', label: '오프라인' },
  { value: '온/오프라인', label: '온/오프라인' },
]

export const PeriodOptions: SelectProps[] = [
  { value: '기간 미정', label: '기간 미정' },
  { value: '1개월', label: '1개월' },
  { value: '2개월', label: '2개월' },
  { value: '3개월', label: '3개월' },
  { value: '4개월', label: '4개월' },
  { value: '5개월', label: '5개월' },
  { value: '6개월', label: '6개월' },
  { value: '장기', label: '장기' },
]
