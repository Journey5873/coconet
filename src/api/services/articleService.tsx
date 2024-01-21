import { ArticleDto, ArticleFilterDto, CommenetDto } from '../../models/article'
import { BaseRepository } from '../baseRepository'

export class ArticleService {
  private articleRepository: BaseRepository<ArticleDto>

  constructor(ArticleDto: BaseRepository<ArticleDto>) {
    this.articleRepository = ArticleDto
  }

  async getArticle(id: string) {
    return this.articleRepository.get('', id)
  }

  async updatArticle<Dto>(data: Dto, id: string) {
    return this.articleRepository.update<Dto>('', id, data)
  }

  async deleteArticle(id: string) {
    return this.articleRepository.delete('', id)
  }

  async test() {
    console.log('article test')
  }
}
