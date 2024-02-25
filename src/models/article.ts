import { Dayjs } from 'dayjs'

export type ArticleType = 'STUDY' | 'PROJECT'
export type EstimatedDuration =
  | 'TWO_MONTHS'
  | 'THREE_MONTHS'
  | 'FOUR_MONTHS'
  | 'FIVE_MONTHS'
  | 'SIX_MONTHS'
  | 'ONE_YEAR'
  | 'OVER_ONE_YEAR'

export type MeetingType = 'ONLINE' | 'OFFLINE' | 'ONOFFLINE'

export interface ArticleRole {
  rolenName: string
  participant: number
}

export interface Stack {
  stackName: string
  category: string
  image: string
}

export interface Role {
  roleName: string
  participant: number
}

export interface Comment {
  commentUUID: string
  content: string
  createdAt: string
  memberUUID: string
  updatedAt: string
  writerName: string
  writerProfileImage: string
}

export interface Article {
  articleUUID: string
  title: string
  bookmarkCount: number
  expiredAt: string
  bookmarked: boolean
  estimatedDuration: EstimatedDuration
  articleType: ArticleType
  meetingType: MeetingType
  stacks: string[]
  roles: Role[]
  comments: Comment[]
  status: number
  content: string
  updatedAt: string
  viewCount: number
  createdAt: string
  memberUUID: string
  plannedStartAt: string
  writerName: string
  writerProfileImage: string
}

export interface AricleDto {
  keyword?: string
  articleType?: ArticleType
  meetingType?: MeetingType
  bookmark?: boolean
  roles?: string[]
  stacks?: string[]
}

export interface ArticleRequestDto {
  title: string
  content: string
  plannedStartAt: string
  expiredAt: string | Dayjs | null
  estimatedDuration: string
  articleType: string
  meetingType: string
  roles?: Role[]
  stacks?: string[]
}
