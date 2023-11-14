import { useState } from "react";
import styled from "styled-components";

interface Props {
    title: string;
    options: string[];
}

const FilterSelect = ({ title, options }: Props) => {
    const [isShow, setIsShow] = useState<boolean>(false);
    return (
        <StyledContainer>
            <div>
                <StyledSelectButton onClick={() => setIsShow((prev) => !prev)}>
                    <span>{title}</span>
                    <span style={{ transform: "rotate(90deg)" }}>{">"}</span>
                </StyledSelectButton>
            </div>
            <StyledPopupWrapper isShow={isShow}>
                {options.map((item, index) => (
                    <div key={index}>{item}</div>
                ))}
            </StyledPopupWrapper>
        </StyledContainer>
    );
};

export default FilterSelect;

const StyledContainer = styled.div`
    position: relative;
`;

const StyledPopupWrapper = styled.div<{
    isShow: boolean;
}>`
    position: absolute;
    top: 100%;
    left: 0;
    display: flex;
    flex-direction: column;
    row-gap: 1rem;
    margin-top: 8px;
    width: 120px;
    height: auto;
    padding: 20px 32px;
    border: 1px solid lightgray;
    border-radius: 20px;
    display: ${(props) => (props.isShow ? "block" : "none")};
    z-index: 10;
    background-color: white;
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
