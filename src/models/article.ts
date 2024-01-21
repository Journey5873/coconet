export interface ArticleDto {
  articleUUID?: string
  title?: string
  content?: string
  plannedStartAt?: string
  expiredAt?: string
  estimatedDuration?: string
  articleType?: string
  meetingType?: string
  roles: [
    {
      roleName?: string
      participant?: number
    },
  ]
  stacks: [
    {
      stackName?: string
      category?: string
      image?: string
    },
  ]
}

export interface ArticleFilterDto {
  keyword?: string
  articleType?: string
  meetingType?: string
  bookmark?: boolean
  roles: [
    {
      roleName?: string
      participant?: number
    },
  ]
  stacks: [
    {
      stackName?: string
      category?: string
      image?: string
    },
  ]
}

export interface CommenetDto {
  commentId: number
  content: string
}
