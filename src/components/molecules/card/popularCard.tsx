import { useNavigate } from 'react-router-dom'
import { Article } from '../../../models/article'
import styled from 'styled-components'
import { typeMap, daysUntil } from '../../../utils/utils'
import { AiFillAlert } from 'react-icons/ai'
import { FaEye, FaRegBookmark } from 'react-icons/fa'

interface Props {
  item: Article
}

const PopularCard = ({ item }: Props) => {
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
  return (
    <StyledCardContainer onClick={() => navigation(`/post/${articleUUID}`)}>
      <StyledRowBetweenWrapper>
        <StyledRowWrapper
          style={{ width: '100%', justifyContent: 'space-between' }}
        >
          <StyledArticleType>{typeMap.get(articleType)}</StyledArticleType>

          <StyledCardExpired>
            <AiFillAlert />
            {`마감일 | ${daysUntil(expiredAt)}일`}
          </StyledCardExpired>
        </StyledRowWrapper>
      </StyledRowBetweenWrapper>

      <h2>{title}</h2>

      <StyledRowBetweenWrapper>
        <StyledRowWrapper>
          <span>조회수</span>
          {viewCount}
        </StyledRowWrapper>
      </StyledRowBetweenWrapper>
    </StyledCardContainer>
  )
}

export default PopularCard

const StyledCardContainer = styled.div`
  position: relative;
  width: 300px;
  padding: 16px 20px;
  border: 2px solid lightgray;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  cursor: pointer;
`

const StyledCardExpired = styled.div`
  font-size: 0.8rem;
  color: gray;
  border: 1px solid red;
  padding: 8px;
  border-radius: 20px;
  color: red;
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

const StyldRowPositioned = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: end;
`

const StyledArticleType = styled(StyledRole)`
  border: none;
  background-color: lightgray;
  color: black;
`
