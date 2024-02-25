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

export const dummyData2 = {
  articleId: 2,
  title: 'new_test_title2',
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

// ‘Java’
// ‘Spring’
// ‘Node.js’
// ‘Kotlin’
// ‘Python’
// ‘Django’
// ‘Mysql’
// ‘MongoDB’
// ‘net’
// ‘JavaScript’
// ‘React’
// ‘TypeScript’
// ‘Next.js’
// ‘Flutter’
// ‘Swift’
// ‘AWS’
// ‘Docker’
// ‘Figma’
// ‘Git’
// ‘Jenkins’
// ‘Jira’
// ‘Unity’

export const skillData: Record<SkillCategory, Skill[]> = {
  all: [
    { value: 'JavaScript', label: 'JavaScript' },
    { value: 'TypeScript', label: 'TypeScript' },
    { value: 'Next.js', label: 'Next.js' },
    { value: 'React', label: 'React' },
    { value: 'Java', label: 'Java' },
    { value: 'Mysql', label: 'Mysql' },
    { value: 'MongoDB', label: 'MongoDB' },
    { value: 'net', label: 'net' },
    { value: 'Spring', label: 'Spring' },
    { value: 'Kotlin', label: 'Kotlin' },
    { value: 'Python', label: 'Python' },
    { value: 'Django', label: 'Django' },
    { value: 'Flutter', label: 'Flutter' },
    { value: 'Swift', label: 'Swift' },
    { value: 'Unity', label: 'Unity' },
    { value: 'AWS', label: 'AWS' },
    { value: 'Node.js', label: 'Node.js' },
    { value: 'Docker', label: 'Docker' },
    { value: 'Jenkins', label: 'Jenkins' },
    { value: 'Jira', label: 'Jira' },
    { value: 'Git', label: 'Git' },
    { value: 'Figma', label: 'Figma' },
  ],
  backend: [
    { value: 'Java', label: 'Java' },
    { value: 'Spring', label: 'Spring' },
    { value: 'Node.js', label: 'Node.js' },
    { value: 'Kotlin', label: 'Kotlin' },
    { value: 'Python', label: 'Python' },
    { value: 'Django', label: 'Django' },
    { value: 'Mysql', label: 'Mysql' },
    { value: 'MongoDB', label: 'MongoDB' },
    { value: 'net', label: 'net' },
  ],
  etc: [
    { value: 'Unity', label: 'Unity' },
    { value: 'AWS', label: 'AWS' },
    { value: 'Docker', label: 'Docker' },
    { value: 'Figma', label: 'Figma' },
    { value: 'Git', label: 'Git' },
    { value: 'Jenkins', label: 'Jenkins' },
    { value: 'Jira', label: 'Jira' },
  ],
  frontend: [
    { value: 'JavaScript', label: 'JavaScript' },
    { value: 'TypeScript', label: 'TypeScript' },
    { value: 'React', label: 'React' },
    { value: 'Next.js', label: 'Next.js' },
  ],
  mobile: [
    { value: 'Flutter', label: 'Flutter' },
    { value: 'Swift', label: 'Swift' },
  ],
}
