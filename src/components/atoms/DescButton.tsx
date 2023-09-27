import React from 'react';

import styled from 'styled-components';

interface Props {
    label: string;
    onClick?: () => void;
}

const DescButton: React.FC<Props> = ({ label, onClick }) => {
    return <StyledDescButton onClick={onClick}>{label}</StyledDescButton>;
};

export default DescButton;

const StyledDescButton = styled.button`
    padding: 4px 12px;
    border: none;
    border-radius: 1rem;
    color: dodgerblue;
    background-color: lightgray;
`;
