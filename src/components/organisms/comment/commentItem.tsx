import styled from 'styled-components'
import { dateFormat } from '../../../utils/utils'
import { Comment } from '../../../models/article'
import { ReactComponent as CoconutIcon } from '../../../components/assets/images/coconutIcon.svg'
import { useEffect, useState } from 'react'
import { User } from '../../../models/user'
import { useUserService } from '../../../api/services/userService'

interface Props {
  comment: Comment
}

const CommentItem = ({ comment }: Props) => {
  const userService = useUserService()
  const [user, setUser] = useState<User>()

  const fetchCurrentUser = async () => {
    if (!comment.memberUUID) return
    try {
      const result = await userService.getUserById(comment.memberUUID)

      if (result.data) {
        setUser(result.data)
      } else {
        console.log(result.errors)
      }
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    fetchCurrentUser()
  }, [])

  if (!user) {
    return <div>댓글을 불러오는데 에러가 발생헀습니다.</div>
  }
  return (
    <StyledCommentList>
      <div>
        <StyledCommentUserInfoWrapper>
          <StyledCommentInputProfile className="comment_list" />
          <StyledCommentUserInfo>
            <StyledCommentUserName>{user.name}</StyledCommentUserName>
            <StyledCommentTime>
              {dateFormat(comment.createdAt)}
            </StyledCommentTime>
          </StyledCommentUserInfo>
        </StyledCommentUserInfoWrapper>
      </div>
      <StyledCommentContent>{comment.content}</StyledCommentContent>
    </StyledCommentList>
  )
}

export default CommentItem

const StyledCommentInputProfile = styled(CoconutIcon)`
  display: block;
  width: 44px;
  height: 44px;
  border-radius: 50%;

  &.comment_list {
    width: 52px;
    height: 52px;
    margin-right: 16px;
    object-fit: cover;
  }
`

const StyledCommentList = styled.li`
  display: flex;
  flex-direction: column;
  padding-top: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 2px solid #e1e1e1;
`

const StyledCommentUserInfoWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 18px;
`

const StyledCommentUserInfo = styled.div`
  display: flex;
  flex-direction: column;
  font-weight: 700;
`

const StyledCommentUserName = styled.div`
  color: #333;
  font-weight: 700;
`

const StyledCommentTime = styled.div`
  font-size: 14px;
  line-height: 126.5%;
  letter-spacing: -0.005em;
  color: #9f9f9f;
`

const StyledCommentContent = styled.p`
  font-size: 1.125rem;
  line-height: 1.7;
  letter-spacing: -0.004em;
  word-break: break-all;
  overflow-wrap: break-all;
`
