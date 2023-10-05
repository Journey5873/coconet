import React from 'react';

import styled from 'styled-components';
import GoogleAuth from './GoogleAuth';
import CancelButton from '../atoms/CancleButton';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

const LoginModal: React.FC<Props> = ({ isOpen, onClose }) => {
  if (!isOpen) {
    return null;
  }
  return (
    <StyledModalContainer>
      <StyledModalContent>
        {/* 추가 예정 */}
        <button>카카오 로그인</button>
        <GoogleAuth />
        <StyledCancleBtn>
          <CancelButton onClick={onClose} />
        </StyledCancleBtn>
      </StyledModalContent>
    </StyledModalContainer>
  );
};

export default LoginModal;

const StyledModalContainer = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.2);
  position: fixed;
  top: 0;
  left: 0;
`;

const StyledModalContent = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 500px;
  height: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  border-radius: 20px;
`;

const StyledCancleBtn = styled.div`
  position: absolute;
  top: 16px;
  right: 16px;
`;
