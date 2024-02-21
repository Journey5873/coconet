import { useState, useEffect, useRef } from 'react'
import { useArticleService } from '../../../api/services/articleService'
import { Article } from '../../../models/article'
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa6'
import styled from 'styled-components'
import PopularCard from '../../molecules/card/popularCard'
import CircularProgress from '@mui/material/CircularProgress'

const PopularArticleList = () => {
  const scrollContainerRef = useRef(null)
  const articleService = useArticleService()
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [popularArticles, setPopularArticles] = useState<Article[]>([])

  const fetchPopularArticle = async () => {
    setIsLoading(true)
    try {
      const result = await articleService.getPoplarArticle()

      if (result.data) {
        setPopularArticles(result.data!)
      }
    } catch (error) {
      setPopularArticles([])
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
    fetchPopularArticle()
  }, [])

  return (
    <StyledPopularWrapper>
      <StyledPopularHeader>
        <h2>🔥 이번주 인기글</h2>
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
          {popularArticles.map((article) => (
            <PopularCard item={article} />
          ))}
        </StyledScrollContainer>
      )}
    </StyledPopularWrapper>
  )
}

export default PopularArticleList

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
  scroll-behavior: smooth;
  scrollbar-width: none; /* 파이어폭스용 */
  -ms-overflow-style: none; /* 인터넷 익스플로러 및 엣지용 */

  &::-webkit-scrollbar {
    display: none;
  }
`
