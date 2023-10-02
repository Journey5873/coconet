import React from 'react';
import styled from 'styled-components';
import defaultImageSource from '../assets/images/profileImg.svg';

interface Props {
    username : string;
    registeredDate : string;
    uri? : string; 
}

const Division = ({username,registeredDate, uri}: Props) => {

    return(
        <StyledStudyContentWrapper>
            <StyledStudyContent>
                {uri ? (
                    /* 회원가입 후 프로필 이미지가 등록된 유저면 해당 유저 프로필 이미지를 노출*/
                        <StyledProfileImage  src={defaultImageSource} alt='profileImage' />
                    ) : (
                        <StyledProfileImage  src={defaultImageSource} alt='profileImage' />
                )}
                <StyledUserName>{username}</StyledUserName>
            </StyledStudyContent>
        </StyledStudyContentWrapper>
        
    )
}

export default Division;

const StyledStudyContentWrapper = styled.div`
    margin-top: 32px;
    padding-bottom: 32px;
    border-bottom: 3px solid #f2f2f2;
    display: flex;
    grid-gap: 15px;
    gap: 15px;
    align-items: center;
`;

const StyledStudyContent = styled.div`
    display: flex;
    align-items: center;
    transition: transform .2s ease-in-out,-webkit-transform .2s ease-in-out;

    &:hover {
        transform: scale(1.05);
    }
`
const StyledProfileImage = styled.img`
    cursor: pointer;
    display: block;
    height: 3rem;
    width: 3rem;
    margin-right: 8px;
    border-radius: 50%;
    object-fit: cover;
`
const StyledUserName = styled.span`
    color: #333;
    cursor: pointer;
    font-size: 18px;
    font-weight: 700;
`

const StyledDivision = styled.div`
    width: 2px;
    height: 20px;
    background-color: #e2e2e2;
`;

const StyledRegisteredDate = styled.div`
    font-size: 18px;
    color: #717171;
`