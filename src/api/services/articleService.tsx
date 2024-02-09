import { PropsWithChildren, createContext, useContext } from 'react'
import { AricleDto, Article } from '../../models/article'
import { BaseRepository } from '../baseRepository'

export class ArticleService {
  private articleRepository: BaseRepository<Article>

  constructor(articleRepository: BaseRepository<Article>) {
    this.articleRepository = articleRepository
  }

  async getAllArticle(articleDto: AricleDto) {
    return this.articleRepository.getManyPagable<AricleDto>(
      'article-service/open-api/articles',
      articleDto,
    )
  }

  async getArticleById(id: string) {
    return this.articleRepository.get(`article-service/open-api/article/${id}`)
  }

  async getPoplarArticle() {
    return this.articleRepository.getMany(`article-service/open-api/popular`)
  }
}

const ArticleContext = createContext(new ArticleService(new BaseRepository()))

export const ArticleProvider = ({ children }: PropsWithChildren) => {
  const userService = new ArticleService(new BaseRepository())

  return (
    <ArticleContext.Provider value={userService}>
      {children}
    </ArticleContext.Provider>
  )
}

export const useArticleService = () => useContext(ArticleContext)
