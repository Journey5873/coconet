import styled from 'styled-components'

export default function ChatForm() {
  return (
    <StyledChatFormWrapper action="">
      <StyledTextArea placeholder="메시지를 입력해주세요"></StyledTextArea>
      <StyledButtonWrapper>
        <StyleSubmitButton>전송</StyleSubmitButton>
      </StyledButtonWrapper>
    </StyledChatFormWrapper>
  )
}

const StyledChatFormWrapper = styled.form`
  display: flex;
  flex-direction: column;
  position: relative;
  margin: 16px;
  border: 1px solid #212124;
  border-radius: 8px;
  height: 125px;
  justify-content: space-between;
`
const StyledTextArea = styled.textarea`
  margin: 12px 12px 0px;
  width: calc(100% - 24px);
  height: 63px;
  line-height: 150%;
  padding: 0px;
  resize: none;
  font-size: 14px;
  border: none;
  outline: none;
  color: #212124;
  background-color: #fff;
`
const StyledButtonWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 8px 10px;
`
const StyleSubmitButton = styled.button`
  margin-left: auto;
  border-radius: 4px;
  width: 64px;
  height: 32px;
  line-height: 150%;
  font-weight: bold;
  font-size: 14px;
  background-color: rgb(140, 175, 142);
  color: rgb(255, 255, 255);
  outline: none;
  border: none;
`
