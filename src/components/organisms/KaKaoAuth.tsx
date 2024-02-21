import { ReactComponent as KakaoLogo } from '../assets/images/kakaoLogo.svg'
import styled from 'styled-components'

const KaKaoAuth = () => {
  const kakaoURL = `http://localhost:8000/member-service/open-api/oauth2/authorize/kakao`
  const handleLogin = () => {
    // window.open(kakaoURL, '_target')
    window.location.href = kakaoURL
  }

  return (
    <StyledButton onClick={handleLogin} style={{ backgroundColor: '#fae100' }}>
      <KakaoLogo />
    </StyledButton>
  )
}

export default KaKaoAuth

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
  background-color: #fae100;

  @media screen and (max-width: 768px) {
    width: 20rem;
    height: 4.5rem;
  }
`
