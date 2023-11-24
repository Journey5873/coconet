import React from 'react'
import styled, { keyframes } from 'styled-components'
import CloseIcon from '@mui/icons-material/Close'
import { ReactComponent as GoogleLogo } from '../assets/images/googleLogo.svg'
import { ReactComponent as GithubLogo } from '../assets/images/githubLogo.svg'
import { ReactComponent as CoconetLogo } from '../assets/images/Logo.svg'
import KakaoAuth from '../organisms/KaKaoAuth'

interface Props {
  handleLoginModalVisible: () => void
}

// 깃헙 로그인
const clientId = '3e638ae1e9bacaed52ec'
const redirectUrl = 'http://localhost:3000'
const githubURL = `https://github.com/login/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUrl}`
const handleLogin = () => {
  window.location.href = githubURL
}

export default function LoginModal({ handleLoginModalVisible }: Props) {
  return (
    <React.Fragment>
      <Login onClick={() => handleLoginModalVisible()} />
      <StyledLoginModalBg>
        <StyledLoginModalWrapper>
          <StyledLoginModalHeader>
            <CoconetLogo style={{ width: '10rem' }} />
            <CloseIcon
              style={{ color: '#868e96', cursor: 'pointer' }}
              onClick={() => handleLoginModalVisible()}
            />
          </StyledLoginModalHeader>
          <StyledLoginModalContent>
            <StyledLoginTitle>COCONET에 오신 것을 환영합니다!</StyledLoginTitle>
            <StyledLoginWrapper>
              <StyledBottonWrapper>
                <StyledButton>
                  <GoogleLogo />
                </StyledButton>
                <StyledButtonDescription href="http://localhost:9091/oauth2/authorize/google">
                  Google 로그인
                </StyledButtonDescription>
              </StyledBottonWrapper>
              <StyledBottonWrapper>
                <StyledButton style={{ backgroundColor: '#272e33' }}>
                  <GithubLogo onClick={handleLogin} />
                </StyledButton>
                <StyledButtonDescription>Github 로그인</StyledButtonDescription>
              </StyledBottonWrapper>
              <StyledBottonWrapper>
                <KakaoAuth />
                <StyledButtonDescription>Kakao 로그인</StyledButtonDescription>
              </StyledBottonWrapper>
            </StyledLoginWrapper>
          </StyledLoginModalContent>
        </StyledLoginModalWrapper>
      </StyledLoginModalBg>
    </React.Fragment>
  )
}

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

const Login = styled.div`
  box-sizing: border-box;
  display: block;
  position: fixed;
  inset: 0px;
  background: rgba(77, 77, 77, 0.5);
  z-index: 999;
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

const StyledLoginWrapper = styled.section`
  margin-top: 4rem;
  width: 60%;
  display: flex;
  justify-content: space-between;
  align-items: center;

  @media (max-width: 768px) {
    flex-direction: column;
    grid-gap: 30px;
    gap: 30px;
    margin-top: 1.5rem;
  }
`

const StyledBottonWrapper = styled.div`
  display: flex;
  flex-direction: column;
`

const StyledButton = styled.button`
  width: 8rem;
  height: 8rem;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  outline: none;
  transition: all 0.125s ease-in 0s;
  color: #fff;
  box-shadow: 0 5px 25px rgba(0, 0, 0, 0.15);
  background-color: #fff;
  cursor: pointer;
  border: none;

  @media screen and (max-width: 768px) {
    width: 20rem;
    height: 4.5rem;
  }
`
const StyledButtonDescription = styled.a`
  margin-top: 10px;
  font-weight: 700;
  font-size: 1rem;
  line-height: 126.5%;
  text-align: center;
  letter-spacing: -0.005em;
  color: #565656;
  text-decoration: none;
`
