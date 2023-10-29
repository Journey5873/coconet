import React, { useEffect, useState } from 'react';
import Select from 'react-select';
import styled from 'styled-components';
import { PositionOptions, PositonProps } from '../../assets/data/data';
import { CareerOptions } from '../../assets/data/data';

export interface LabelProps<T extends any> {
  label: '직무' | '경력' | '관심스택';
  onChange: (value: T) => void;
  value: T;
}

export default function SingleSelect<T extends any>({
  label,
  onChange,
  value,
}: LabelProps<T>) {
  const [option, setOption] = useState<PositonProps | any>([]);

  useEffect(() => {
    if (label === '직무') {
      setOption(PositionOptions);
    } else if (label === '경력') {
      setOption(CareerOptions);
    }
  }, [label]);

  return (
    <StyledSelectWrapper>
      <StyledInputLabel id="demo-simple-select-label">
        {label}
        <StyledRequired>*</StyledRequired>
      </StyledInputLabel>
      <StyledSelect
        aria-labelledby="aria-label"
        // defaultValue={[option[0]]}
        inputId="aria-example-input"
        options={option}
        value={value || [option[0]]}
        className="singleSelect"
        onChange={(newValue, _) => onChange(newValue as T)}
      />
    </StyledSelectWrapper>
  );
}

const StyledSelectWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 20px;
  width: 100%;
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
  &.singleSelect .css-1fdsijx-ValueContainer {
    min-height: 48px;
  }
`;
