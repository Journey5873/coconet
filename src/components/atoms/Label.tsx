import React from 'react';

import { LabelHTMLAttributes, DetailedHTMLProps } from 'react';

import styled from 'styled-components';

export interface LabelProps
  extends DetailedHTMLProps<
    LabelHTMLAttributes<HTMLLabelElement>,
    HTMLLabelElement
  > {
  isRequired: boolean;
  text: string;
}

const Label: React.FC<LabelProps> = ({ isRequired, text, ...rest }) => {
  return (
    <label {...rest}>
      <StyledLabel>
        <StyledSpan>{text}</StyledSpan>
        {isRequired && <StyledRequired>*</StyledRequired>}
      </StyledLabel>
    </label>
  );
};

export default Label;

const StyledLabel = styled.div`
  display: flex;
  flex-direction: row;
  column-gap: 4px;
  justify-content: center;
  align-items: center;
`;

const StyledSpan = styled.span`
  font-weight: 700;
  line-height: 20px;
`;

const StyledRequired = styled(StyledSpan)`
  color: red;
  font-size: 14px;
`;
