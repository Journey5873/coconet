import { useState, useEffect, useRef } from 'react'
import { useArticleService } from '../../../api/services/articleService'
import { Article } from '../../../models/article'
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa6'
import styled from 'styled-components'
import PopularCard from '../../molecules/card/popularCard'
import { CircularProgress } from '@mui/material'

const SuggestionArticleList = () => {
  const scrollContainerRef = useRef(null)
  const articleService = useArticleService()
  const [suggestionArticles, setSuggestionArticles] = useState<Article[]>([])
  const [isLoading, setIsLoading] = useState<boolean>(false)

  const [userName, setUserName] = useState('')

  const getSuggestionArticles = async () => {
    setIsLoading(true)
    try {
      const result = await articleService.getSuggestionArticle()

      if (result.data) {
        setSuggestionArticles(result.data)
        setUserName(result.data[0].writerName)
      }
    } catch (error) {
      setSuggestionArticles([])
    } finally {
      setIsLoading(false)
    }
  }

  const scroll = (direction: string) => {
    if (!scrollContainerRef.current) return

    const container = scrollContainerRef.current as HTMLElement

    const scrollAmount = container.clientWidth

    if (direction === 'left') {
      container.scrollLeft -= scrollAmount
    } else if (direction === 'right') {
      container.scrollLeft += scrollAmount
    }
  }

  useEffect(() => {
    getSuggestionArticles()
  }, [])

  return (
    <StyledSuggestionWrapper>
      <StyledPopularHeader>
        <h2>{!!userName ? userName : '회원'}님께 추천!</h2>
        <StyledButtonContainer>
          <StyledButtonWrapper onClick={() => scroll('left')}>
            <FaChevronLeft />
          </StyledButtonWrapper>
          <StyledButtonWrapper onClick={() => scroll('right')}>
            <FaChevronRight />
          </StyledButtonWrapper>
        </StyledButtonContainer>
      </StyledPopularHeader>

      {isLoading ? (
        <StyledLoadingWrapper>
          <CircularProgress />
        </StyledLoadingWrapper>
      ) : (
        <StyledScrollContainer ref={scrollContainerRef}>
          {suggestionArticles.map((article) => (
            <PopularCard item={article} />
          ))}
        </StyledScrollContainer>
      )}
    </StyledSuggestionWrapper>
  )
}

export default SuggestionArticleList

const StyledPopularHeader = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`

const StyledButtonContainer = styled.div`
  display: flex;
  column-gap: 1rem;
  align-items: center;
`

const StyledButtonWrapper = styled.div`
  cursor: pointer;
`

const StyledLoadingWrapper = styled.div`
  padding: 16px;
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
`

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
  scroll-behavior: smooth;
  scrollbar-width: none;
  -ms-overflow-style: none;
  &::-webkit-scrollbar {
    display: none;
  }
`
