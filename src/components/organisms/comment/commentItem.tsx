import styled from 'styled-components'
import { dateFormat } from '../../../utils/utils'
import { Comment } from '../../../models/article'
import { ReactComponent as CoconutIcon } from '../../../components/assets/images/coconutIcon.svg'
import { ChangeEvent, useCallback, useEffect, useMemo, useState } from 'react'
import { User } from '../../../models/user'
import { useUserService } from '../../../api/services/userService'
import { toast } from 'react-toastify'
import { useArticleDetailService } from '../../../api/services/articleDetialService'
interface Props {
  comment: Comment
}

const CommentItem = ({ comment }: Props) => {
  const userService = useUserService()
  const [user, setUser] = useState<User>()
  const [updateComment, setUpdateComment] = useState<string>(comment.content)
  const [isEditComment, setIsEditComment] = useState<boolean>(false)
  const memeberId = localStorage.getItem('memberUUID')
  const articleDetailService = useArticleDetailService()

  const handleEdit = () => {
    setIsEditComment(true)
  }

  const handleUpdateComment = useCallback(
    (e: ChangeEvent<HTMLInputElement>) => {
      setUpdateComment(e.target.value)
    },
    [],
  )

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

  const updateMyComment = async () => {
    try {
      const requestDto: any = {
        commentUUID: comment.commentUUID,
        content: updateComment,
      }
      const result = await articleDetailService.updateComment(
        JSON.stringify(requestDto),
      )
      console.log(result.data)
    } catch (error) {
      console.log(error)
    }
  }

  const deleteMyComment = async () => {
    const confirm = window.confirm('해당 댓글을 삭제하시겠습니까?')

    if (confirm) {
      try {
        const requestDto: any = {
          commentUUID: comment.commentUUID,
          content: comment.content,
        }
        const result = await articleDetailService.deleteComment({
          data: JSON.stringify(requestDto),
        })
        if (result.succeeded) {
          toast.success('댓글을 삭제했습니다.')
        } else {
          toast.error('다시 시도해주세요.')
        }
      } catch (error) {
        console.log(error)
      }
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
      <StyledCommentUserInfoWrapper>
        <StyledCommentInputProfile className="comment_list" />
        <StyledCommentUserInfo>
          <StyledCommentUserName>{user.name}</StyledCommentUserName>
          <StyledCommentTime>{dateFormat(comment.createdAt)}</StyledCommentTime>
        </StyledCommentUserInfo>
      </StyledCommentUserInfoWrapper>
      <StyledComment>
        {isEditComment ? (
          <>
            <input
              type="text"
              value={updateComment}
              onChange={handleUpdateComment}
            />
          </>
        ) : (
          <StyledCommentContent>{updateComment}</StyledCommentContent>
        )}

        {comment.memberUUID === memeberId && (
          <StyledMyCommentButtonWrapper>
            {isEditComment ? (
              <>
                <StyledMyCommentButton onClick={updateMyComment}>
                  확인
                </StyledMyCommentButton>
                <StyledMyCommentButton onClick={() => setIsEditComment(false)}>
                  취소
                </StyledMyCommentButton>
              </>
            ) : (
              <>
                <StyledMyCommentButton onClick={handleEdit}>
                  수정
                </StyledMyCommentButton>
                <StyledMyCommentButton onClick={deleteMyComment}>
                  삭제
                </StyledMyCommentButton>
              </>
            )}
          </StyledMyCommentButtonWrapper>
        )}
      </StyledComment>
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
const StyledComment = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 0 14px;
`

const StyledMyCommentButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 6px;
`
const StyledMyCommentButton = styled.button`
  border: none;
  outline: none;
  background: #fff;
  cursor: pointer;
`
