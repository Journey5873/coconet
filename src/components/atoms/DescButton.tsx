import React from 'react';

import styled from 'styled-components';

interface Props {
    label: string;
    color?: string;
    onClick?: () => void;
    bgColor?: string;
}

const DescButton: React.FC<Props> = ({ label, onClick, color = 'black', bgColor = 'lightgray' }) => {
    return (
        <StyledDescButton onClick={onClick} color={color} bgColor={bgColor}>
            {label}
        </StyledDescButton>
    );
};

export default DescButton;

const StyledDescButton = styled.button<{
    color: string;
    bgColor: string;
}>`
    padding: 4px 12px;
    border: none;
    border-radius: 1rem;
    color: ${(props) => props.color};
    background-color: ${(props) => props.bgColor};
`;
