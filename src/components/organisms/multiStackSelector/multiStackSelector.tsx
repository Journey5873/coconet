import React, { useState } from "react";

import { skillList } from "../../atoms/MultipleSelect";

import Tabs from "../customTabs/Tabs";

import Tab from "../customTabs/Tab";

import styled from "styled-components";

import SpecButton from "../../atoms/Button";

interface Props {
    handleSelected: (value: string) => void;
    selected: string[];
}

const MultiStackSelector = ({ handleSelected, selected }: Props) => {
    const [isShow, setIsShow] = useState<boolean>(false);
    return (
        <StyledContainer>
            <div>
                <StyledSelectButton onClick={() => setIsShow((prev) => !prev)}>
                    <span>기술 스택</span>
                    <span style={{ transform: "rotate(90deg)" }}>{">"}</span>
                </StyledSelectButton>
            </div>
            <StyledPopupWrapper isShow={isShow}>
                <Tabs
                    options={[
                        { name: "인기" },
                        { name: "프론트엔드" },
                        { name: "백엔드" },
                        { name: "모바일" },
                        { name: "기타" },
                        { name: "모두보기" },
                    ]}
                >
                    <Tab value="인기">
                        <StyledStackWrapper>
                            {skillList.map((skill) => (
                                <SpecButton label={skill.label} onClick={() => handleSelected(skill.value)} />
                            ))}
                        </StyledStackWrapper>
                    </Tab>
                </Tabs>
            </StyledPopupWrapper>
        </StyledContainer>
    );
};

export default MultiStackSelector;

const StyledContainer = styled.div`
    position: relative;
`;

const StyledPopupWrapper = styled.div<{
    isShow: boolean;
}>`
    margin-top: 8px;
    width: 900px;
    height: auto;
    padding: 20px 32px;
    border: 1px solid lightgray;
    border-radius: 20px;
    display: ${(props) => (props.isShow ? "block" : "none")};
`;

const StyledStackWrapper = styled.div`
    display: flex;
    flex-direction: row;
    gap: 8px;
    flex-wrap: wrap;
`;

const StyledSelectButton = styled.div`
    width: 140px;
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    column-gap: 1rem;
    border: 1px solid lightgray;
    border-radius: 20px;
`;
