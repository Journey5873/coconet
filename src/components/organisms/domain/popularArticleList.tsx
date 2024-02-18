import { useState, useEffect } from 'react'
import { useArticleService } from '../../../api/services/articleService'
import styled from 'styled-components'
import { Article } from '../../../models/article'
import PopularCard from '../../molecules/card/popularCard'

const PopularArticleList = () => {
  const articleService = useArticleService()
  const [popularArticles, setPopularArticles] = useState<Article[]>([])

  const fetchPopularArticle = async () => {
    try {
      const result = await articleService.getPoplarArticle()

      if (result.data) {
        setPopularArticles(result.data!)
      }
    } catch (error) {
      setPopularArticles([])
    }
  }

  useEffect(() => {
    fetchPopularArticle()
  }, [])

  return (
    <StyledPopularWrapper>
      <h2>🔥 이번주 인기글</h2>
      <StyledScrollContainer>
        {popularArticles.map((article) => (
          <PopularCard item={article} />
        ))}
      </StyledScrollContainer>
    </StyledPopularWrapper>
  )
}

export default PopularArticleList

const StyledPopularWrapper = styled.div`
  margin: 40px 0;
`

const StyledScrollContainer = styled.div`
  padding: 16px 0;
  display: flex;
  gap: 2rem;
  overflow-x: auto;
  scrollbar-width: thin;
  scrollbar-color: #888 #f0f0f0;

  &::-webkit-scrollbar {
    height: 8px;
  }

  &::-webkit-scrollbar-track {
    background: #f0f0f0;
  }

  &::-webkit-scrollbar-thumb {
    background: #888;
    border-radius: 10px;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: #555;
  }
`
