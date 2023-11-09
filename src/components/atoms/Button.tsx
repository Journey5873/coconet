import React from "react";

import styled from "styled-components";

interface Props {
    iconUrl?: string;
    label: string;
    onClick?: () => void;
}

const SpecButton: React.FC<Props> = ({ iconUrl, label, onClick }) => {
    return (
        <StyledSpecButton onClick={onClick}>
            <StyledSpecButtonImage src={iconUrl} />
            <span>{label}</span>
        </StyledSpecButton>
    );
};

export default SpecButton;

const StyledSpecButton = styled.button`
    padding: 6px 12px;
    border: 1px solid lightgray;
    border-radius: 1rem;
    display: flex;
    align-items: center;
    column-gap: 4px;
    justify-content: center;
    background-color: white;
    transition: transform 0.2s ease;
    &:hover {
        transform: scale(1.1);
    }
`;

const StyledSpecButtonImage = styled.img`
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    border: 1px solid lightgray;
`;
