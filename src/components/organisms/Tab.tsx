import React, { ReactNode, useState } from 'react';

import styled from 'styled-components';

interface TabItem {
    id: number;
    name: string;
    contents: ReactNode;
}

const Tab = () => {
    const [currentIndex, setCurrentIndex] = useState<number>(0);
    const tabItems: TabItem[] = [];
    return (
        <StyledTabContainer>
            <StyledTabNav>
                {tabItems.map((item, index) => (
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
                {tabItems.map((item, index) => (
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
`;

const StyledTabNavBtn = styled.button<{
    isActive: boolean;
}>`
    width: 50px;
    background-color: ${(props) => (props.isActive ? 'lightgray' : 'transparent')};
`;

const StyledTabContent = styled.section`
    padding: 20px;
    border: 1px solid black;
`;

const StyledTabItem = styled.div<{
    isActive: boolean;
}>`
    display: ${(props) => (props.isActive ? 'block' : 'none')};
`;
