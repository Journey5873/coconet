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
                  {/* {
                                post.userImage
                                ? <StyledUserImg src="../../components/assets/images/Logo.svg" />
                                : <Logo />
                            }     */}
                  <StyledUserName>코코넷</StyledUserName>
                </StyledUser>
                <StyledSeperator></StyledSeperator>
                <StlyedRegisteredDate>2023.10.31</StlyedRegisteredDate>
              </StyledPostProfileBox>
              <StyledPostInfoWrapper>
                <StyledPostInfoList>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>모집 구분</StyledPostInfoTitle>
                    <span>프로젝트</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>진행 방식</StyledPostInfoTitle>
                    <span>온라인</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>모집 인원</StyledPostInfoTitle>
                    <span>1명</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>시작 예정</StyledPostInfoTitle>
                    <span>2023.11.19</span>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>연락 방법</StyledPostInfoTitle>
                    <StyledContackPoint>
                      <div>이메일</div>
                      <StyledLinkIcon />
                    </StyledContackPoint>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>예상 기간</StyledPostInfoTitle>
                    <span>2개월</span>
                  </StyledPostInfoListContent>
                </StyledPostInfoList>
                <StyledPostInfoRemains>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>모집 분야</StyledPostInfoTitle>
                    <StyledLanguageList>
                      <StyledPostInfoPositions>
                        프론트엔드
                      </StyledPostInfoPositions>
                      <StyledPostInfoPositions>
                        안드로이드
                      </StyledPostInfoPositions>
                    </StyledLanguageList>
                  </StyledPostInfoListContent>
                  <StyledPostInfoListContent>
                    <StyledPostInfoTitle>사용 언어</StyledPostInfoTitle>
                    <ul>
                      <li>
                        <img src="" alt="" />
                      </li>
                    </ul>
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
                <StyledViewcount>31</StyledViewcount>
              </StyledViewWrapper>
              <StyledBookmarkWrapper>
                <StyledBoockmarkImg />
                <StyledViewcount>0</StyledViewcount>
              </StyledBookmarkWrapper>
            </StyledViewAndBookmarkCount>
            <div style={{ marginBottom: 80 }}></div>
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
  padding: 1.5rem 1.5rem 5rem;
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
`

const StyledPostInfoRemains = styled.div`
  display: flex;
  margin-top: 24px;
`

const StyledLanguageList = styled.ul`
  display: flex;
  align-items: center;
  grid-gap: 12px;
  gap: 12px;
  list-style: none;
`
const StyledPostInfoPositions = styled.li`
  padding: 6px 10px;
  background: #f2f4f8;
  border-radius: 15px;
  font-weight: 700;
  font-size: 14px;
  line-height: 16px;
  text-align: center;
  color: #4a5e75;
`

const StyledPostInfoListContent = styled.li`
  width: 50%;
  display: flex;
  position: relative;
  align-items: center;
  font-weight: 700;
  font-size: 20px;
`

const StyledPostInfoTitle = styled.span`
  color: #717171;
  margin-right: 36px;
`

const StyledPostContentWrapper = styled.div`
  margin-top: 132px;
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

const StyledContackPoint = styled.div`
  cursor: pointer;
  display: flex;
  grid-gap: 4px;
  gap: 4px;
  position: absolute;
  background: #f2f4f8;
  padding: 4px 8px;
  border-radius: 10px;
  color: #4a5e75;
  left: 114px;
  font-size: 14px;
  text-decoration-line: underline;
  text-decoration-color: #4a5e75;
`

const StyledLinkIcon = styled(LinkIcon)`
  width: 16px;
  height: 16px;
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
