import React, { useState } from "react";
import Select, { components } from "react-select";
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import {useSelector } from "react-redux";
import { RootState } from "../../../store/config";
import styled from "styled-components";
// import Select from "@mui/material/Select";
import Box from "@mui/material/Box";
import Chip from "@mui/material/Chip";

interface Props {
    children: any;
    innerProps: any;
}

const Option = ({ children, innerProps, ...props}:Props | any) => {
  const { selectOption, selectProps, data } = props;
    const { optionAdd, optionDelete } = selectProps;

  const clickWithoutSelect = (e:any, callback:any) => {
      callback(data);
    e.stopPropagation();
  };

  const onMouseDown = {
    label: () => selectOption(data),
    add: (e: any) => clickWithoutSelect(e, optionAdd),
    delete: (e: any) => clickWithoutSelect(e, optionDelete)
  };

  const { onClick, ...newInnerProps } = innerProps;

  return (
    <components.Option {...props} innerProps={newInnerProps} >
      <span onClick={onMouseDown.label}>{children}</span>
        
        <StyledButtonWrapper>
          <RemoveCircleOutlineIcon onClick={onMouseDown.delete}>delete</RemoveCircleOutlineIcon>
              <span>{data.count}</span>
          <AddCircleOutlineIcon onClick={onMouseDown.add}>add</AddCircleOutlineIcon>
        </StyledButtonWrapper>
      
    </components.Option>
  );
};

const M2s = (props:any) => {
    const { optionAdd, optionDelete,count } = props;
    const item = useSelector((state : RootState) => state.positionList);
    
  const styles = {
        option: (css: any) => ({
        ...css,
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        minHeight: "2.5rem", 
        span: { cursor: "pointer",  },
        button: { cursor: "pointer" },
        })
    };

  return (
    <Select 
          styles={styles}
          components={{ Option }}
          closeMenuOnSelect={false}
          isMulti
          options={item}
          optionAdd={optionAdd}
          optionDelete={optionDelete}
          count={count}
    />
  );
};

export default M2s;


const StyledButtonWrapper = styled.div`
    display:flex;
    gap : 4px;
    
`