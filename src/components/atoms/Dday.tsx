import React from 'react';
import SvgIcon from "@mui/material/SvgIcon";
// import { SvgIconComponent } from "@mui/icons-material";
import NotificationImportantIcon from '@mui/icons-material/NotificationImportant';
import styled from 'styled-components';

interface Props {
    dDay : number;
}

const Dday = ({dDay}: Props) => {
    return(
        <StyledDday>
          <SvgIcon style={{ color: '#f05653',fontSize : 16 }} component={NotificationImportantIcon} inheritViewBox />
          <StyledDdayText>마감 {dDay}일전</StyledDdayText>
        </StyledDday>
        
    )
}

const StyledDday = styled.div`
    display : flex;
    align-items : center;
    padding: 3px 5px;
    border: 1px solid rgb(234, 114, 111);
    border-radius: 20px;
    max-width : fit-content;
`
const StyledDdayText = styled.span`
    margin : 0px;
    color: rgb(234, 114, 111);
    font-size: 11px;
    font-weight: 600;
    letter-spacing: -0.56px;
`

export default Dday;