import { useEffect, useState } from 'react'
import Loader from '../../components/atoms/Loader'
import { useNavigate, useParams } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import styled from 'styled-components'
import { ReactComponent as ArrowBack } from '../../components/assets/images/arrowBack.svg'
import { ReactComponent as CoconutIcon } from '../../components/assets/images/coconutIcon.svg'
import { FiEye } from 'react-icons/fi'
import { FaRegThumbsUp } from 'react-icons/fa'
import { imageMap, dateFormat } from '../../utils/utils'
import SupportButtonModal from '../../components/molecules/SupportButtonModal'
import { useArticleDetailService } from '../../api/services/articleDetialService'
import { Article } from '../../models/article'
import CommentItem from '../../components/organisms/comment/commentItem'
import { User } from '../../models/user'
import { useUserService } from '../../api/services/userService'
import CommentForm from '../../components/organisms/comment/commentForm'
import { toast } from 'react-toastify'

const PostForm = () => {
  const [post, setPost] = useState<Article | null>(null)
  const navigate = useNavigate()
  const articleDetailService = useArticleDetailService()
  const { id } = useParams()
  console.log(id)
  const fetchPostDetail = async (id: string) => {
    try {
      const result = await articleDetailService.getDetailArticle(id)
      if (result.data) {
        setPost(result.data)
        console.log(result.data)
      }
    } catch (error) {
      console.log(error)
      setPost(null)
    }
  }

  const updateMyPost = async () => {
    try {
      const requestDto: any = {
        articleUUID: '3fa85f64-5717-4562-b3fc-2c963f66afa6',
        title: 'string',
        content: 'string',
        plannedStartAt: '2024-02-15',
        expiredAt: '2024-02-15',
        estimatedDuration: 'TWO_MONTHS',
        articleType: 'STUDY',
        meetingType: 'ONLINE',
        roles: [
          {
            roleName: 'string',
            participant: 0,
          },
        ],
        stacks: ['string'],
      }
      const result = await articleDetailService.updateMyPost(
        JSON.stringify(requestDto),
      )
      console.log(result.data)
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    if (id) {
      fetchPostDetail(id)
    }
  }, [id])
  return (
    <>
      <StyledPostDetail>
        {post ? (
          <>
            <StyledPostHeader>
              <ArrowBack
                onClick={() => {
                  navigate('/')
                }}
              />
              <StyledPostTitle>{post.title}</StyledPostTitle>
              <StyledPostProfileBox>
                <StyledUser>
                  <StyledUserImg color="rgb(153, 153, 153)" />
                  <StyledUserName>
                    {/* {currentUser?.name ?? '알 수 없는 유저'} */}
                  </StyledUserName>
                </StyledUser>
                <StyledSeperator></StyledSeperator>
                <StlyedRegisteredDate>{`${dateFormat(
                  post.expiredAt,
                )}`}</StlyedRegisteredDate>
                <StyledButton>확인</StyledButton>
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
                    <span>{post.estimatedDuration}</span>
                  </StyledPostInfoListContent>
                </StyledPostInfoList>
                <StyledPostInfoRemains>
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
                  <StyledPostInfoListContent className="language">
                    <StyledPostInfoTitle>사용 언어</StyledPostInfoTitle>
                    <StyledLanguageListWrapper>
                      {post.stacks.map((item, index) => (
                        <StyledTech key={index}>
                          <StyledTechImg src={imageMap[item]} />
                        </StyledTech>
                      ))}
                    </StyledLanguageListWrapper>
                  </StyledPostInfoListContent>
                </StyledPostInfoRemains>
              </StyledPostInfoWrapper>
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

              {post.comments.map((comment) => (
                <>
                  <CommentItem key={comment.commentUUID} comment={comment} />
                </>
              ))}
            </div>
          </>
        ) : (
          <Loader />
        )}
      </StyledPostDetail>
    </>
  )
}

export default PostForm

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

const StyledUserImg = styled(CoconutIcon)`
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
  margin-right: 36px;
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

const StyledButton = styled.button``
