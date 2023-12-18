import { useRef, useState } from 'react'
import styled from 'styled-components'
import { FaCamera } from 'react-icons/fa'

export default function ChatForm() {
  const [fileURL, setFileURL] = useState<string>('')
  const [file, setFile] = useState<FileList | null>()
  const imgUploadInput = useRef<HTMLInputElement | null>(null)

  const onImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setFile(event.target.files)

      const newFileURL = URL.createObjectURL(event.target.files[0])
      setFileURL(newFileURL)
    }
  }
  return (
    <StyledChatFormWrapper action="">
      <StyledTextArea placeholder="메시지를 입력해주세요"></StyledTextArea>
      <StyledButtonWrapper>
        <StyledImageWrapper>
          <FaCamera size={24} color="rgb(140, 175, 142)" />
          <StyledImageInput
            type="file"
            id="img"
            accept="image/*"
            required
            ref={imgUploadInput}
            onChange={onImageChange}
          ></StyledImageInput>
        </StyledImageWrapper>

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
  align-items: center;
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

const StyledImageWrapper = styled.label`
  display: flex;
  position: relative;
  cursor: pointer;
  width: 150px;
`

const StyledImage = styled.img`
  display: block;
  height: 150px;
  width: 150px;
  border: 1px solid #e1e1e1;
  border-radius: 50%;
  object-fit: cover;
`

const StyledImageInput = styled.input`
  opacity: 0;
  z-index: -1;
  position: absolute;
  width: 0;
  height: 0;
`
