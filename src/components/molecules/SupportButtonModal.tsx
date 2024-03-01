import { useContext, useEffect, useState } from 'react'
import CloseIcon from '@mui/icons-material/Close'
import { ReactComponent as CoconetLogo } from '../assets/images/Logo.svg'
import styled from 'styled-components'
import { AlertContext } from '../organisms/modal/AlertModalContext'
import { useNavigate } from 'react-router-dom'
import { useAppSelector } from '../../store/RootReducer'
import { toast } from 'react-toastify'
import { ChatService, useChatService } from '../../api/services/chatService'
import { ChatDto } from '../../models/chat'

interface SupportButtonModalProps {
  handleSupportButton: () => void
  isVisible: boolean
}

export default function SupportButtonModal({
  handleSupportButton,
  isVisible,
}: SupportButtonModalProps) {
  const chatService = useChatService()
  const navigate = useNavigate()
  const token = useAppSelector((state) => state.reducer.auth.token)

  const handleNextModal = async () => {
    const confirm = window.confirm('메세지를 보내시겠습니까?')
    if (confirm) {
      try {
        const requestDto: ChatDto = {
          articleUUID: '50ba8300-d1b5-4cd8-b8fc-87271aebe71b',
          roomName: 'room1',
          writerUUID: '6a2e74dc-beab-4d68-99fb-c15cc48dd455',
        }
        const result = await chatService.createChatRoom(
          JSON.stringify(requestDto),
        )

        if (result.succeeded) {
          toast.success('지원이 완료되었습니다!')
          navigate('/chat')
        }
      } catch (error) {
        console.log(error)
        toast.error('다시 시도해 주세요.')
      }
    }
  }

  return (
    <>
      {isVisible && (
        <>
          <StyledGrayBackground />
          <StyledModalWrapper>
            <StyledLoginModalHeader>
              <CoconetLogo style={{ width: '10rem' }} />
              <CloseIcon
                style={{ color: '#868e96', cursor: 'pointer' }}
                onClick={() => handleSupportButton()}
              />
            </StyledLoginModalHeader>
            <StyledContentWrapper>
              <div style={{ textAlign: 'center' }}>
                <StyledSetSelfIntroduce>
                  보내실 첫 메세지를 입력해주세요!
                </StyledSetSelfIntroduce>
                <StyledTextArea name="info" id="info"></StyledTextArea>
                <StyledSupportButton onClick={handleNextModal}>
                  전송
                </StyledSupportButton>
              </div>
            </StyledContentWrapper>
          </StyledModalWrapper>
        </>
      )}
    </>
  )
}

const StyledGrayBackground = styled.div`
  box-sizing: border-box;
  display: block;
  position: fixed;
  inset: 0px;
  background: rgba(77, 77, 77, 0.5);
  z-index: 999;
`

const StyledLoginModalHeader = styled.div`
  background: #f8f9fa;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 8px 8px 0 0;
  width: 100%;
  height: 3rem;
  box-sizing: border-box;

  @media (max-width: 768px) {
    background: #f8f9fa;
    padding: 0 1rem;
    border-radius: 0;
  }
`

const StyledModalWrapper = styled.div`
  width: 700px;
  height: 450px;
  position: fixed;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 8px;
  /* background: #B0DEDB; */
  background: #fff;
`

const StyledSetSelfIntroduce = styled.h2`
  margin-top: 4rem;
  text-align: center;
  margin-bottom: 32px;
`

const StyledTextArea = styled.textarea`
  height: 134px;
  width: 87%;
  padding: 15px 13px;
  border-radius: 5px;
  border: 1px solid rgb(225, 226, 227);
  resize: none;
  font-size: 16px;
  font-style: normal;
  font-weight: 400;
  outline: none;
  line-height: 150%;
  box-sizing: border-box;
`

const StyledContentWrapper = styled.div`
  padding: 1.5rem;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
`

const StyledSupportButton = styled.button`
  margin-top: 2.5rem;
  font-weight: 700;
  color: #fff;
  border-radius: 4px;
  width: 6rem;
  height: 2.5rem;
  font-size: 1.25rem;
  background-color: #262626;
  cursor: pointer;
`

const StyledSelectPositionTitle = styled.h2`
  margin-bottom: 32px;
`

const StyledSelectPositionWrapper = styled.div`
  padding: 0 60px;
  text-align: left;
`
