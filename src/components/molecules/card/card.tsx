import styled from 'styled-components'
import newIcon from '../../assets/images/newIcon.svg'
import { FaBookmark, FaRegBookmark, FaEye } from 'react-icons/fa'
import { DummyData } from '../../../data/data'
import { typeMap, imageMap, dateFormat } from '../../../utils/utils'
import { useNavigate } from 'react-router-dom'

interface Props {
  item: DummyData
}

const Card = ({ item }: Props) => {
  const navigation = useNavigate()
  const {
    articleId,
    expiredAt,
    title,
    articleRoleDtos,
    articleStackDtos,
    viewCount,
    articleType,
    meetingType,
    author,
  } = item

  const isPostIdInLocalStorage = (articleId: number): boolean => {
    const postIds = JSON.parse(localStorage.getItem('postIds') || '[]')

    return postIds.includes(articleId.toString())
  }

  return (
    <StyledCardContainer onClick={() => navigation(`/post/${articleId}`)}>
      <StyledNewIconWrapper>
        {!isPostIdInLocalStorage(articleId) && (
          <img width={30} height={30} src={newIcon} />
        )}
      </StyledNewIconWrapper>

      <StyledRowBetweenWrapper>
        <StyledRowWrapper>
          <StyledArticleType>{typeMap.get(articleType)}</StyledArticleType>
          <StyledMeetingType>{typeMap.get(meetingType)}</StyledMeetingType>
        </StyledRowWrapper>
        <FaRegBookmark style={{ fontSize: '32px', color: '#8caf8e' }} />
      </StyledRowBetweenWrapper>
      <StyledCardExpired>{`마감일 | ${dateFormat(
        expiredAt,
      )}`}</StyledCardExpired>
      <h2>{title}</h2>
      <div style={{ display: 'flex', columnGap: '4px' }}>
        {articleRoleDtos.map((item, index) => (
          <StyledRole key={index}>{typeMap.get(item.roleName)}</StyledRole>
        ))}
      </div>
      <StyledRowWrapper>
        {articleStackDtos.map((item, index) => (
          <StyledTech key={index}>
            <StyledTechImg src={imageMap[item.stackName]} />
          </StyledTech>
        ))}
      </StyledRowWrapper>
      <div style={{ borderTop: '1px solid lightgray', paddingTop: '16px' }}>
        <StyledRowBetweenWrapper>
          <span>{author}</span>
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
  padding: 16px 20px;
  border: 2px solid #8caf8e;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;

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
