import React, { ReactNode, useState, PropsWithChildren } from 'react'

import { TabProvider, TabsContextProviderProps } from './useTabContext'

import styled from 'styled-components'

interface TabItem {
  icon?: any
  name: string
}

interface Props {
  options: TabItem[]
}

const Tabs: React.FC<PropsWithChildren<Props>> = ({ options, children }) => {
  const [currentIndex, setCurrentIndex] = useState<number>(0)
  const [activeValue, setActiveValue] = useState<string>(options[0].name)

  const providerValue: TabsContextProviderProps = {
    activeValue,
  }

  return (
    <TabProvider value={providerValue}>
      <StyledTabContainer>
        <StyledTabNav>
          {options.map((item, index) => (
            <StyledTabNavBtn
              isActive={index === currentIndex}
              key={index}
              onClick={() => {
                setActiveValue(item.name)
                setCurrentIndex(index)
              }}
            >
              {item?.icon}
              {item.name}
            </StyledTabNavBtn>
          ))}
        </StyledTabNav>
        <StyledTabContent>{children}</StyledTabContent>
      </StyledTabContainer>
    </TabProvider>
  )
}

export default Tabs

const StyledTabContainer = styled.div`
  margin: 0 auto;
  width: 100%;
  min-height: fit-content;
`

const StyledTabNav = styled.nav`
  display: flex;
  /* border-bottom: 1px solid lightgray; */
`

const StyledTabNavBtn = styled.button<{
  isActive: boolean
}>`
  display: flex;
  align-items: center;
  padding: 16px 20px;
  border: none;
  background-color: transparent;
  font-size: 1.5rem;
  font-weight: ${(props) => (props.isActive ? '800' : '500')};
  /* border-bottom: ${(props) =>
    props.isActive ? '2px solid black' : 'none'}; */
`

const StyledTabContent = styled.section`
  padding: 20px;
`

const StyledTabItem = styled.div<{
  isActive: boolean
}>`
  display: ${(props) => (props.isActive ? 'block' : 'none')};
`
