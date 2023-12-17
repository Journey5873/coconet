import styled from 'styled-components'

export default function ChatContentLeft() {
  return (
    <StyledContentLeft>
      <StyledProfileImageSmall src="/static/media/coconutIcon.4f2890a9909daf761c319c4573be165e.svg" />
      <StyledMessageBox>안녕하세요 프로젝트 참여하고 싶어요!</StyledMessageBox>
      <StyledMessageDate>오전 7:14</StyledMessageDate>
    </StyledContentLeft>
  )
}

const StyledContentLeft = styled.div`
  display: flex;
  justify-content: start;
  padding: 4px;
  gap: 8px;
`
const StyledProfileImageSmall = styled.img`
  border: 1px solid #dcdee3;
  border-radius: 50%;
  width: 32px;
  height: 32px;
`
const StyledMessageBox = styled.p`
  display: inline-flex;
  margin: 0px;
  padding: 10px 14px;
  max-width: 484px;
  word-break: break-word;
  white-space: pre-wrap;
  font-size: 14px;
  line-height: 150%;
  letter-spacing: -0.02em;
  border-radius: 2px 20px 20px;
  background-color: #eaebee;
  color: #212124;
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
