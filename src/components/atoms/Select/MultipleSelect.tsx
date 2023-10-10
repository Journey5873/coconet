import React from 'react';
import Select from 'react-select'
import makeAnimated from 'react-select/animated';
import styled from 'styled-components';

interface MultipleProp {
    label: string;
}

const animatedComponents = makeAnimated();

  const skillList = [
    { value: 'JavaScript', label: 'JavaScript' },
    { value: 'TypeScript', label: 'TypeScript' },
    { value: 'React', label: 'React' },
    { value: 'Vue', label: 'Vue' },
    { value: 'Nodejs', label: 'Nodejs' },
    { value: 'Java', label: 'Java' },
    { value: 'Spring', label: 'Spring'},
    { value: 'Kotlin', label: 'Kotlin' },
    { value: 'C++', label: 'C++' },
    { value: 'Go', label: 'Go'},
    { value: 'Python', label: 'Python' },
    { value: 'Django', label: 'Django' },
    { value: 'Flutter', label: 'Flutter'},
    { value: 'Swift', label: 'Swift' },
    { value: 'ReactNative',label: 'ReactNative'},
    { value: 'Unity', label: 'Unity' },
    { value: 'AWS', label: 'AWS' },
    { value: 'Kubernetes', label: 'Kubernetes' },
    { value: 'Docker', label: 'Docker' },
    { value: 'Git', label: 'Git' },
    { value: 'Figma', label: 'Figma' },
    { value: 'Zeplin', label: 'Zeplin' },
    { value: 'Jest', label: 'Jest' },
];
  


export default function MultipleSelect({label}:MultipleProp) {
    
    return (
        <StyledMultipleSelectWapprer>
        <StyledInputLabel id="demo-simple-select-label">{label}<StyledRequired>*</StyledRequired></StyledInputLabel>
            <StlyedMultiSelect
            closeMenuOnSelect={false}
            components={animatedComponents}
            defaultValue={[skillList[4], skillList[5]]}
            isMulti
            options={skillList}
            />
        </StyledMultipleSelectWapprer>
    )
}

const StyledMultipleSelectWapprer = styled.div`
    max-width : 500px;
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

const StlyedMultiSelect = styled(Select)`
        -webkit-box-align: center;
    align-items: center;
    background-color: rgb(255, 255, 255);
    border-color: rgb(204, 204, 204);
    border-radius: 4px;
    cursor: default;
    display: flex;
    flex-wrap: wrap;
    -webkit-box-pack: justify;
    justify-content: space-between;
    min-height: 48px;
    position: relative;
    transition: all 100ms ease 0s;
    box-sizing: border-box;
    max-width: 500px;
    width: 100%;
    outline: 0px !important;
`