import { useState } from 'react'
import styled from 'styled-components'
import useClose from '../../../hooks/useClose'

interface Props {
  title: string
  options: string[]
  selected: string
  handleSelected: (value: string) => void
}

const FilterSelect = ({ title, options, selected, handleSelected }: Props) => {
  const { ref } = useClose({ callBack: () => setIsShow(false) })
  const [isShow, setIsShow] = useState<boolean>(false)

  const handleClickOptionItem = (value: string) => {
    handleSelected(value)
    setIsShow(false)
  }
  return (
    <StyledContainer ref={ref}>
      <div>
        <StyledSelectButton onClick={() => setIsShow((prev) => !prev)}>
          <span>{selected || title}</span>
          <span style={{ transform: 'rotate(90deg)' }}>{'>'}</span>
        </StyledSelectButton>
      </div>
      <StyledPopupWrapper isShow={isShow}>
        {options.map((item, index) => (
          <StyledOptionItem
            key={index}
            onClick={() => handleClickOptionItem(item)}
          >
            {item}
          </StyledOptionItem>
        ))}
      </StyledPopupWrapper>
    </StyledContainer>
  )
}

export default FilterSelect

const StyledContainer = styled.div`
  position: relative;
`

const StyledPopupWrapper = styled.div<{
  isShow: boolean
}>`
  position: absolute;
  top: 100%;
  left: 0;
  display: flex;
  flex-direction: column;
  gap: 32px;
  margin-top: 8px;
  width: 120px;
  padding: 16px;
  min-height: fit-content;
  border: 1px solid #8caf8e;
  border-radius: 20px;
  display: ${(props) => (props.isShow ? 'block' : 'none')};
  z-index: 10;
  background-color: white;
`

const StyledOptionItem = styled.div`
  padding: 4px 8px;
  border-radius: 20px;
  &:hover {
    background-color: lightgray;
  }
`

const StyledSelectButton = styled.div`
  width: 140px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 1rem;
  border: 1px solid lightgray;
  border-radius: 20px;
`
