import { ButtonHTMLAttributes } from 'react';

import styled from 'styled-components';

interface Props {
  buttonName: string;
}

export default function GreenButton({
  buttonName,
  ...rest
}: Props & ButtonHTMLAttributes<HTMLButtonElement>) {
  return <StyledGreenButton {...rest}>{buttonName}</StyledGreenButton>;
}

const StyledGreenButton = styled.button`
  box-sizing: border-box;
  border: none;
  display: flex;
  width: 100%;
  height: 44px;
  padding: 10px;
  justify-content: center;
  align-items: center;
  gap: 5px;
  color: rgb(255, 255, 255);
  font-size: 16px;
  font-weight: 700;
  line-height: 126.5%;
  letter-spacing: -0.51px;
  border-radius: 50px;
  background: rgb(110, 209, 192);
`;
