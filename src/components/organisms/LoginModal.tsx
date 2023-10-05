import React from 'react';

import styled from 'styled-components';
import GoogleAuth from './GoogleAuth';

const LoginModal = () => {
    return (
        <StyledModalContainer>
            <StyledModalContent>
                {/* 추가 예정 */}
                <button>카카오 로그인</button>
                <GoogleAuth />
            </StyledModalContent>
        </StyledModalContainer>
    );
};

export default LoginModal;

const StyledModalContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-color: lightgray;
    position: relative;
`;

const StyledModalContent = styled.div`
    position: absolute;
    top: 0;
    left: 0;
    transform: translate(-50%, -50%);
    width: 800px;
    height: 500px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;
`;
