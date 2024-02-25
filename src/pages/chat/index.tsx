import styled from 'styled-components'
import io from 'socket.io-client'
import ChatList from '../../components/molecules/ChatLists'
import ChatForm from '../../components/atoms/ChatForm'
import ReceiveMessage from '../../components/molecules/ReceiveMessage'
import SendMessage from '../../components/molecules/SendMessage'

export default function ChattingPage() {
  return (
    <div style={{ background: '#f2f3f6', paddingTop: 85 }}>
      <StyledMainWrapper>
        <StyledChatListWrapper>
          <StyledMyProfile>코코넷</StyledMyProfile>
          <ChatList />
        </StyledChatListWrapper>
        <StyledChatSection>
          <StyledChatSectionWrapper>
            <StyledChattingBox>
              <StyledChatHeader>
                <StyledUserInfo>
                  <StyledProfileImage src="/static/media/coconutIcon.4f2890a9909daf761c319c4573be165e.svg" />
                  <StyledUserNickname>코코</StyledUserNickname>
                </StyledUserInfo>
              </StyledChatHeader>
              <StyledChatContent>
                <StyledDayDivider>
                  <StyledDateText>2023년 8월12일</StyledDateText>
                </StyledDayDivider>
                <ReceiveMessage />
                <SendMessage />
              </StyledChatContent>
              <ChatForm />
            </StyledChattingBox>
          </StyledChatSectionWrapper>
        </StyledChatSection>
      </StyledMainWrapper>
    </div>
  )
}

const StyledMainWrapper = styled.main`
  display: flex;
  overflow-x: auto;
  position: relative;
  margin: 0px auto;
  width: 1196px;
  height: calc(100vh - 64px);
`

const StyledChatListWrapper = styled.nav`
  display: flex;
  border-right: 1px solid #eaebee;
  width: 312px;
  min-width: 312px;
  flex-direction: column;
  justify-content: space-between;
  background-color: #fff;
`

const StyledMyProfile = styled.div`
  position: relative;
  display: flex;
  height: 64px;
  min-height: 64px;
  border-bottom: 1px solid #0017580d;
  padding: 0px 20px;
  align-items: center;
`

const StyledProfileImage = styled.img`
  width: 40px;
  min-height: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid var(--profile-image-border);
`

const StyledChatSection = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #eaebee;
  min-width: 812px;
  max-width: 812px;
  background-color: #fff;
`

const StyledChatSectionWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
`

const StyledChattingBox = styled.div`
  position: relative;
  display: flex;
  flex: 1 1 0px;
  flex-direction: column;
  overflow: hidden;
`

const StyledChatHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  min-height: 64px;
  border-bottom: 1px solid #0017580d;
  padding: 0px 20px;
`

const StyledUserInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`

const StyledUserNickname = styled.span`
  color: #212124;
`

const StyledChatContent = styled.div`
  overflow: hidden auto;
  padding: 0px 20px;
  background-color: #fff;
  min-height: 370px;
`

const StyledDayDivider = styled.div`
  display: flex;
  -webkit-box-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  align-items: center;
  margin: 20px 0px;
  color: #4d5159;
`

const StyledDateText = styled.div`
  display: flex;
  align-items: center;
  height: 28px;
  border: 1px solid #eaebee;
  box-sizing: border-box;
  border-radius: 14px;
  padding: 0px 12px;
  font-size: 12px;
`
