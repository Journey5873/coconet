import React, { useState } from "react";
import Dday from "./components/atoms/Dday";
import Division from "./components/atoms/Division";
import Endline from "./components/atoms/EndLine";
import MobileSelectBox from "./components/molecules/MobileSelectBox";
import LoginModal from "./components/atoms/LoginModal";
import SelectBox from "./components/organisms/SelectBox";
import styled from "styled-components";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import SocialKakao from "./utils/SosialKakao";
import RegisterImage from "./components/atoms/RegisterImage";
import SingleSelect from "./components/atoms/Select/SingleSelect";
import MultipleSelectWithCount from "./components/atoms/Select/MultipleSelectWithCount";
import MultipleSelect from "./components/atoms/Select/MultipleSelect";

import CustomCarousel from "./components/organisms/Carousel";
import CustomInput from "./components/atoms/Input";
import Label from "./components/atoms/Label";
import Labelnput from "./components/molecules/Labelnput";
import { Box } from "@mui/material";
import LinkList from "./components/organisms/LinkList";
import Loader from "./components/atoms/Loader";

import { useRoutes } from "react-router-dom";

import { routes } from "./routes";



function App() {
    const elem = useRoutes(routes);
    
    return (
        <>
        <SingleSelect label="직무" />
        <SingleSelect label="경력" />
        <MultipleSelect label={"관심스택"} />
            
        </>
    ); 
}

export default App;

const StyledModalButton = styled.div`
    margin-top: 10px;
    width: 100px;
    box-sizing: border-box;
    justify-content: center;
    min-width: 100px;
    height: 30px;
    border: 1px solid rgb(227, 227, 227);
    color: rgb(100, 100, 100);
    font-weight: 500;
    font-size: 14px;
    letter-spacing: -0.42px;
    display: flex;
    align-items: center;
    border-radius: 36px;
`;
