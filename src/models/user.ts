export interface User {
  token: string
  name: string
  career: number
  profileImg?: string
  bio?: string
  githubLink?: string
  blogLink?: string
  notionLink?: string
  roles: string[]
  stacks: string[]
}
