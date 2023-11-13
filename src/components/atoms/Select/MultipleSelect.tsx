import React from 'react';
import { useSelector } from 'react-redux';

import Select from 'react-select';
import { RootState } from '../../../store/RootReducer';
import { LabelProps } from './SingleSelect';
import styled from 'styled-components';

export default function MultipleSelect<T extends any>({
  label,
  onChange,
}: LabelProps<T>) {
  const item = useSelector((state: RootState) => state.reducer.position);

  return (
    <StyledLabelAndSelect>
      <StyledInputLabel id="demo-simple-select-label">
        {label}
        <StyledRequired>*</StyledRequired>
      </StyledInputLabel>
      <StyledSelect
        isMulti
        name="colors"
        options={item}
        className="basic-multi-select"
        classNamePrefix="select"
        placeholder="Select..."
        onChange={(newValue) => onChange(newValue as any)}
      />
    </StyledLabelAndSelect>
  );
}

const StyledLabelAndSelect = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  margin-bottom: 20px;
`;

const StyledInputLabel = styled.label`
  color: rgb(51, 51, 51);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`;

const StyledRequired = styled.span`
  padding-left: 3px;
  color: rgb(234, 114, 111);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`;

const StyledSelect = styled(Select)`
  &.basic-multi-select {
    .select__control {
      min-height: 48px;
    }
    .select__value-container .select__multi-value {
      font-weight: 550;
    }
    .select__indicators .select__indicator-separator {
      display: none;
    }
    .select__menu .select__menu-list .select__option {
      font-weight: 550;
    }
  }
`;
