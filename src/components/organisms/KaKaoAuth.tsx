import React, { useEffect, useState } from 'react';
import {ReactComponent as KakaoLogo} from '../assets/images/kakaoLogo.svg'
import styled from 'styled-components';

const KaKaoAuth = () => {
    const REACT_APP_KAKAO_REST_API_KEY = process.env.REACT_APP_KAKAO_REST_API_KEY;
  const KAKAO_REDIRECT_URL = process.env.REACT_APP_KAKAO_REDIRECT_URL; //Redirect URI
  
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${KAKAO_REDIRECT_URL}&response_type=code`
    const handleLogin = ()=>{
        window.location.href = kakaoURL
    }

    return (
        <StyledButton style={{backgroundColor:'#272e33'}}>
                  <KakaoLogo onClick={handleLogin} />
      </StyledButton>
    );
};

export default KaKaoAuth;


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
    background-color: #fae100;

    @media screen and (max-width: 768px)  {
    width: 20rem;
    height: 4.5rem;
  }
`