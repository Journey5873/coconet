import React, { useState, useMemo } from 'react'

import { skillData } from '../../../data/data'

import { imageMap } from '../../../utils/utils'

import { ImCancelCircle } from 'react-icons/im'

import useClose from '../../../hooks/useClose'

import Tabs from '../customTabs/Tabs'

import Tab from '../customTabs/Tab'

import styled from 'styled-components'

import SpecButton from '../../atoms/Button'

interface Props {
  handleSelected: (value: string) => void
  selected: string[]
}

const MultiStackSelector = ({ handleSelected, selected }: Props) => {
  const { ref } = useClose({ callBack: () => setIsShow(false) })
  const [isShow, setIsShow] = useState<boolean>(false)
  const isSelected = useMemo(() => selected.length > 0, [selected.length])
  return (
    <StyledContainer ref={ref}>
      <div>
        <StyledSelectButton onClick={() => setIsShow((prev) => !prev)}>
          <span>기술 스택</span>
          <span style={{ transform: 'rotate(90deg)' }}>{'>'}</span>
        </StyledSelectButton>
      </div>
      <StyledPopupWrapper isShow={isShow}>
        <Tabs
          options={[
            { name: '전체' },
            { name: '프론트엔드' },
            { name: '백엔드' },
            { name: '모바일' },
            { name: '기타' },
          ]}
        >
          <Tab value="전체">
            <StyledStackWrapper>
              {skillData.all.map((skill, index) => (
                <StyledSpecButton
                  key={index}
                  isSelected={isSelected && selected.indexOf(skill.value) < 0}
                >
                  <SpecButton
                    label={skill.label}
                    iconUrl={imageMap[skill.label]}
                    onClick={() => handleSelected(skill.value)}
                  />
                </StyledSpecButton>
              ))}
            </StyledStackWrapper>
          </Tab>
          <Tab value="프론트엔드">
            <StyledStackWrapper>
              {skillData.frontend.map((skill, index) => (
                <StyledSpecButton
                  key={index}
                  isSelected={isSelected && selected.indexOf(skill.value) < 0}
                >
                  <SpecButton
                    label={skill.label}
                    iconUrl={imageMap[skill.label]}
                    onClick={() => handleSelected(skill.value)}
                  />
                </StyledSpecButton>
              ))}
            </StyledStackWrapper>
          </Tab>
          <Tab value="백엔드">
            <StyledStackWrapper>
              {skillData.backend.map((skill, index) => (
                <StyledSpecButton
                  key={index}
                  isSelected={isSelected && selected.indexOf(skill.value) < 0}
                >
                  <SpecButton
                    label={skill.label}
                    iconUrl={imageMap[skill.label]}
                    onClick={() => handleSelected(skill.value)}
                  />
                </StyledSpecButton>
              ))}
            </StyledStackWrapper>
          </Tab>
          <Tab value="모바일">
            <StyledStackWrapper>
              {skillData.mobile.map((skill, index) => (
                <StyledSpecButton
                  key={index}
                  isSelected={isSelected && selected.indexOf(skill.value) < 0}
                >
                  <SpecButton
                    label={skill.label}
                    iconUrl={imageMap[skill.label]}
                    onClick={() => handleSelected(skill.value)}
                  />
                </StyledSpecButton>
              ))}
            </StyledStackWrapper>
          </Tab>
          <Tab value="기타">
            <StyledStackWrapper>
              {skillData.etc.map((skill, index) => (
                <StyledSpecButton
                  key={index}
                  isSelected={isSelected && selected.indexOf(skill.value) < 0}
                >
                  <SpecButton
                    label={skill.label}
                    iconUrl={imageMap[skill.label]}
                    onClick={() => handleSelected(skill.value)}
                  />
                </StyledSpecButton>
              ))}
            </StyledStackWrapper>
          </Tab>
        </Tabs>
        <div
          style={{
            display: 'flex',
            columnGap: '8px',
            flexWrap: 'wrap',
            alignItems: 'center',
          }}
        >
          {selected?.map((skill, index) => (
            <StyledSelectedButton key={index}>
              <span>{skill}</span>
              <ImCancelCircle onClick={() => handleSelected(skill)} />
            </StyledSelectedButton>
          ))}
        </div>
      </StyledPopupWrapper>
    </StyledContainer>
  )
}

export default MultiStackSelector

const StyledContainer = styled.div`
  position: relative;
`

const StyledPopupWrapper = styled.div<{
  isShow: boolean
}>`
  position: absolute;
  top: 100%;
  left: 0;
  margin-top: 8px;
  width: 900px;
  height: auto;
  padding: 20px 32px;
  border: 1px solid lightgray;
  border-radius: 20px;
  display: ${(props) => (props.isShow ? 'block' : 'none')};
  z-index: 10;
  background-color: white;
`

const StyledStackWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 8px;
  flex-wrap: wrap;
`

const StyledSpecButton = styled.div<{
  isSelected: boolean
}>`
  opacity: ${(props) => (props.isSelected ? '0.5' : '1')};
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
const StyledSelectedButton = styled.div`
  padding: 4px 16px;
  background-color: lightgray;
  display: flex;
  column-gap: 8px;
  align-items: center;
  border-radius: 20px;
`
