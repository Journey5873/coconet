import React, { useEffect, useState } from 'react'
import Select from 'react-select'
import styled from 'styled-components'
import {
  CategoryOptions,
  OnOfflineOptions,
  PeriodOptions,
  PersonnelOptions,
  PositionOptions,
  SelectProps,
} from '../../assets/data/data'
import { CareerOptions } from '../../assets/data/data'

export interface LabelStringProps {
  label: string
  onChange: (value: any) => void
  value: string
  placeholder: string
}

export default function SingleSelectString({
  label,
  onChange,
  placeholder,
  value,
}: LabelStringProps) {
  const [option, setOption] = useState<SelectProps[]>([])

  useEffect(() => {
    if (label === '직무') {
      setOption(PositionOptions)
    } else if (label === '경력') {
      setOption(CareerOptions)
    } else if (label === '모집구분') {
      setOption(CategoryOptions)
    } else if (label === '모집 인원') {
      setOption(PersonnelOptions)
    } else if (label === '진행 방식') {
      setOption(OnOfflineOptions)
    } else if (label === '진행 기간') {
      setOption(PeriodOptions)
    }
  }, [])

  return (
    <StyledSelectWrapper className="singleSelect">
      <StyledInputLabel id="demo-simple-select-label">
        {label}
        <StyledRequired>*</StyledRequired>
      </StyledInputLabel>
      <StyledSelect
        value={{ value, label: value }}
        inputId="aria-example-input"
        options={option}
        className="singleSelect"
        placeholder={placeholder}
        onChange={onChange}
      />
    </StyledSelectWrapper>
  )
}

const StyledSelectWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 20px;
  width: 100%;
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
  flex: 1;
  &.singleSelect .css-1fdsijx-ValueContainer {
    min-height: 48px;
  }
`
