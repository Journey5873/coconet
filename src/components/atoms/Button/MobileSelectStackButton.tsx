import React from "react";
import styled from "styled-components";

interface StackProps {
  children?: React.ReactNode;
  stackName: string;
  selected: boolean;
  value: number;
  idd: number;
  index: number;
  onClickStack: (checked: boolean, index: number) => void;
}

export default function MobileStackButton(props: StackProps) {

  const { children, value, index, selected, stackName, onClickStack, ...other } = props;

  return (
    <StyledMobileStackButton
      onClick={() => onClickStack(selected, index)}
      selected={selected}
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      <label htmlFor="">{stackName}</label>

      
    </StyledMobileStackButton>
    )
}

const StyledMobileStackButton = styled.li< { selected:boolean }>`
    padding: 10px;
    display: flex;
    font-size: 12px;
    font-weight : 700;
    letter-spacing : 0.1px;
    justify-content: center;
    align-items: center;
    color: ${({selected}) => selected ? 'rgb(74, 94, 117)' : 'rgb(153, 153, 153)'};
    background:${({selected}) => selected ? 'rgb(242, 244, 248)' : 'rgb(255, 255, 255)'};
    border-radius: 30px;
    border: ${({selected}) => selected ? '1px solid rgb(242, 244, 248)' :'1px solid rgb(225, 225, 225)'};
`;