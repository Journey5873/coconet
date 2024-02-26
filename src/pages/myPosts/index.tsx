import { useState, useEffect } from 'react'
import { BiSolidBookHeart } from 'react-icons/bi'
import styled from 'styled-components'
import { Article } from '../../models/article'
import { useArticleService } from '../../api/services/articleService'
import Card from '../../components/molecules/card/card'
import { CircularProgress } from '@mui/material'

const MyPosts = () => {
  const articleService = useArticleService()
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [myArticles, setMyArticles] = useState<Article[]>([])

  const fetchMyArticles = async () => {
    setIsLoading(true)
    try {
      const result = await articleService.getMyArticle()

      if (result.data) {
        setMyArticles(result.data)
      }
    } catch (error) {
      console.log(error)
      setMyArticles([])
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchMyArticles()
  }, [])

  return (
    <StyledMyPostsPageWrapper>
      <StyledMypostsMyLikes>
        <StyledMypostsMain>
          <StyledMypostsCategory>
            <StyledMypostsCategoryItem>
              <BiSolidBookHeart
                color="#8CAF8E"
                style={{ width: 35, height: 35 }}
              />
              <StyledMypostsText>작성 목록</StyledMypostsText>
            </StyledMypostsCategoryItem>
          </StyledMypostsCategory>
          <StyledItemWrpper>
            {isLoading ? (
              <CircularProgress />
            ) : (
              <>
                {myArticles.length <= 0 && (
                  <div style={{ fontSize: '1rem' }}>작성 목록이 없습니다.</div>
                )}
                {myArticles?.map((article) => (
                  <Card item={article} key={article.articleUUID} />
                ))}
              </>
            )}
          </StyledItemWrpper>
        </StyledMypostsMain>
      </StyledMypostsMyLikes>
    </StyledMyPostsPageWrapper>
  )
}
export default MyPosts

const StyledMyPostsPageWrapper = styled.section`
  padding-top: 125px;
`

const StyledMypostsMyLikes = styled.div`
  width: 1300px;
  margin: 0 auto;
  min-height: 100vh;

  @media screen and (max-width: 1919px) {
    width: 1200px;
  }
  @media screen and (max-width: 1024px) {
    width: calc(100% - 2rem);
  }
`

const StyledMypostsMain = styled.main`
  display: flex;
  flex-direction: column;
  padding: 16px 20px;
`

const StyledMypostsCategory = styled.section`
  display: flex;
  margin-bottom: 2rem;
`

const StyledMypostsCategoryItem = styled.div`
  display: flex;
  align-items: center;
  margin-right: 1rem;
  cursor: pointer;
  font-weight: 700;
  font-size: 1.5rem;
`

const StyledMypostsText = styled.span`
  margin-left: 0.5rem;
  color: #343a40;
`

const StyledItemWrpper = styled.div`
  margin-top: 1rem;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
`
