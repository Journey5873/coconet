import { PropsWithChildren, createContext, useContext } from 'react'
import { AricleDto, Article } from '../../models/article'
import { BaseRepository } from '../baseRepository'

export class ArticleService {
  private articleRepository: BaseRepository<Article>

  constructor(articleRepository: BaseRepository<Article>) {
    this.articleRepository = articleRepository
  }

  async getAllArticle(articleDto: AricleDto, page: number) {
    return this.articleRepository.getManyPagableWithBody<AricleDto>(
      `article-service/open-api/articles?page=${page}`,
      articleDto,
    )
  }

  async getAllArticlePrivate(articleDto: AricleDto) {
    return this.articleRepository.getManyPagableWithBody<AricleDto>(
      'article-service/api/articles',
      articleDto,
    )
  }

  async getArticleById(id: string) {
    return this.articleRepository.get(`article-service/open-api/article/${id}`)
  }

  async getPoplarArticle() {
    return this.articleRepository.getMany(`article-service/open-api/popular`)
  }

  async getSuggestionArticle() {
    return this.articleRepository.getMany(`article-service/api/suggestions`)
  }

  async getBookmarkedArticle() {
    return this.articleRepository.getMany(`article-service/api/bookmark`)
  }

  async getSuggestingArticle() {
    return this.articleRepository.getMany(`article-service/api/suggestions`)
  }

  async getMyArticle() {
    return this.articleRepository.getManyPagable(
      `article-service/api/my-articles`,
    )
  }

  async bookmarkArticle(articleUUID: string) {
    return this.articleRepository.create(
      `article-service/api/bookmark/${articleUUID}`,
      {},
    )
  }

  async createNewArticle(data: any) {
    return this.articleRepository.create(
      `article-service/api/article/create`,
      data,
    )
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
