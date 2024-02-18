import { useState, useEffect } from 'react'
import { useArticleService } from '../../../api/services/articleService'
import styled from 'styled-components'
import { Article } from '../../../models/article'
import PopularCard from '../../molecules/card/popularCard'

const SuggestionArticleList = () => {
  const articleService = useArticleService()
  const [suggestionArticles, setSuggestionArticles] = useState<Article[]>([])
  const [userName, setUserName] = useState('')

  const getSuggestionArticles = async () => {
    try {
      const result = await articleService.getSuggestionArticle()

      if (result.data) {
        setSuggestionArticles(result.data)
        setUserName(result.data[0].writerName)
      }
    } catch (error) {
      setSuggestionArticles([])
    }
  }

  useEffect(() => {
    getSuggestionArticles()
  }, [])

  return (
    <StyledSuggestionWrapper>
      <h2>{!!userName ? userName : '회원'}님께 추천!</h2>
      <StyledScrollContainer>
        {suggestionArticles.map((article) => (
          <PopularCard item={article} />
        ))}
      </StyledScrollContainer>
    </StyledSuggestionWrapper>
  )
}

export default SuggestionArticleList

const StyledSuggestionWrapper = styled.div`
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
