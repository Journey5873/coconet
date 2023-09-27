import styled from "styled-components"

interface Props {
    buttonName: string;
}

export default function GrayButton({buttonName }:Props) {
    return (
        <StyledGrayButton>{buttonName }</StyledGrayButton>
    )
}

const StyledGrayButton = styled.div`
    box-sizing : border-box;
    display: flex;
    width: 40%;
    height: 44px;
    padding: 11px 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    color: rgb(133, 133, 133);
    font-size: 16px;
    font-weight: 700;
    line-height: 126.5%;
    letter-spacing: -0.51px;
    border-radius: 50px;
    background: rgb(241, 241, 241);
`