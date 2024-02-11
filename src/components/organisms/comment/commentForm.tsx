import styled from 'styled-components'
import { ReactComponent as CoconutIcon } from '../../../components/assets/images/coconutIcon.svg'
import { Article, Comment } from '../../../models/article'
import { useArticleDetailService } from '../../../api/services/articleDetialService'
import { useState } from 'react'

interface CommentProps {
  post: Article
}

const CommentForm = ({ post }: CommentProps) => {
  const articleService = useArticleDetailService()
  const [commentValue, setCommnetValue] = useState<string>('')

  const onRegister = async () => {
    try {
      const requestDto: any = {
        content: commentValue,
      }

      const result = await articleService.createComment(
        `${post.articleUUID}`,
        JSON.stringify(requestDto),
      )

      console.log(result.data)
    } catch (error) {
      console.log(error)
    }
  }

  const handleComment = (e: any) => {
    setCommnetValue(e.target.value)
  }
  return (
    <StyledCommentInputWrppaer>
      <StyledCommentTitle>
        댓글
        <span style={{ marginLeft: 6 }}>{post.comments.length}</span>
      </StyledCommentTitle>
      <StyledCommentInputContainer>
        <StyledCommentInputProfile />
        <form style={{ width: '100%' }}>
          <StyledCommentInputTextArea
            placeholder="댓글을 입력해 주세요."
            onChange={handleComment}
            value={commentValue}
          ></StyledCommentInputTextArea>
          <StyledCommentInputButton onClick={onRegister}>
            댓글등록
          </StyledCommentInputButton>
        </form>
      </StyledCommentInputContainer>
    </StyledCommentInputWrppaer>
  )
}

export default CommentForm

const StyledCommentInputWrppaer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 100px;
`

const StyledCommentTitle = styled.div`
  margin-bottom: 15px;
  font-size: 18px;
  font-weight: 700;

  span {
    line-height: 24px;
    color: #939393;
  }
`

const StyledCommentInputContainer = styled.div`
  display: flex;
  grid-gap: 15px;
  gap: 15px;
`

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

const StyledCommentInputTextArea = styled.textarea`
  font-family: inherit;
  padding: 1rem 1rem 1.5rem;
  outline: none;
  border: 2px solid #e1e1e1;
  border-radius: 16px;
  width: 100%;
  min-height: 80px;
  margin-bottom: 10px;
  resize: none;
`
const StyledCommentInputButton = styled.button`
  margin: 16px 0 24px;
  margin-left: auto;
  width: 120px;
  height: 40px;
  background: #333;
  border-radius: 50px;
  font-weight: 700;
  color: #fff;
  font-size: 16px;
  line-height: 40px;
  cursor: pointer;
  display: flex;
  justify-content: center;
`
