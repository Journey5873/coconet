import React from 'react';

import { InputProps } from '@mui/material';

import Label, { LabelProps } from '../atoms/Label';

import CustomInput from '../atoms/Input';

import styled from 'styled-components';

const Labelnput: React.FC<InputProps & LabelProps> = ({
  isRequired,
  text,
  onClick,
  value,
  ...rest
}) => {
  return (
    <LabelInputContainer>
      <Label isRequired={isRequired} text={text} />
      <CustomInput onClick={onClick} value={value} />
    </LabelInputContainer>
  );
};

export default Labelnput;

const LabelInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  row-gap: 4px;
`;
