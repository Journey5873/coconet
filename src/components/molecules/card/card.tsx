import styled from 'styled-components'
import newIcon from '../../assets/images/newIcon.svg'
import { FaRegBookmark, FaEye } from 'react-icons/fa'
import { typeMap, imageMap, dateFormat } from '../../../utils/utils'
import { useNavigate } from 'react-router-dom'
import { Article } from '../../../models/article'
import { useAppSelector } from '../../../store/RootReducer'
import { useArticleService } from '../../../api/services/articleService'

interface Props {
  item: Article
}

const Card = ({ item }: Props) => {
  const token = useAppSelector((state) => state.reducer.auth.token)
  const articleService = useArticleService()

  const navigation = useNavigate()
  const {
    articleUUID,
    expiredAt,
    title,
    roles,
    stacks,
    viewCount,
    articleType,
    meetingType,
  } = item

  const substringTitle =
    title.length > 33 ? title.substring(0, 33) + '...' : title

  const isPostIdInLocalStorage = (articleId: string): boolean => {
    const postIds = JSON.parse(localStorage.getItem('postIds') || '[]')

    return postIds.includes(articleId.toString())
  }

  const handleClickBookmark = async (event: any) => {
    event.stopPropagation()

    // TODO : 로그인 했는지 확인.
    if (!token) return

    await articleService.bookmarkArticle(articleUUID)
    alert('bookmarking!!')
  }

  return (
    <StyledCardContainer onClick={() => navigation(`/post/${articleUUID}`)}>
      <StyledNewIconWrapper>
        {!isPostIdInLocalStorage(articleUUID) && (
          <img width={30} height={30} src={newIcon} />
        )}
      </StyledNewIconWrapper>

      <StyledRowBetweenWrapper>
        <StyledRowWrapper>
          <StyledArticleType>{typeMap.get(articleType)}</StyledArticleType>
          <StyledMeetingType>{typeMap.get(meetingType)}</StyledMeetingType>
        </StyledRowWrapper>
        {token && (
          <FaRegBookmark
            style={{ fontSize: '32px', color: '#8caf8e' }}
            onClick={handleClickBookmark}
          />
        )}
      </StyledRowBetweenWrapper>
      <StyledCardExpired>{`마감일 | ${dateFormat(
        expiredAt,
      )}`}</StyledCardExpired>
      <StyledTitle>{substringTitle}</StyledTitle>
      <div style={{ display: 'flex', columnGap: '4px', overflow: 'hidden' }}>
        {roles?.map((role, index) => (
          <StyledRole key={index}>{typeMap.get(role.roleName)}</StyledRole>
        ))}
      </div>
      <StyledRowWrapper>
        {stacks?.slice(0, 5).map((stack, index) => (
          <StyledTech key={index}>
            <StyledTechImg src={imageMap[stack]} />
          </StyledTech>
        ))}
      </StyledRowWrapper>
      <div style={{ borderTop: '1px solid lightgray', paddingTop: '16px' }}>
        <StyledRowBetweenWrapper>
          {/* <span>{author}</span> */}
          <StyledRowWrapper>
            <FaEye />
            {viewCount}
          </StyledRowWrapper>
        </StyledRowBetweenWrapper>
      </div>
    </StyledCardContainer>
  )
}

export default Card

const StyledCardContainer = styled.div`
  position: relative;
  width: 300px;
  padding: 30px 20px;
  border: 2px solid #8caf8e;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  box-sizing: border-box;

  &:hover {
    scale: 1.05;
  }
`

const StyledNewIconWrapper = styled.div`
  position: absolute;
  top: -10px;
  left: -5px;
`

const StyledCardExpired = styled.div`
  font-size: 0.8rem;
  color: gray;
`

const StyledRole = styled.div`
  padding: 4px;
  min-width: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  color: #8caf8e;
  border: 1px solid #8caf8e;
  border-radius: 20px;
`

const StyledRowWrapper = styled.div`
  display: flex;
  flex-direction: row;
  column-gap: 8px;
  align-items: center;
`

const StyledRowBetweenWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`

const StyledTech = styled.div`
  width: 35px;
  aspect-ratio: 1/1;
  border: 1px solid lightgray;
  border-radius: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`

const StyledTechImg = styled.img`
  width: 90%;
  height: 90%;
  object-fit: cover;
`

const StyledArticleType = styled(StyledRole)`
  border: none;
  background-color: lightgray;
  color: black;
`

const StyledMeetingType = styled(StyledRole)`
  border: 1px solid lightgray;
  color: black;
`
const StyledTitle = styled.h2`
  font-size: 18px;
`
