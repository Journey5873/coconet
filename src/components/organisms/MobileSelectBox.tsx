import React, { useState } from 'react';
import styled from 'styled-components';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import MobileStackButton from '../atoms/Button/MobileSelectStackButton';
import MobileCategoryButtonList from '../molecules/MobileCategoryButtonList';
import GreenButton from '../atoms/Button/GreenButton';
import GrayButton from '../atoms/Button/GrayButton';


function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const MobileSelectBox = () => {

  const [value, setValue] = React.useState(0);
  const tabs = [
    {label : "기술스택", val : 0},
    {label : "모집구분", val : 1},
    {label : "포지션", val : 2},
    {label : "진행방식", val : 3},
    {label : "마감여부", val : 4}
  ]
  
  // 기술 스택 리스트
  const skillList = [
    { skillName: 'javascript', id: 0 },
    { skillName: 'typescript', id: 1 },
    { skillName: 'react', id: 2 },
    { skillName: 'vue', id: 3 },
    { skillName: 'nodejs', id: 4 },
    { skillName: 'java', id: 5 },
    { skillName: 'spring', id: 6 },
    { skillName: 'kotlin', id: 7 },
    { skillName: 'c++', id: 8 },
    { skillName: 'go', id: 9 },
    { skillName: 'python', id: 10 },
    { skillName: 'django', id: 11 },
    { skillName: 'flutter', id: 12 },
    { skillName: 'swift', id: 13 },
    { skillName: 'ReactNative', id: 14 },
    { skillName: 'Unity', id: 15 },
    { skillName: 'AWS', id: 16 },
    { skillName: 'Kubernetes', id: 17 },
    { skillName: 'Docker', id: 18 },
    { skillName: 'Git', id: 19 },
    { skillName: 'Figma', id: 20 },
    { skillName: 'Zeplin', id: 21 },
    { skillName: 'Jest', id: 22 },
  ];

  const [selectedSkillList, setSelectedSkillList] = useState<number[]>([]);
  const handleSelectedSkill = (checked: boolean, id: number) => {
    checked = !checked;
    if (checked) {
      setSelectedSkillList(prev => [...prev, id]);
    } else {
      setSelectedSkillList(selectedSkillList.filter((v) => v !== id))
    }
  }

  // 모집 구분 리스트
  const [categoryOption, setCategoryOption] = useState<string>('전체');
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleChangeOption = (option: string) => {
    setCategoryOption(option);
  }
  
  
    return(
      <StyledBox>
        <Box sx={{ borderBottom: 1, borderColor: 'divider',marginBottom : 3 }}>
          <Tabs 
            textColor='inherit'
            sx={{
              ".Mui-selected": {
              color: `rgb(51, 51, 51)`,
              },
              }}
            TabIndicatorProps={{style : {backgroundColor : 'rgb(51, 51, 51)'}}}
            value={value} onChange={handleChange} aria-label="basic tabs example">
            {tabs.map((tab) => (
              <Tab
                key={tab.val}
                style={{paddingLeft : 8, paddingRight : 8,minWidth : 'auto'}}
                label={
                  <Typography
                    sx={{
                      fontSize : '15px',
                      fontWeight : 'bold',
                      letterSpacing : '-0.45px',
                      color : 'inherit'
                    }}
                  >
                    {tab.label}
                  </Typography>
                }
                {...a11yProps(tab.val)} 
              />
            ))}
          </Tabs>
        </Box>
          <StyledMobileStackButtonWrapper>
          {value === 0 ? skillList.map((v, i) => (
              <MobileStackButton
                value={value}
                index={v.id}
                idd={v.id}
                stackName={v.skillName}
                selected={selectedSkillList.includes(v.id)}
                onClickStack={handleSelectedSkill}
              />
            ))
            : 
              <MobileCategoryButtonList value={value} index={1} categoryOption={categoryOption} handleChangeOption={handleChangeOption} key={value} />
            }
          
        </StyledMobileStackButtonWrapper>
        <StyledBottomButtons>
          <GrayButton buttonName={'초기화'} />
          <GreenButton buttonName={'필터적용'} />
        </StyledBottomButtons>
        
      </StyledBox>
    )
}


export default MobileSelectBox;

const StyledBox = styled(Box)`
  position: relative;
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    padding: 8px 20px 30px;

    @media screen and (max-width: 500px) {
      height: 430px;
    }
    @media screen and (max-width: 400px) {
      height: 470px;
    }
`

const StyledMobileStackButtonWrapper = styled.ul`
    padding : 0;
    display: flex;
    flex-wrap: wrap;
    gap: 7px;
    margin-bottom: 16px;
    list-style: none;
`

const StyledBottomButtons = styled.div`
  display:flex;
  box-sizing : border-box;
  width : 100%;
  gap: 8px;
  padding: 0px 8px;
  margin: 0px auto;
  position: absolute;
  bottom: 30px;
  left: 0px;
  padding: 0px 8px;
`;