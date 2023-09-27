import styled from "styled-components"

interface Props {
    buttonName: string;
}

export default function GreenButton({buttonName }:Props) {
    return (
        <StyledGreenButton>{buttonName}</StyledGreenButton>
    )
}

const StyledGreenButton = styled.div`
    box-sizing : border-box;
    display: flex;
    width: 60%;
    height: 44px;
    padding: 10px;
    justify-content: center;
    align-items: center;
    gap: 5px;
    color: rgb(255, 255, 255);
    font-size: 16px;
    font-weight: 700;
    line-height: 126.5%;
    letter-spacing: -0.51px;
    border-radius: 50px;
    background: rgb(110, 209, 192);
`