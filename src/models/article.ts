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

export interface Article {
  id: string
  title: string
  plannedStartAt: string
  expiredAt: string
  estimatedDurationm: EstimatedDuration
  articleType: ArticleType
  meetingType: MeetingType
  stacks: Stack[]
}

export interface AricleDto {
  keyword?: string
  articleType?: ArticleType
  meetingType?: MeetingType
  bookmark?: boolean
  roles?: string[]
  stacks?: string[]
}
