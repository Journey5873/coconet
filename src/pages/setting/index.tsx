import React from "react";

import RegisterImage from "../../components/atoms/RegisterImage";

import styled from "styled-components";

import Labelnput from "../../components/molecules/Labelnput";

import SingleSelect from "../../components/atoms/Select/SingleSelect";
import LinkList from "../../components/organisms/LinkList";
import GreenButton from "../../components/atoms/Button/GreenButton";
import MultipleSelect from "../../components/atoms/Select/MultipleSelect";

const SettingPage = () => {
    return (
        <Container>
            <div style={{ margin: "0 auto" }}>
                <RegisterImage />
            </div>
            <Labelnput text="닉네임" onChange={() => {}} isRequired />
            <SingleSelect label="직무" />
            <Labelnput text="소속" onChange={() => {}} />
            <SingleSelect label="경력" />
            <Labelnput text="자기소개" onChange={() => { }} />
            <MultipleSelect label={"관심스택"} />
            <LinkList onSubmit={() => { }} />
            <StyledButtonWrapper>
                <GreenButton buttonName="저장" />
                <DeleteUserButton>회원탈퇴</DeleteUserButton>
            </StyledButtonWrapper>
        </Container>
    );
};

export default SettingPage;

const Container = styled.div`
    width: 100%;
    max-width: 500px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 16px;
    gap: 16px;
    margin: 0px auto;
`;

const StyledButtonWrapper = styled.div`
    margin: 0 auto;
    width: 100%;
    display: flex;
    flex-direction: column;
    row-gap: 16px;
    align-items: center;
    justify-items: center;
    text-align: center;
`

const DeleteUserButton = styled.div`
    font-size: 16px;
    font-weight: 700;
    line-height: 126.5%;
    letter-spacing: -0.51px;
    text-align: center;
    color: rgb(194, 198, 207);
    cursor: pointer;
`