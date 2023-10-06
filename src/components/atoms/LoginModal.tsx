import React from "react";
import styled from "styled-components"
import CloseIcon from '@mui/icons-material/Close';
import {ReactComponent as GoogleLogo} from '../assets/images/googleLogo.svg';
import {ReactComponent as GithubLogo} from '../assets/images/githubLogo.svg'
import { ReactComponent as KakaoLogo } from '../assets/images/kakaoLogo.svg';

interface Props {
  handleLoginModalVisible: () => void;
}

const loginWithKakao = () => {
   const KAKAO_API_KEY = `${process.env.REACT_APP_KAKAO_API_KEY}`;
  const KAKAO_REDIRECT_URL = `${process.env.REACT_APP_KAKAO_REDIRECT_URL}`; //Redirect URI

  // oauth 요청 URL
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_API_KEY}&redirect_uri=${KAKAO_REDIRECT_URL}&response_type=code`;
  window.location.href = kakaoURL;
  
}
export default function LoginModal({handleLoginModalVisible}:Props) {
  return (
    <React.Fragment>
      <Login onClick={() => handleLoginModalVisible()} />
      <StyledLoginModalBg >
        <StyledLoginModalWrapper>
          <StyledLoginModalHeader>
            <CloseIcon style={{color: '#868e96',cursor:'pointer'}} onClick={() => handleLoginModalVisible()} />
          </StyledLoginModalHeader>
          <StyledLoginModalContent>
            <StyledLoginTitle>COCONET에 오신 것을 환영합니다!</StyledLoginTitle>
            <StyledLoginWrapper>
              <StyledBottonWrapper>
                <StyledButton>
                  <GoogleLogo />
                </StyledButton>
                <StyledButtonDescription>Goggle 로그인</StyledButtonDescription>
              </StyledBottonWrapper>
              <StyledBottonWrapper>
                <StyledButton style={{backgroundColor:'#272e33'}}>
                  <GithubLogo />
                </StyledButton>
                <StyledButtonDescription>Github 로그인</StyledButtonDescription>
              </StyledBottonWrapper>
              <StyledBottonWrapper>
                <StyledButton onClick={() => loginWithKakao()} style={{backgroundColor:'#fae100'}}>
                  <KakaoLogo />
                </StyledButton>
                <StyledButtonDescription>Kakao 로그인</StyledButtonDescription>
              </StyledBottonWrapper>
            </StyledLoginWrapper>
          </StyledLoginModalContent>
        </StyledLoginModalWrapper>
      </StyledLoginModalBg>  
    </React.Fragment>
    )
}

const Login = styled.div`
box-sizing: border-box;
    display: block;
    position: fixed;
    inset: 0px;
    background: rgba(77, 77, 77, 0.5);
    z-index: 999;
`

const StyledLoginModalBg = styled.div`
    margin : 0 auto;
    box-sizing: border-box;
    display: block;
    position: fixed;
    inset: 0px;
    z-index: 1000;
    overflow: auto;
    outline: 0px;
    width: 800px;
    height: 550px;

    @media (max-width: 768px) {
      width : 100%;
      min-height : 100%;
      position: relative;
    }
`;

const StyledLoginModalWrapper = styled.div`
  width:100%;
  height : 100%;
    box-shadow: 0 2px 12px 0 rgb(0 0 0/9%);
    position: relative;
    display: flex;
    flex-direction: column;
    margin: 0 auto;
  `

const StyledLoginModalHeader = styled.div`
    background: #f8f9fa;
    padding: 0 1rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-radius: 8px 8px 0 0;
    height: 3rem;

    @media (max-width: 768px){
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
    transition: all .125s ease-in 0s;
    color: #fff;
    box-shadow: 0 5px 25px rgba(0,0,0,.15);
    background-color : #fff;
    cursor: pointer;
    border: none;

    @media screen and (max-width: 768px)  {
    width: 20rem;
    height: 4.5rem;
  }
`
const StyledButtonDescription = styled.p`
  margin-top: 10px;
    font-weight: 700;
    font-size: 1rem;
    line-height: 126.5%;
    text-align: center;
    letter-spacing: -.005em;
    color: #565656;
`