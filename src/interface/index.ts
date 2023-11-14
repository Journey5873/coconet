export interface PostDetailProps {
  articleId: number;
  title: string;
  content: string;
  createdAt: string;
  updateAt: string;
  plannedStartAt: string;
  expiredAt: string;
  estimatedDuration?: string;
  viewCount: number;
  bookmarkCount: number;
  articleType: string;
  status: number;
  meetingType: string;
  author: string;
  articleRoleDtos: [
    {
      roleName: string;
      participant: number;
    }
  ],
  articleStackDtos: [
    {
      stackName: string;
      category: string;
      image: string;
    }
  ]
}
