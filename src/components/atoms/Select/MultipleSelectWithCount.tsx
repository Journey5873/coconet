import * as React from 'react'
import { Theme, useTheme } from '@mui/material/styles'
import Box from '@mui/material/Box'
import OutlinedInput from '@mui/material/OutlinedInput'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'
import Chip from '@mui/material/Chip'
import { useSelector, useDispatch } from 'react-redux'
import styled from 'styled-components'
import { decrease, increase } from '../../../store/positionSlice'
import { RootState } from '../../../store/RootReducer'
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline'
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline'

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
  label: string
  placeholder: string
}

interface StackListProps {
  value: string
  label: string
  count: number
  id: number
}

function getStyles(name: string, personName: readonly string[], theme: Theme) {
  return {
    fontWeight:
      personName.indexOf(name) === -1
        ? theme.typography.fontWeightRegular
        : theme.typography.fontWeightMedium,
  }
}

export default function MultipleSelectWithCount({ label, placeholder }: Props) {
  const theme = useTheme()
  const [stackLists, setStackLists] = React.useState<string[]>([])

  const items = useSelector((state: RootState) => state.reducer.position)

  const dispatch = useDispatch()

  const handleChange = (label: string) => {
    setStackLists(
      stackLists.includes(label) ? [...stackLists] : [...stackLists, label],
    )
  }

  return (
    <div>
      <FormControl sx={{ m: 1, width: '100%', margin: 0, gap: 1 }}>
        <StyledInputLabel id="demo-simple-select-label">
          {label}
        </StyledInputLabel>
        <Select
          multiple
          value={stackLists}
          input={<OutlinedInput id="select-multiple-chip" />}
          renderValue={(selected) => (
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
              {selected.map((value) => {
                const isSelected = items.find((item) => item.value === value)
                  ?.count
                return (
                  (isSelected as number) > 0 && (
                    <Chip
                      key={value}
                      label={
                        value +
                        ' (' +
                        items.find((item) => item.value === value)?.count +
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
          {items.map((item: StackListProps, i: number) => (
            <MenuItem
              key={item.value}
              value={item.value}
              style={{ display: 'flex', justifyContent: 'space-between' }}
            >
              <div>{item.value}</div>
              <StyledButtonWrapper>
                <RemoveCircleOutlineIcon
                  onClick={(e) => {
                    e.stopPropagation()
                    dispatch(decrease(items[i].id))
                    handleChange(items[i].label)
                  }}
                >
                  -
                </RemoveCircleOutlineIcon>
                {item.count}
                <AddCircleOutlineIcon
                  onClick={(e) => {
                    e.stopPropagation()
                    dispatch(increase(items[i].id))
                    handleChange(items[i].label)
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
