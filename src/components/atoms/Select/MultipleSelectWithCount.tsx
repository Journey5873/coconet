import * as React from 'react'
import Box from '@mui/material/Box'
import OutlinedInput from '@mui/material/OutlinedInput'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'
import Chip from '@mui/material/Chip'
import styled from 'styled-components'
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline'
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline'
import { useState } from 'react'
import { StackListProps } from '../../../pages/postNew'

const ITEM_HEIGHT = 48
const ITEM_PADDING_TOP = 8
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
}

interface Props {
  stackLists: StackListProps[]
  increaseCount: (stackLabel: string) => void
  decreaseCount: (stackLabel: string) => void
  label: string
  placeholder: string
  disabled: boolean
  isRequired?: boolean
}

export default function MultipleSelectWithCount({
  stackLists,
  increaseCount,
  decreaseCount,
  label,
  placeholder,
  disabled,
  isRequired = false,
}: Props) {
  return (
    <div>
      <FormControl
        disabled={disabled}
        sx={{ m: 1, width: '100%', margin: 0, gap: 1 }}
      >
        <StyledInputLabel id="demo-multiple-chip-label">
          {label}

          {isRequired && <StyledRequired>*</StyledRequired>}
        </StyledInputLabel>
        <Select
          multiple
          placeholder={placeholder}
          value={stackLists.map((stack) => stack.roleName)}
          input={<OutlinedInput id="select-multiple-chip" />}
          renderValue={(selected) => (
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
              {selected.map((value, i) => {
                const isSelected = stackLists.find(
                  (stack) => stack.roleName === value,
                )?.participant
                return (
                  (isSelected as number) > 0 && (
                    <Chip
                      key={i}
                      label={
                        value +
                        ' (' +
                        stackLists.find((stack) => stack.roleName === value)
                          ?.participant +
                        ')'
                      }
                    ></Chip>
                  )
                )
              })}
            </Box>
          )}
          MenuProps={MenuProps}
        >
          {stackLists.map((stack: StackListProps) => (
            <MenuItem
              key={stack.roleName}
              value={stack.roleName}
              style={{ display: 'flex', justifyContent: 'space-between' }}
            >
              <div>{stack.roleName}</div>
              <StyledButtonWrapper>
                <RemoveCircleOutlineIcon
                  onClick={(e) => {
                    e.stopPropagation()
                    decreaseCount(stack.roleName)
                  }}
                >
                  -
                </RemoveCircleOutlineIcon>
                {stack.participant > 0 ? stack.participant : 0}
                <AddCircleOutlineIcon
                  onClick={(e) => {
                    e.stopPropagation()
                    increaseCount(stack.roleName)
                  }}
                >
                  +
                </AddCircleOutlineIcon>
              </StyledButtonWrapper>
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  )
}

const StyledInputLabel = styled.label`
  color: rgb(51, 51, 51);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`

const StyledButtonWrapper = styled.div`
  display: flex;
  gap: 4px;
`

const StyledRequired = styled.span`
  padding-left: 3px;
  color: rgb(234, 114, 111);
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  letter-spacing: -0.28px;
`
