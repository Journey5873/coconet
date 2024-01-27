import React from 'react'

import { Input, InputProps } from '@mui/material'

import styled from 'styled-components'

const CustomInput: React.FC<InputProps> = (props) => {
  return <StyledInput {...props} />
}

export default CustomInput

const StyledInput = styled(Input)`
  width: 100%;
  min-height: 48px;
  align-items: center;
  background-color: white;
  border: 1px solid lightgray;
  transition: all 100ms ease 0s;
  box-sizing: border-box;
  outline: 0px !important;
  padding-left: 8px;

  &:focus {
    outline: 1px;
    border: 1px solid dodgerblue;
  }
`
