import React from 'react';
import Dday from './components/atoms/Dday';
import Division from './components/atoms/Division';
import Endline from './components/atoms/EndLine';
import MobileSelectBox from './components/organisms/MobileSelectBox';
import LoginModal from './components/organisms/LoginModal';
import SelectBox from './components/organisms/SelectBox';

function App() {
  return (
    <div className="App">
      <h1>코코넷</h1>
      <Dday dDay={7} />
      <Division username={'사계'} registeredDate={'2023.09.12'} />
      <Endline endline={'2023.09.30'} />
      <MobileSelectBox />
      {/* <GoogleAuth /> */}
      <LoginModal isOpen={false} onClose={() => {}} />
      <SelectBox xPos={0} yPos={500} />
    </div>
  );
}

export default App;
