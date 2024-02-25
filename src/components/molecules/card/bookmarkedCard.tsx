import styled from 'styled-components'
import { dateFormat } from '../../../utils/utils'
import { useNavigate } from 'react-router-dom'
import { Article } from '../../../models/article'
import { useAppSelector } from '../../../store/RootReducer'

interface Props {
  item: Pick<Article, 'articleUUID' | 'expiredAt' | 'title' | 'plannedStartAt'>
}

const BookmarkedCard = ({ item }: Props) => {
  const { articleUUID, expiredAt, title, plannedStartAt } = item
  const token = useAppSelector((state) => state.reducer.auth.token)
  const navigation = useNavigate()

  return (
    <StyledCardContainer onClick={() => navigation(`/post/${articleUUID}`)}>
      <h2>{title}</h2>
      <StyledRowWrapper>
        <StyledCardExpired>{`마감일 | ${dateFormat(
          expiredAt,
        )}`}</StyledCardExpired>
        <StyledCardExpired>{`시작예정일 | ${dateFormat(
          plannedStartAt,
        )}`}</StyledCardExpired>
      </StyledRowWrapper>
    </StyledCardContainer>
  )
}

export default BookmarkedCard

const StyledCardContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 300px;
  min-height: 110px;
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

const StyledCardExpired = styled.div`
  font-size: 0.8rem;
  color: gray;
`

const StyledRowWrapper = styled.div`
  display: flex;
  flex-direction: row;
  column-gap: 8px;
  align-items: center;
`
