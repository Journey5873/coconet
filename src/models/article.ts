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
  roleName: string
  participant: number
}

export interface Stack {
  stackName: string
  category: string
  image: string
}

export interface Comment {
  commentId: number
  content: string
  createdAt: string
  memeberUuid: string
  updatedAt: string
}

export interface Article {
  memberUuid: string
  articleUuid: string
  title: string
  plannedStartAt: string
  expiredAt: string
  estimatedDuration: EstimatedDuration
  articleType: ArticleType
  meetingType: MeetingType
  roles: ArticleRole[]
  stacks: Stack[]
  content: string
  viewCount: number
  bookmarkCount: number
  comments: Comment[]
}

export interface AricleDto {
  keyword?: string
  articleType?: ArticleType
  meetingType?: MeetingType
  bookmark?: boolean
  roles?: string[]
  stacks?: string[]
}
