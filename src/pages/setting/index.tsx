import React from "react";

import Header from "../../components/atoms/Header";

import RegisterImage from "../../components/atoms/RegisterImage";

import styled from "styled-components";

import Labelnput from "../../components/molecules/Labelnput";

import SingleSelect from "../../components/atoms/Select/SingleSelect";
import LinkList from "../../components/organisms/LinkList";
import GreenButton from "../../components/atoms/Button/GreenButton";
import GrayButton from "../../components/atoms/Button/GrayButton";

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
            <Labelnput text="자기소개" onChange={() => {}} />
            <LinkList onSubmit={() => {}} />
            <div
                style={{
                    margin: "0 auto",
                    width: "100%",
                    display: "flex",
                    flexDirection: "column",
                    rowGap: "16px",
                    alignItems: "center",
                    justifyItems: "center",
                    textAlign: "center",
                }}
            >
                <GreenButton buttonName="저장" />
                <GrayButton buttonName="회원탈퇴" />
            </div>
        </Container>
    );
};

export default SettingPage;

const Container = styled.div`
    margin: 0 auto;
    max-width: 700px;
    display: flex;
    flex-direction: column;
    row-gap: 16px;
    justify-content: center;
    /* align-items: center; */
`;
