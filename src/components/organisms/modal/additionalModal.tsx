import React from 'react'
import styled, { keyframes } from 'styled-components'
import CloseIcon from '@mui/icons-material/Close'
import { ReactComponent as CoconetLogo } from '../../assets/images/Logo.svg'
import { Input } from '@mui/material'
import CustomInput from '../../atoms/Input'
import { StyledFlexRowBox } from '../../common/common'

interface Props {
  open: boolean
  handleClose?: () => void
  //   nickname: string
  //   handleFormValue: (e: any) => void
}

const AdditionalModal = ({ open, handleClose }: Props) => {
  if (!open) {
    return null
  }
  return (
    <React.Fragment>
      {/* <Login onClick={() => handleLoginModalVisible()} /> */}
      <StyledLoginModalBg>
        <StyledLoginModalWrapper>
          <StyledLoginModalHeader>
            <CoconetLogo style={{ width: '10rem' }} />
            <CloseIcon
              style={{ color: '#868e96', cursor: 'pointer' }}
              onClick={handleClose}
            />
          </StyledLoginModalHeader>
          <StyledLoginModalContent>
            <StyledLoginTitle>COCONET에 처음오셨군요.</StyledLoginTitle>
            <StyledLoginTitle>닉네임을 설정해주세요!</StyledLoginTitle>
            <StyledContentsWrapper>
              <StyledFlexRowBox>
                <span style={{ minWidth: 'fit-content' }}>닉네임</span>
                <CustomInput />
              </StyledFlexRowBox>
              <div style={{ alignSelf: 'center' }}>
                <StyledFlexRowBox>
                  <StyledButton>다음</StyledButton>
                </StyledFlexRowBox>
              </div>
            </StyledContentsWrapper>
          </StyledLoginModalContent>
        </StyledLoginModalWrapper>
      </StyledLoginModalBg>
    </React.Fragment>
  )
}

export default AdditionalModal

const loginModalAnimation = keyframes`
  0% {
      opacity: 0;
      top: -100%;
    }

    100% {
      opacity: 1;
      top: 50%;
  }
`

const StyledLoginModalBg = styled.div`
  box-sizing: border-box;
  display: block;
  position: fixed;
  inset: 0px;
  z-index: 1000;
  overflow: auto;
  outline: 0px;

  @media (max-width: 768px) {
    width: 100%;
    min-height: 100%;
    position: relative;
  }
`

const StyledLoginModalWrapper = styled.div`
  width: 800px;
  height: 550px;
  box-shadow: 0 2px 12px 0 rgb(0 0 0/9%);
  position: relative;
  display: flex;
  flex-direction: column;
  top: -200%;
  margin: 0 auto;
  transform: translateY(-50%);
  animation: ${loginModalAnimation} 0.5s ease 0s 1 normal forwards running;
`

const StyledLoginModalHeader = styled.div`
  background: #f8f9fa;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 8px 8px 0 0;
  height: 3rem;

  @media (max-width: 768px) {
    background: #f8f9fa;
    padding: 0 1rem;
    border-radius: 0;
  }
`
const StyledLoginModalContent = styled.div`
  background: #fff;
  height: 100%;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 0 0 8px 8px;
`

const StyledLoginTitle = styled.h1`
  margin: 20px 0;

  @media (max-width: 768px) {
    font-size: 1.2rem;
  }
`

const StyledContentsWrapper = styled.section`
  margin-top: 4rem;
  width: 60%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  row-gap: 1rem;

  @media (max-width: 768px) {
    flex-direction: column;
    grid-gap: 30px;
    gap: 30px;
    margin-top: 1.5rem;
  }
`

const StyledButton = styled.button`
  width: 5rem;
  height: 3rem;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  outline: none;
  transition: all 0.125s ease-in 0s;
  color: #fff;
  background-color: black;
  cursor: pointer;
  border: none;

  @media screen and (max-width: 768px) {
    width: 20rem;
    height: 4.5rem;
  }
`
