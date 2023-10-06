import Select, { SelectChangeEvent } from '@mui/material/Select';
// import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import React from 'react';
import styled from 'styled-components';

interface LabelProps {
    label: string;
}

export default function SingleSelect({label} : LabelProps) {
 const [value, setValue] = React.useState('');

  const handleChange = (event: SelectChangeEvent<unknown>) => {
    setValue(event.target.value as string);
  };

    return (
        <StyledSelectWrapper>
            <StyledInputLabel id="demo-simple-select-label">{label}<StyledRequired>*</StyledRequired></StyledInputLabel>
            <StyledSelect
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={value}
            label="Age"
            onChange={handleChange}
        >
            <MenuItem value={10}>프론트엔드</MenuItem>
            <MenuItem value={20}>백엔드</MenuItem>
            <MenuItem value={30}>디자이너</MenuItem>
            <MenuItem value={30}>IOS</MenuItem>
            <MenuItem value={30}>안드로이드</MenuItem>
            <MenuItem value={30}>데브옵스</MenuItem>
            <MenuItem value={30}>PM</MenuItem>
            <MenuItem value={30}>기획자</MenuItem>
            </StyledSelect>
        </StyledSelectWrapper>
    )
}

const StyledSelectWrapper = styled.div`
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 20px;
`

const StyledInputLabel = styled.label`
    color: rgb(51, 51, 51);
    font-size: 14px;
    font-weight: 700;
    line-height: 20px;
    letter-spacing: -0.28px;
`
const StyledRequired = styled.span`
    padding-left : 3px;
    color: rgb(234, 114, 111);
    font-size: 14px;
    font-weight: 700;
    line-height: 20px;
    letter-spacing: -0.28px;
`

const StyledSelect = styled(Select)`
    align-items: center;
    background-color: rgb(255, 255, 255);
    border-color: rgb(204, 204, 204);
    border-radius: 4px;
    border-style: solid;
    border-width: 1px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    min-height: 48px;
    position: relative;
    transition: all 100ms ease 0s;
    box-sizing: border-box;
    max-width: 500px;
    width: 100%;
    outline: 0px !important;
`