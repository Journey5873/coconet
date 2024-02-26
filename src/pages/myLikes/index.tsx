import { useEffect, useState } from 'react'
import { BiSolidBookBookmark, BiSolidHeart } from 'react-icons/bi'
import styled from 'styled-components'
import Tabs from '../../components/organisms/customTabs/Tabs'
import Tab from '../../components/organisms/customTabs/Tab'
import { Article } from '../../models/article'
import { FaBookmark } from 'react-icons/fa'
import { useArticleService } from '../../api/services/articleService'
import Card from '../../components/molecules/card/card'
import BookmarkedCard from '../../components/molecules/card/bookmarkedCard'

const MyLikes = () => {
  const articleService = useArticleService()
  const [isActive, setIsActive] = useState<boolean>(false)
  const [bookmarkingArticles, setBookmarkingArticles] = useState<Article[]>([])
  const [suggestingArticles, setSuggestingArticles] = useState<Article[]>([])

  const fetchBookmarkingArticles = async () => {
    try {
      const result = await articleService.getBookmarkedArticle()

      if (result.data) {
        setBookmarkingArticles(result.data)
      }
    } catch (error) {
      console.log(error)
      setBookmarkingArticles([])
    }
  }

  const fetchSuggestingArticles = async () => {
    try {
      const result = await articleService.getSuggestingArticle()

      if (result.data) {
        setSuggestingArticles(result.data)
      }
    } catch (error) {
      console.log(error)
      setBookmarkingArticles([])
    }
  }

  const [value, setValue] = useState('one')

  const handleChange = (event: any, newValue: string) => {
    setValue(newValue)
  }

  useEffect(() => {
    fetchBookmarkingArticles()
    fetchSuggestingArticles()
  }, [])

  return (
    <StyledMyPostsPageWrapper>
      <StyledMypostsMyLikes>
        <StyledMypostsMain>
          <Tabs
            options={[
              {
                name: '읽은 목록',
                icon: (
                  <BiSolidBookBookmark
                    color="#8CAF8E"
                    style={{ width: 35, height: 35 }}
                  />
                ),
              },
              {
                name: '북마크',
                icon: (
                  <FaBookmark
                    color="#8CAF8E"
                    style={{ width: 35, height: 35 }}
                  />
                ),
              },
              {
                name: '지원 목록',
                icon: (
                  <BiSolidHeart
                    color="#8CAF8E"
                    style={{ width: 35, height: 35 }}
                  />
                ),
              },
            ]}
          >
            <Tab value="읽은 목록">읽은 목록</Tab>
            <Tab value="북마크">
              <StyledItemWrpper>
                {bookmarkingArticles.length <= 0 && (
                  <div>관심 목록이 없습니다.</div>
                )}
                {bookmarkingArticles?.map((article) => (
                  <BookmarkedCard item={article} key={article.articleUUID} />
                ))}
              </StyledItemWrpper>
            </Tab>
            <Tab value="지원 목록">
              <StyledItemWrpper>
                {suggestingArticles.length <= 0 && (
                  <div>지원 목록이 없습니다.</div>
                )}
                {suggestingArticles?.map((article) => (
                  <Card item={article} key={article.articleUUID} />
                ))}
              </StyledItemWrpper>
            </Tab>
          </Tabs>
        </StyledMypostsMain>
      </StyledMypostsMyLikes>
    </StyledMyPostsPageWrapper>
  )
}
export default MyLikes

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
`

const StyledItemWrpper = styled.div`
  margin-top: 1rem;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
`
