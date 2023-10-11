import React from 'react';

import { LabelHTMLAttributes, DetailedHTMLProps } from 'react';

import styled from 'styled-components';

interface Props
  extends DetailedHTMLProps<
    LabelHTMLAttributes<HTMLLabelElement>,
    HTMLLabelElement
  > {
  isRequired: boolean;
  text: string;
}

const Label: React.FC<Props> = ({ isRequired, text, ...rest }) => {
  return (
    <label {...rest}>
      <StyledLabel>
        <span>{text}</span>
        {isRequired && <StyledRequired>*</StyledRequired>}
      </StyledLabel>
    </label>
  );
};

export default Label;

const StyledLabel = styled.div`
  display: flex;
  flex-direction: row;
  column-gap: 3px;
  justify-content: center;
  align-items: center;
`;

const StyledRequired = styled.span`
  color: red;
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
`;
