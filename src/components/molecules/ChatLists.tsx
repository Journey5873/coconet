import styled from 'styled-components'
import { useChatService } from '../../api/services/chatService'
import { useEffect, useState } from 'react'
import { ChatDto } from '../../models/chat'

export default function ChatList() {
  const chatService = useChatService()
  const [myChatLists, setMyChatLists] = useState<ChatDto[]>([])

  const fetchPostDetail = async () => {
    try {
      const result = await chatService.getMyRooms()
      if (result.data) {
        setMyChatLists(result.data)
      }
      console.log(result)
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    fetchPostDetail()
  }, [])

  return (
    <StyledChatLists>
      <StyledChatList>
        <StyledProfileWrapper>
          <StyledProfileImage
            src="/static/media/coconutIcon.4f2890a9909daf761c319c4573be165e.svg"
            alt="profileImage"
          />
        </StyledProfileWrapper>
        <StyledPreviewWapper>
          <StyledPreviewTitleWrapper>
            <StyledPreviewNickname>코코</StyledPreviewNickname>
            <StyledpreviewDate>오후 06:59</StyledpreviewDate>
          </StyledPreviewTitleWrapper>
          <StyledPreviewDescription>
            <StyledPreviewText>히로님이 이모티콘을 보냈어요.</StyledPreviewText>
          </StyledPreviewDescription>
        </StyledPreviewWapper>
      </StyledChatList>
    </StyledChatLists>
  )
}

const StyledChatLists = styled.ul`
  position: relative;
  margin: 0px;
  padding: 0px;
  height: calc(100% - 56px);
  list-style: none;
  overflow: hidden auto;
  background-color: var(--seed-semantic-color-paper-sheet);
`

const StyledChatList = styled.li`
  display: flex;
  padding: 16px;
  height: 72px;
  border-bottom: 1px solid #0017580d;
  align-items: center;
  position: relative;
  overflow: hidden;
  background-color: #fff;
  transition:
    background-color 0.6s ease 0s,
    background-size 0.6s ease 0s;
  background-position: center center;
  contain: content;
`

const StyledProfileWrapper = styled.div`
  margin-right: 8px;
  height: 40px;
`

const StyledProfileImage = styled.img`
  width: 40px;
  min-height: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid var(--profile-image-border);
`

const StyledPreviewWapper = styled.div`
  flex: 1 0 0%;
  width: 0px;
`

const StyledPreviewTitleWrapper = styled.div`
  display: flex;
  -webkit-box-align: center;
  align-items: center;
`

const StyledPreviewNickname = styled.span`
  height: 20px;
  font-weight: bold;
  font-size: 13px;
  letter-spacing: -0.02em;
  color: var(--seed-scale-color-gray-900);
  overflow-x: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`

const StyledpreviewDate = styled.div`
  margin-left: 4px;
  color: var(--seed-scale-color-gray-600);
  font-size: 12px;
  white-space: nowrap;
  box-sizing: border-box;
`
const StyledPreviewDescription = styled.div`
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  height: 20px;
  font-size: 13px;
  color: #4d5159;
`

const StyledPreviewText = styled.span`
  overflow-x: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`
