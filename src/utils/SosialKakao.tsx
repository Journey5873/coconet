import axios from 'axios';
import React, { useEffect } from 'react';

const SocialKakao = () => {
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code'); //인가코드
    const grant_type = 'authorization_code';
    const clident_id = `${process.env.REACT_APP_KAKAO_API_KEY}`;
    const redirect_uri = `${process.env.REACT_APP_KAKAO_REDIRECT_URL}`;

    axios
      .post(
        `https://kauth.kakao.com/oauth/token?grant_type=${grant_type}&client_id=${clident_id}&redirect_uri=${redirect_uri}&code=${code}`,
        {},
        {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
          },
        }
      )
      .then((res) => {
        console.log(res)
      })
  }, []);
  
    return(
    <React.Fragment></React.Fragment>
    )
}
export default SocialKakao