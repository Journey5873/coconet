import { PropsWithChildren, createContext, useContext } from 'react'
import { Article } from '../../models/article'
import { BaseRepository } from '../baseRepository'

export class ArticleDetailService {
  private articleRepository: BaseRepository<Article>

  constructor(articleRepository: BaseRepository<Article>) {
    this.articleRepository = articleRepository
  }

  async getDetailArticle(articleUuid: string) {
    return this.articleRepository.get(
      `article-service/open-api/article/${articleUuid}`,
    )
  }

  async createComment(articleUUID: string, data: any) {
    return this.articleRepository.create(
      `article-service/api/comment/${articleUUID}`,
      data,
    )
  }
}

const ArticleDetailContext = createContext(
  new ArticleDetailService(new BaseRepository()),
)

export const ArticleDetailProvider = ({ children }: PropsWithChildren) => {
  const userService = new ArticleDetailService(new BaseRepository())

  return (
    <ArticleDetailContext.Provider value={userService}>
      {children}
    </ArticleDetailContext.Provider>
  )
}

export const useArticleDetailService = () => useContext(ArticleDetailContext)
