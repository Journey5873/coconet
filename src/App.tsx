import React, { useState } from 'react';
import Dday from './components/atoms/Dday';
import Division from './components/atoms/Division';
import Endline from './components/atoms/EndLine';
import MobileSelectBox from './components/molecules/MobileSelectBox';
import LoginModal from './components/atoms/LoginModal';
import SelectBox from './components/organisms/SelectBox';
import styled from 'styled-components';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SocialKakao from './utils/SosialKakao';
import RegisterImage from './components/atoms/RegisterImage';
import SingleSelect from './components/atoms/Select/SingleSelect';
import MultipleSelect from './components/atoms/Select/MultipleSelect';

import CustomCarousel from './components/organisms/Carousel';
import CustomInput from './components/atoms/Input';

function App() {
  const [stackModalVisible, setStackModalVisible] = useState(false);
  const handleStackModalVisible = () => {
    setStackModalVisible(false);
  };

  const [loginModalVisible, setLoginModalVisible] = useState(false);
  const handleLoginModalVisible = () => {
    setLoginModalVisible(false);
  };

  return (
    <div className="App">
      <h1>코코넷</h1>
      <RegisterImage />
      <SingleSelect label={'직무'} />
      <SingleSelect label={'경력'} />
      <MultipleSelect label={'관심스택'} />
      <Dday dDay={7} />
      <Division username={'사계'} registeredDate={'2023.09.12'} />
      <Endline endline={'2023.09.30'} />
      {/* <GoogleAuth /> */}
      <StyledModalButton onClick={() => setStackModalVisible(true)}>
        스택선택 모달
      </StyledModalButton>
      {stackModalVisible && (
        <MobileSelectBox
          handleStackModalVisible={() => handleStackModalVisible()}
        />
      )}
      <StyledModalButton onClick={() => setLoginModalVisible(true)}>
        로그인 모달
      </StyledModalButton>
      {loginModalVisible && (
        <LoginModal handleLoginModalVisible={handleLoginModalVisible} />
      )}
      <BrowserRouter>
        <Routes>
          <Route path="/oauth2/authorize/kakao" element={<SocialKakao />} />{' '}
        </Routes>
      </BrowserRouter>
      {/* <SelectBox xPos={0} yPos={500} /> */}

      <CustomCarousel />
      <CustomInput />
    </div>
  );
}

export default App;

const StyledModalButton = styled.div`
  margin-top: 10px;
  width: 100px;
  box-sizing: border-box;
  justify-content: center;
  min-width: 100px;
  height: 30px;
  border: 1px solid rgb(227, 227, 227);
  color: rgb(100, 100, 100);
  font-weight: 500;
  font-size: 14px;
  letter-spacing: -0.42px;
  display: flex;
  align-items: center;
  border-radius: 36px;
`;
