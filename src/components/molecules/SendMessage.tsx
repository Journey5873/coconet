import styled from 'styled-components'

export default function SendMessage() {
  return (
    <StyledSendMessage>
      <StyledMessageDate>오전 10:39</StyledMessageDate>
      <StyledUserMessageWrapper>
        <StyledUserMessageBox>
          안녕하세요! 지원해주셔서 감사합니다!
        </StyledUserMessageBox>
      </StyledUserMessageWrapper>
    </StyledSendMessage>
  )
}

const StyledSendMessage = styled.div`
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  padding: 4px;
`

const StyledMessageDate = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  padding: 0px 4px;
  font-size: 12px;
  line-height: 150%;
  letter-spacing: -0.02em;
  color: #868b94;
`

const StyledUserMessageWrapper = styled.div`
  position: relative;
  padding-left: 4px;
  box-sizing: border-box;
`

const StyledUserMessageBox = styled.p`
  display: inline-flex;
  margin: 0px;
  padding: 10px 14px;
  max-width: 484px;
  word-break: break-word;
  white-space: pre-wrap;
  font-size: 14px;
  line-height: 150%;
  letter-spacing: -0.02em;
  border-radius: 20px 2px 20px 20px;
  background-color: rgb(140, 175, 142);
  color: rgb(255, 255, 255);
`
