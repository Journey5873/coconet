import { useEffect, useState } from 'react'
import Loader from '../../components/atoms/Loader'
import { useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { ReactComponent as ArrowBack } from '../../components/assets/images/arrowBack.svg'
import { FiEye } from 'react-icons/fi'
import { FaRegBookmark, FaRegThumbsUp, FaBookmark } from 'react-icons/fa'
import { imageMap, dateFormat } from '../../utils/utils'
import SupportButtonModal from '../../components/molecules/SupportButtonModal'
import { useArticleDetailService } from '../../api/services/articleDetialService'
import { Article } from '../../models/article'
import CommentItem from '../../components/organisms/comment/commentItem'
import { User } from '../../models/user'
import { useUserService } from '../../api/services/userService'
import CommentForm from '../../components/organisms/comment/commentForm'
import { toast } from 'react-toastify'
import { useAppSelector } from '../../store/RootReducer'
import { useArticleService } from '../../api/services/articleService'

export const DateLists = [
  { value: 'TWO_MONTHS', Label: '2개월' },
  { value: 'THREE_MONTHS', Label: '3개월' },
  { value: 'FOUR_MONTHS', Label: '4개월' },
  { value: 'FIVE_MONTHS', Label: '5개월' },
  { value: 'SIX_MONTHS', Label: '6개월' },
  { value: 'ONE_YEAR', Label: '1년 미만' },
  { value: 'OVER_ONE_YEAR', Label: '1년 이상' },
]

const PostDetail = () => {
  const articleDetailService = useArticleDetailService()
  const [isVisible, setIsVisible] = useState(false)
  const [isDeleteComment, setIsDeleteComment] = useState<boolean>(false)
  const [isBookmarked, setIsBookmarked] = useState<boolean>(false)
  const [estimatedDurationKR, setEstimatedDurationKR] = useState('')

  const { id } = useParams()
  const [post, setPost] = useState<Article | null>(null)
  const navigate = useNavigate()
  const token = useAppSelector((state) => state.reducer.auth.token)
  const articleService = useArticleService()

  const handleSupportButton = () => {
    if (!token) {
      toast.error('로그인 후 이용해 주세요.')
      return
    }
    if (token) {
      setIsVisible(!isVisible)
    }
  }

  const handleEstimatedDuration = (date: string) => {
    DateLists.forEach((val) =>
      val.value === date ? setEstimatedDurationKR(val.Label) : val.value,
    )
  }

  const handleClickBookmark = async () => {
    if (!token) {
      toast.error('로그인 후 이용해 주세요.')
    }
    try {
      if (post) {
        const result = await articleService.bookmarkArticle(post.articleUUID)
        if (result.data === null) {
          toast.success('북마크를 삭제했습니다.')
        } else toast.success('북마크 되었습니다!')
        setIsBookmarked(!isBookmarked)
      }
    } catch (error) {
      console.log(error)
      toast.error('다시 시도해주세요!')
    }
  }

  const fetchPostDetail = async (id: string) => {
    try {
      const result = await articleDetailService.getDetailArticle(id)
      if (result.data) {
        setPost(result.data)
        savePostIdToLocalStorage(id || '')
        handleEstimatedDuration(result.data.estimatedDuration)
      }
      console.log(result)
    } catch (error) {
      console.log(error)
      setPost(null)
    }
  }

  const fetchPrivatePostDetail = async (id: string) => {
    try {
      const result = await articleDetailService.getPrivateDetailArticle(id)
      console.log(result)

      if (result.data) {
        setPost(result.data)
        savePostIdToLocalStorage(id || '')
        setIsBookmarked(result.data.bookmarked)
      }
    } catch (error) {
      console.log(error)
      setPost(null)
    }
  }

  const savePostIdToLocalStorage = (postId: string) => {
    if (!postId) return

    const postIds = JSON.parse(localStorage.getItem('postIds') || '[]')

    if (!postIds.includes(postId)) {
      postIds.push(postId)
      localStorage.setItem('postIds', JSON.stringify(postIds))
    }
  }

  const deleteMyPost = async () => {
    const confirm = window.confirm('해당 게시글을 삭제하시겠습니까?')

    if (confirm) {
      try {
        const result = await articleDetailService.deleteMyPost(
          `${post?.articleUUID}`,
        )
        if (result.succeeded) {
          toast.success('게시글을 삭제했습니다.')
          navigate('/')
        } else {
          toast.error('다시 시도해주세요.')
        }
      } catch (error) {
        console.log(error)
      }
    }
  }

  //게시글 GET
  useEffect(() => {
    if (id) {
      console.log('token', token)
      if (token) {
        fetchPrivatePostDetail(id)
      } else {
        fetchPostDetail(id)
      }
    }
  }, [id, isDeleteComment, token])

  if (!post) {
    return <Loader />
  }

  return (
    <>
      <StyledPostDetail>
        {post ? (
          <>
            <StyledPostHeader>
              <StyledBookmarButtonWrapper>
                <ArrowBack
                  onClick={() => {
                    navigate('/')
                  }}
                />
                {token ? (
                  isBookmarked ? (
                    <FaBookmark
                      style={{ fontSize: '35px', color: '#8caf8e' }}
                      onClick={handleClickBookmark}
                    />
                  ) : (
                    <FaRegBookmark
                      style={{
                        fontSize: '35px',
                        color: '#8caf8e',
                      }}
                      onClick={handleClickBookmark}
                    />
                  )
                ) : (
                  <></>
                )}
              </StyledBookmarButtonWrapper>
              <StyledPostTitle>{post.title}</StyledPostTitle>
              <StyledPostProfileBox>
                <StyledUser>
                  <StyledUserImg
                    src={'data:image/;base64,' + post.writerProfileImage ?? ''}
                    color="rgb(153, 153, 153)"
                  />
                  <StyledUserName>{post.writerName}</StyledUserName>
                </StyledUser>
                <StyledSeperator></StyledSeperator>
                <StlyedRegisteredDate>{`${dateFormat(
                  post.expiredAt,
                )}`}</StlyedRegisteredDate>
                {token === post.memberUUID && (
                  <StyledButtonWrapper>
                    <StyledButton onClick={() => navigate(`/post/edit/${id}`)}>
                      수정
                    </StyledButton>
                    <StyledButton onClick={deleteMyPost}>삭제</StyledButton>
                  </StyledButtonWrapper>
                )}
              </StyledPostProfileBox>
              <StyledPostInfoWrapper>
                <StyledPostInfoList>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>모집 구분</StyledPostInfoTitle>
                    <span>{post.articleType}</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>진행 방식</StyledPostInfoTitle>
                    <span>{post.meetingType}</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>시작 예정</StyledPostInfoTitle>
                    <span>{`${dateFormat(post.plannedStartAt)}`}</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>예상 기간</StyledPostInfoTitle>
                    <span>{estimatedDurationKR}</span>
                  </StyledPostInfoListContent>
                </StyledPostInfoList>
                <StyledPostInfoRemains>
                  {post.articleType === 'PROJECT' && (
                    <StyledPostInfoListContent className="recruitment_status">
                      <StyledPostInfoTitle>모집 현황</StyledPostInfoTitle>
                      <ul>
                        {post.roles.map((role, i) => (
                          <StyledPositionList>
                            <StyledPositionName>
                              {role?.roleName}
                            </StyledPositionName>
                            <StyledPositionCount>
                              {role.participant}
                            </StyledPositionCount>
                          </StyledPositionList>
                        ))}
                      </ul>
                    </StyledPostInfoListContent>
                  )}
                  <StyledPostInfoListContent className="language">
                    <StyledPostInfoTitle>사용 언어</StyledPostInfoTitle>
                    <StyledLanguageListWrapper>
                      {post.stacks.map((item, index) => {
                        return (
                          <StyledTech key={index}>
                            <StyledTechImg src={imageMap[item]} />
                          </StyledTech>
                        )
                      })}
                    </StyledLanguageListWrapper>
                  </StyledPostInfoListContent>
                </StyledPostInfoRemains>
              </StyledPostInfoWrapper>
              {post.memberUUID !== token && (
                <StyledSupportButton onClick={handleSupportButton}>
                  지원하기
                </StyledSupportButton>
              )}
            </StyledPostHeader>
            <StyledPostContentWrapper>
              <StyledPostInfo>프로젝트 소개</StyledPostInfo>
              <StyledPostContent>{post.content}</StyledPostContent>
            </StyledPostContentWrapper>
            <StyledViewAndBookmarkCount>
              <StyledViewWrapper>
                <StyledViewImg />
                <StyledViewcount>{post.viewCount}</StyledViewcount>
              </StyledViewWrapper>
              <StyledBookmarkWrapper>
                <StyledBoockmarkImg />
                <StyledViewcount>{post.bookmarkCount}</StyledViewcount>
              </StyledBookmarkWrapper>
            </StyledViewAndBookmarkCount>
            <div style={{ marginBottom: 80 }}>
              <CommentForm post={post} />
              {post.comments.length > 0 &&
                post.comments.map((comment) => (
                  <>
                    <CommentItem
                      key={comment.commentUUID}
                      comment={comment}
                      setIsDeleteComment={setIsDeleteComment}
                    />
                  </>
                ))}
            </div>
            {isVisible && (
              <SupportButtonModal
                isVisible={isVisible}
                handleSupportButton={handleSupportButton}
              />
            )}
          </>
        ) : (
          <Loader />
        )}
      </StyledPostDetail>
    </>
  )
}

export default PostDetail

const StyledPostDetail = styled.div`
  box-sizing: border-box;
  max-width: 900px;
  width: 100%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  padding: 85px 24px;
`

const StyledPostHeader = styled.section`
  display: flex;
  flex-direction: column;
  margin-top: 3rem;
`

const StyledBookmarButtonWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`

const StyledPostTitle = styled.div`
  margin-top: 40px;
  font-weight: 800;
  font-size: 36px;
  line-height: 126.5%;
  letter-spacing: -0.005em;
  color: #000;
`

const StyledPostProfileBox = styled.div`
  margin-top: 32px;
  padding-bottom: 32px;
  border-bottom: 3px solid #f2f2f2;
  display: flex;
  grid-gap: 15px;
  gap: 15px;
  align-items: center;
`

const StyledUser = styled.div`
  display: flex;
  align-items: center;
  position: relative;
  transition:
    transform 0.2s ease-in-out,
    -webkit-transform 0.2s ease-in-out;
`

const StyledUserImg = styled.img`
  cursor: pointer;
  display: block;
  height: 2.5rem;
  width: 2.5rem;
  margin-right: 8px;
  border-radius: 50%;
  object-fit: cover;
`

const StyledUserName = styled.div`
  color: #333;
  cursor: pointer;
  font-size: 18px;
  font-weight: 700;
`

const StyledSeperator = styled.div`
  width: 2px;
  height: 20px;
  background-color: #e2e2e2;
`

const StlyedRegisteredDate = styled.div`
  font-size: 18px;
  color: #717171;
`

const StyledPostInfoWrapper = styled.section`
  width: 100%;
`

const StyledPostInfoList = styled.ul`
  margin-top: 60px;
  display: flex;
  flex-wrap: wrap;
  list-style: none;
  justify-content: space-between;
  row-gap: 24px;

  @media screen and (max-width: 600px) {
    flex-direction: column;
  }
`

const StyledPostInfoRemains = styled.div`
  display: flex;
  margin-top: 24px;
`

const StyledPositionList = styled.li`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 8px;
`

const StyledPositionName = styled.h2`
  width: 100px;
  font-size: 15px;
  line-height: 1.3125rem;
  margin-right: 30px;
  font-weight: 700;
`

const StyledPositionCount = styled.span`
  font-size: 15px;
  line-height: 1.3125rem;
  color: #424251;
  margin-right: 34px;
`

const StyledSupportButton = styled.button`
  margin: 0 auto;
  margin-top: 61px;
  width: 180px;
  height: 50px;
  background: #8caf8e;
  border-radius: 50px;
  border: 1px solid #8caf8e;
  font-weight: 700;
  color: #fff;
  font-size: 16px;
  line-height: 40px;
  cursor: pointer;
`

const StyledPostInfoListContent = styled.li`
  width: 50%;
  display: flex;
  position: relative;
  align-items: center;
  font-weight: 700;
  font-size: 20px;

  &.recruitment_status {
    width: 100%;
  }
  &.language {
    width: 100%;
    margin-top: 24px;
  }

  @media screen and (max-width: 600px) {
    width: 100%;
  }
`

const StyledLanguageListWrapper = styled.ul`
  list-style: none;
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
`
const StyledTech = styled.li`
  width: 50px;
  height: 50px;
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

const StyledPostInfoTitle = styled.span`
  color: #717171;
  margin-right: 34px;
  min-width: 90px;
`

const StyledPostContentWrapper = styled.div`
  margin-top: 44px;
  font-size: 1.125rem;
  word-break: break-all;
  line-height: 1.7;
  letter-spacing: -0.004em;
`

const StyledPostInfo = styled.h2`
  margin: 0;
  color: #333;
  font-size: 26px;
  font-weight: 700;
  padding-bottom: 24px;
  border-bottom: 3px solid #f2f2f2;
`

const StyledPostContent = styled.div`
  width: 100%;
  margin: 40px auto 0;
`

const StyledViewAndBookmarkCount = styled.section`
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
  height: 20px;
`

const StyledViewWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
`

const StyledViewImg = styled(FiEye)`
  color: rgb(153, 153, 153);
  display: block;
  width: 20px;
  height: auto;
`

const StyledBoockmarkImg = styled(FaRegThumbsUp)`
  color: rgb(153, 153, 153);
  display: block;
  width: 20px;
  height: auto;
`

const StyledViewcount = styled.span`
  color: rgb(153, 153, 153);
  font-size: 14px;
  font-weight: 700;
`

const StyledBookmarkWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
`

const StyledButtonWrapper = styled.div`
  margin-left: auto;
  display: flex;
  gap: 4px;
`

const StyledButton = styled.button`
  outline: none;
  border: none;
  background: #fff;
  text-decoration: underline;
  cursor: pointer;

  &:hover {
    color: rgb(140, 175, 142);
  }
`
