import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8000/member-service/open-api/github?code=https://epocle.shop",
});

// 유저 관련 API
export const userApi = {
  //kakao 로그인
  kakaoLogin: (code:string) => api.get(`/auth/kakao?code=${code}`),

  //회원 탈퇴
//   userSecession: () =>
//     api.delete(`/api/user`, {
//       headers: {
//         Authorization: `Bearer ${sessionStorage.getItem("token")}`,
//       },
//     }),

  //초기 세팅정보 보내기
//   settingsData: (Settings_info) =>
//     api.post(`/api/user/initial`, Settings_info, {
//       headers: {
//         Authorization: `Bearer ${sessionStorage.getItem("token")}`,
//       },
//     }),

//   //유저 초기 세팅정보 받아오기
//   userInfo: () =>
//     api.get(`/api/user/initial`, {
//       headers: {
//         Authorization: `Bearer ${sessionStorage.getItem("token")}`,
//       },
//     }),

//   //유저 정보 업데이트
//   updateSettings: (Update_info) =>
//     api.put(`/api/user/initial`, Update_info, {
//       headers: {
//         Authorization: `Bearer ${sessionStorage.getItem("token")}`,
//         "Content-Type":
//           "multipart/form-data; boundary=----WebKitFormBoundaryfApYSlK1ODwmeKW3",
//       },
//     }),
//   //유저 정보 체크
//   userCheck: () =>
//     api.get(`/api/user`, {
//       headers: {
//         Authorization: `Bearer ${sessionStorage.getItem("token")}`,
//       },
//     }),
//   //유저 신고 기능
//   userReport: (Report_info) =>
//     api.post(`/api/user/report`, Report_info, {
//       headers: {
//         Authorization: `Bearer ${sessionStorage.getItem("token")}`,
//       },
//     }),
};