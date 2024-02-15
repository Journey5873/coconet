import React, { useEffect, useMemo, useState } from 'react'
import { useSelector } from 'react-redux'

import Select from 'react-select'
import { RootState } from '../../../store/RootReducer'
import { LabelProps } from './SingleSelect'
import styled from 'styled-components'
import { SelectProps } from '@mui/material'
import { StackOptions } from '../../assets/data/data'

export default function MultipleSelect({
  label,
  onChange,
  placeholder,
  value,
}: LabelProps) {
  const [item, setItem] = useState<SelectProps[]>([])

  useEffect(() => {
    if (label === '관심스택' || '기술 스택') {
      setItem(StackOptions)
    }
  }, [])

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
        placeholder={placeholder}
        onChange={(newValue) => onChange(newValue)}
      />
    </StyledLabelAndSelect>
  )
}

const StyledLabelAndSelect = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  margin-bottom: 20px;
`

const StyledInputLabel = styled.label`
  color: rgb(51, 51, 51);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`

const StyledRequired = styled.span`
  padding-left: 3px;
  color: rgb(234, 114, 111);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`

const StyledSelect = styled(Select)`
  &.basic-multi-select {
    .select__control {
      min-height: 48px;
    }
    .select__indicators .select__indicator-separator {
      display: none;
    }
  }
`
