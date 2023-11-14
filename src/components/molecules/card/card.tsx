import { FaBookmark, FaEye } from "react-icons/fa";

import styled from "styled-components";

interface Props {
    title: string;
    expiredAt: string;
    viewCount: number;
    bookmarkCount: number;
    role: string[];
    techStacks: string[];
}

const Card = ({ bookmarkCount, expiredAt, role, techStacks, title, viewCount }: Props) => {
    return (
        <StyledCardContainer>
            <StyledRowBetweenWrapper>
                <StyledCardExpired>{`마감일 | ${expiredAt}`}</StyledCardExpired>
                <FaBookmark />
            </StyledRowBetweenWrapper>

            <h2>{title}</h2>
            {role.map((item, index) => (
                <StyledRole key={index}>{item}</StyledRole>
            ))}
            <StyledRowWrapper>
                {techStacks.map((item, index) => (
                    <StyledTech key={index}>{item}</StyledTech>
                ))}
            </StyledRowWrapper>

            <StyledRowWrapper>
                <FaEye />
                {viewCount}
            </StyledRowWrapper>
        </StyledCardContainer>
    );
};

export default Card;

const StyledCardContainer = styled.div`
    width: 100%;
    padding: 1rem;
    border: 2px solid lightgray;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    gap: 1rem;

    &:hover {
        scale: 1.1;
    }
`;

const StyledCardExpired = styled.div`
    font-size: 0.8rem;
    color: gray;
`;

const StyledRole = styled.div`
    padding: 2px 4px;
    max-width: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #dddcdc;
    color: dodgerblue;
    border-radius: 20px;
`;

const StyledRowWrapper = styled.div`
    display: flex;
    flex-direction: row;
    column-gap: 1rem;
    align-items: center;
`;

const StyledRowBetweenWrapper = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
`;

const StyledTech = styled.div`
    width: 50px;
    height: 50px;
    border: 1px solid lightgray;
    border-radius: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
`;
