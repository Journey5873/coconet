import React, { ReactNode, useState } from 'react';

import styled from 'styled-components';

interface TabItem {
  id: number;
  name: string;
  contents: ReactNode;
}

interface Props {
  tabList: TabItem[];
}

const Tab: React.FC<Props> = ({ tabList }) => {
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  return (
    <StyledTabContainer>
      <StyledTabNav>
        {tabList.map((item, index) => (
          <StyledTabNavBtn
            isActive={index === currentIndex}
            key={item.id}
            onClick={() => setCurrentIndex(index)}
          >
            {item.name}
          </StyledTabNavBtn>
        ))}
      </StyledTabNav>
      <StyledTabContent>
        {tabList.map((item, index) => (
          <StyledTabItem isActive={index === currentIndex} key={item.id}>
            {item.contents}
          </StyledTabItem>
        ))}
      </StyledTabContent>
    </StyledTabContainer>
  );
};

export default Tab;

const StyledTabContainer = styled.div`
  padding: 20px;
  width: 800px;
  min-height: 500px;
  border: 1px solid black;
`;

const StyledTabNav = styled.nav`
  display: flex;
  border-bottom: 1px solid lightgray;
`;

const StyledTabNavBtn = styled.button<{
  isActive: boolean;
}>`
  width: 85px;
  border: none;
  background-color: transparent;
  font-size: 16px;
  font-weight: ${(props) => (props.isActive ? '800' : '500')};
  border-bottom: ${(props) => (props.isActive ? '2px solid black' : 'none')};
`;

const StyledTabContent = styled.section`
  padding: 20px;
`;

const StyledTabItem = styled.div<{
  isActive: boolean;
}>`
  display: ${(props) => (props.isActive ? 'block' : 'none')};
`;
