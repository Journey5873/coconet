export const dummyData = {
  articleId: 1,
  title: 'new_test_title',
  content: 'new_test_content',
  createdAt: '2023-09-30T04:37:23',
  updateAt: '2023-09-30T04:37:23',
  plannedStartAt: '2023-11-24T17:11:32',
  expiredAt: '2023-10-11T09:57:49',
  estimatedDuration: '4개월미만',
  viewCount: 0,
  bookmarkCount: 0,
  articleType: 'project',
  status: 1,
  meetingType: 'online',
  author: 'tester9090',
  articleRoleDtos: [
    {
      roleName: 'Backend',
      participant: 3,
    },
    {
      roleName: 'Frontend',
      participant: 3,
    },
  ],
  articleStackDtos: [
    {
      stackName: 'Java',
      category: 'backend',
      image: 'java.png',
    },
    {
      stackName: 'Spring',
      category: 'backend',
      image: 'spring.png',
    },
    {
      stackName: 'Next.js',
      category: 'front-end',
      image: 'nextjs.png',
    },
    {
      stackName: 'Mysql',
      category: 'backend',
      image: 'mysql.png',
    },
  ],
}

export type DummyData = typeof dummyData

type Skill = { value: string; label: string }

type SkillCategory = 'all' | 'frontend' | 'backend' | 'mobile' | 'etc'

export const skillData: Record<SkillCategory, Skill[]> = {
  all: [
    { value: 'JavaScript', label: 'JavaScript' },
    { value: 'TypeScript', label: 'TypeScript' },
    { value: 'React', label: 'React' },
    { value: 'Vue', label: 'Vue' },
    { value: 'Node.js', label: 'Nodejs' },
    { value: 'Java', label: 'Java' },
    { value: 'Spring', label: 'Spring' },
    { value: 'Kotlin', label: 'Kotlin' },
    { value: 'C++', label: 'C++' },
    { value: 'Go', label: 'Go' },
    { value: 'Python', label: 'Python' },
    { value: 'Django', label: 'Django' },
    { value: 'Flutter', label: 'Flutter' },
    { value: 'Swift', label: 'Swift' },
    { value: 'ReactNative', label: 'ReactNative' },
    { value: 'Unity', label: 'Unity' },
    { value: 'AWS', label: 'AWS' },
    { value: 'Kubernetes', label: 'Kubernetes' },
    { value: 'Docker', label: 'Docker' },
    { value: 'Git', label: 'Git' },
    { value: 'Figma', label: 'Figma' },
    { value: 'Zeplin', label: 'Zeplin' },
    { value: 'Jest', label: 'Jest' },
  ],
  backend: [
    { value: 'Node.js', label: 'Nodejs' },
    { value: 'Java', label: 'Java' },
    { value: 'Spring', label: 'Spring' },
  ],
  etc: [
    { value: 'C++', label: 'C++' },
    { value: 'Go', label: 'Go' },
    { value: 'Python', label: 'Python' },
    { value: 'Unity', label: 'Unity' },
    { value: 'AWS', label: 'AWS' },
    { value: 'Kubernetes', label: 'Kubernetes' },
    { value: 'Docker', label: 'Docker' },
    { value: 'Figma', label: 'Figma' },
    { value: 'Zeplin', label: 'Zeplin' },
  ],
  frontend: [
    { value: 'JavaScript', label: 'JavaScript' },
    { value: 'TypeScript', label: 'TypeScript' },
    { value: 'React', label: 'React' },
    { value: 'Vue', label: 'Vue' },
    { value: 'Next.js', label: 'Next.js' },
    { value: 'Jest', label: 'Jest' },
  ],
  mobile: [
    { value: 'Kotlin', label: 'Kotlin' },
    { value: 'Flutter', label: 'Flutter' },
    { value: 'Swift', label: 'Swift' },
    { value: 'ReactNative', label: 'ReactNative' },
  ],
}
