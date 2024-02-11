import { useState, useEffect } from 'react'
import { useArticleService } from '../../../api/services/articleService'
import styled from 'styled-components'
import { Article } from '../../../models/article'
import PopularCard from '../../molecules/card/popularCard'

const SuggestionArticleList = () => {
  const articleService = useArticleService()
  const [suggestionArticles, setSuggestionArticles] = useState<Article[]>([])

  const fetchPopularArticle = async () => {
    try {
      const result = await articleService.getSuggestionArticle()

      if (result.data) {
        setSuggestionArticles(result.data!)
      }
    } catch (error) {
      setSuggestionArticles([])
    }
  }

  useEffect(() => {
    fetchPopularArticle()
  }, [])

  return (
    <div>
      <h2>[추천] 다른 모임도 둘러보세요!</h2>
      <StyledScrollContainer>
        {suggestionArticles.map((article) => (
          <PopularCard item={article} />
        ))}
      </StyledScrollContainer>
    </div>
  )
}

export default SuggestionArticleList

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
