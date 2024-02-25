import React, { useEffect, useState } from 'react'
import Select from 'react-select'
import styled from 'styled-components'
import { SelectProps } from '@mui/material'
import { StackOptions } from '../../assets/data/data'

export interface LabelStringProps {
  label: string
  onChange: (value: any) => void
  value: string[]
  placeholder: string
}

export default function MultipleSelectString({
  label,
  onChange,
  placeholder,
  value,
}: LabelStringProps) {
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
        value={value.map((item) => ({ value: item, label: item }))}
        options={item}
        className="basic-multi-select"
        classNamePrefix="select"
        placeholder={placeholder}
        onChange={onChange}
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
