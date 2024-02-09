import { useEffect, useState } from 'react'
import Loader from '../../components/atoms/Loader'
import { useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { ReactComponent as ArrowBack } from '../../components/assets/images/arrowBack.svg'
import { ReactComponent as CoconutIcon } from '../../components/assets/images/coconutIcon.svg'
import { FiEye } from 'react-icons/fi'
import { FaRegThumbsUp } from 'react-icons/fa'
import { imageMap, dateFormat } from '../../utils/utils'
import SupportButtonModal from '../../components/molecules/SupportButtonModal'
import { DummyData, dummyData } from '../../data/data'
import { useArticleService } from '../../api/services/articleService'

const PostDetail = () => {
  const [isVisible, setIsVisible] = useState(false)

  const { id } = useParams()

  const articleService = useArticleService()
  const [post, setPost] = useState<DummyData | null>(null)
  const navigate = useNavigate()

  const handleSupportButton = () => {
    setIsVisible(!isVisible)
  }

  const savePostIdToLocalStorage = (postId: string) => {
    if (!postId) return

    const postIds = JSON.parse(localStorage.getItem('postIds') || '[]')

    if (!postIds.includes(postId)) {
      postIds.push(postId)
      localStorage.setItem('postIds', JSON.stringify(postIds))
    }
  }

  //게시글 GET
  useEffect(() => {
    setPost(dummyData)
    savePostIdToLocalStorage(id || '')
  }, [id])

  if (!post) return null

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
                  <StyledUserName>{post.author}</StyledUserName>
                </StyledUser>
                <StyledSeperator></StyledSeperator>
                <StlyedRegisteredDate>{`${dateFormat(
                  post.expiredAt,
                )}`}</StlyedRegisteredDate>
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
                      {post.articleRoleDtos.map((value, i) => (
                        <StyledPositionList>
                          <StyledPositionName>
                            {value.roleName}
                          </StyledPositionName>
                          <StyledPositionCount>
                            {value.participant}
                          </StyledPositionCount>
                        </StyledPositionList>
                      ))}
                    </ul>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent className="language">
                    <StyledPostInfoTitle>사용 언어</StyledPostInfoTitle>
                    <StyledLanguageListWrapper>
                      {post.articleStackDtos.map((item, index) => (
                        <StyledTech key={index}>
                          <StyledTechImg src={imageMap[item.stackName]} />
                        </StyledTech>
                      ))}
                    </StyledLanguageListWrapper>
                  </StyledPostInfoListContent>
                </StyledPostInfoRemains>
              </StyledPostInfoWrapper>
              <StyledSupportButton onClick={() => handleSupportButton()}>
                지원하기
              </StyledSupportButton>
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
              <StyledCommentInputWrppaer>
                <StyledCommentTitle>
                  댓글<span style={{ marginLeft: 6 }}>0</span>
                </StyledCommentTitle>
                <StyledCommentInputContainer>
                  <StyledCommentInputProfile />
                  <StyledCommentInputTextArea
                    name="comment"
                    id="comment"
                    placeholder="댓글을 입력하세요."
                  ></StyledCommentInputTextArea>
                </StyledCommentInputContainer>
                <StyledCommentInputButton>댓글등록</StyledCommentInputButton>
              </StyledCommentInputWrppaer>
              <StyledCommentListWrapper>
                <StyledCommentList>
                  <div>
                    <StyledCommentUserInfoWrapper>
                      <StyledCommentInputProfile className="comment_list" />
                      <StyledCommentUserInfo>
                        <StyledCommentUserName>작성자 1</StyledCommentUserName>
                        <StyledCommentTime>
                          2023-11-12 18:28:21
                        </StyledCommentTime>
                      </StyledCommentUserInfo>
                    </StyledCommentUserInfoWrapper>
                  </div>
                  <StyledCommentContent>
                    Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                    Suscipit optio, laudantium quaerat quasi praesentium
                    possimus tempore velit numquam veniam laboriosam..
                  </StyledCommentContent>
                </StyledCommentList>
                <StyledCommentList>
                  <div>
                    <StyledCommentUserInfoWrapper>
                      <StyledCommentInputProfile className="comment_list" />
                      <StyledCommentUserInfo>
                        <StyledCommentUserName>작성자 2</StyledCommentUserName>
                        <StyledCommentTime>
                          2023-11-12 18:28:21
                        </StyledCommentTime>
                      </StyledCommentUserInfo>
                    </StyledCommentUserInfoWrapper>
                  </div>
                  <StyledCommentContent>
                    Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                  </StyledCommentContent>
                </StyledCommentList>
              </StyledCommentListWrapper>
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
  min-height: 100px;
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
`

const StyledCommentListWrapper = styled.div``

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
