import Loader from '../atoms/Loader';
import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import axios, { AxiosResponse } from 'axios';
import { Router, useNavigate } from 'react-router-dom';

const KakaoCallBack = () => {
    const [valid, setValid] = useState(false);
    const REACT_APP_KAKAO_REST_API_KEY = process.env.REACT_APP_KAKAO_REST_API_KEY;
    const KAKAO_REDIRECT_URL = process.env.REACT_APP_KAKAO_REDIRECT_URL; //Redirect URI
    const code = new URL(document.location.toString()).searchParams.get("code");
    const dispatch = useDispatch(); 
    const navigate = useNavigate();

    useEffect(() => {
    //////////////////////////////////////////////////////////////
    //       백엔드로부터 인가코드 넘기고 jwt 토큰 받기
    ////////////////////////////////////////////////////////////
    (async () => {
      try {
        const res = await axios
          // 백엔드 주소 뒤에 인가코드 붙여서 GET 설정
          // 백엔드는 이 주소를 통해 뒤에 붙여져있는 인가코드를 전달 받게 된다.
          .get(
            `http://localhost:9091/oauth2/authorize/kakao?code=${code}`
          )
          // 백엔드 쪽에서 보내준 응답 확인
          .then((response) => {
            console.log("응답 확인", response);
            // 이때,
            // 백엔드로부터 받아온 헤더값에 저장되어있는 authorization 을 접근해 token 이라는 변수에 저장
            const token = response.headers.authorization;
            // 이 토큰은 프론트엔드, 즉 현재 내 서버에 저장시킨다.
            window.localStorage.setItem("token", token);
          });
        console.log(res);
      } catch (e) {
        // 에러 발생 시, 에러 응답 출력
        console.error(e);
      }

      // 위에서 setItem 을 사용하여 내부에 저장시킨 토크을 다시 불러온다.
      // 이때, 내부 저장소에서 가져온 토큰을 다시 token 이라는 변수에 담는다.
      const token = window.localStorage.getItem("token");
      ////////////////////////////////////////////////////////////
      //               백엔드로 토큰 다시 넘기기
      ////////////////////////////////////////////////////////////
      try {
        const res = await axios
          // 이때, post가 아닌 get으로 접근한다.
          // 접근 주소는 백엔드에서 설정한 주소로 한다.
          .get(
            "http://localhost:9091/oauth2/authorize/kakao",
            {
              // 헤더값에는 받아온 토큰을 Authorization과 request 에 담아서 보낸다/
              headers: {
                Authorization: token,
                request: token,
              },
            }
          )
          // 위에서 백엔드가 토큰을 잘받고 처리해서 유저정보를 다시 넘겨준다면, 그 응답을 처리한다.
          // data 라는 변수에 유저 정보를 저장하고, setItem을 사용해 로컬에 다시 저장한다.
            .then((data: AxiosResponse<any, any>) => {
                if (data) {
                    // window.localStorage.setItem("profile", data);
              }
            // 만약, 유저정보를 잘 불러왔다면 navigate를 사용해 프론트엔드에서 설정한 마이페이지 경로를 설정해서 이동시킨다.
            if (data) {
              navigate("/");
            }
          });
      } catch (e) {
        // 에러 발생 시, 에러 응답 출력
        console.error(e);
      }
    })();
  }, []);




    //현재 url의 파라미터를 가져옴
    // useEffect(() => {
    //     axios
    //         .post(
    //             `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${REACT_APP_KAKAO_REST_API_KEY}&redirect_uri=${KAKAO_REDIRECT_URL}&code=${code}`,
    //             {},
    //             {
    //                 headers: {
    //                     "Content-Type":
    //                         "application/x-www-form-urlencoded;charset=utf-8",
    //                 },
    //             }
    //         ).then((res) => {
    //             const { data } = res;
    //             const { access_token } = data;
    //             if (access_token) {
    //                 console.log(access_token);

    //             }
    //         })
    // }, [])

    
    return (
        <Loader />
    )
}

export default KakaoCallBack;