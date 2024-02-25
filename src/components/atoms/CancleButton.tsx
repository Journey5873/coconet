import React from 'react';

import styled from 'styled-components';

interface Props {
  onClick: () => void;
}

const CancelButton: React.FC<Props> = ({ onClick }) => {
  return <StyledCancelButton onClick={onClick}>X</StyledCancelButton>;
};

export default CancelButton;

const StyledCancelButton = styled.button`
  width: 50px;
  height: 50px;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 50%;
  background-color: transparent;
  &:hover {
    background-color: lightgray;
  }
`;
