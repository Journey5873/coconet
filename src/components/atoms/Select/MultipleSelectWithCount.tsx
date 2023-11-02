import * as React from "react";
import { Theme, useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import OutlinedInput from "@mui/material/OutlinedInput";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import Chip from "@mui/material/Chip";
import { useSelector, useDispatch } from "react-redux";
import styled from "styled-components";
import { decrease, increase } from "../../../store/positionSlice";
import { RootState } from "../../../store/config";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';


const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250
    }
  }
};


function getStyles(name: string, personName: readonly string[], theme: Theme) {
  return {
    fontWeight:
      personName.indexOf(name) === -1
        ? theme.typography.fontWeightRegular
              : theme.typography.fontWeightMedium
  };
}

export default function MultipleSelectWithCount() {
    const theme = useTheme();
    const [personName, setPersonName] = React.useState<string[]>([]);
    
    const item = useSelector((state : RootState) => state.positionList);

    let dispatch = useDispatch();

    const handleChange = (label: string) => {
        setPersonName(
       personName.includes(label) ? [...personName] : [...personName,label]
    );
      
  };

  return (
    <div>
      <FormControl sx={{ m: 1, width: 300 }}>
        <Select
            multiple
            value={personName}
            input={<OutlinedInput id="select-multiple-chip"/>}
            renderValue={(selected) => (
                <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                    {selected.map((value) => {
                        const isSelected = item.find((item => item.value === value))?.count;
                            return (
                                isSelected as number > 0 &&  <Chip key={value} label={ value + " (" +item.find((item => item.value === value))?.count + ")"}></Chip>
                            )
                        })}
                </Box>
          ) }
          MenuProps={MenuProps}
        >
          {item.map((name:any,i:number) => (
              <MenuItem
                key={name.value}
                value={name.value}
                style={{display: "flex", justifyContent : "space-between"}}
                >
                    <div>{name.value}</div>
                    <StyledButtonWrapper>
                    <RemoveCircleOutlineIcon onClick={(e) => {
                        e.stopPropagation();
                        dispatch(decrease(item[i].id));
                        handleChange(item[i].label)
                    }}>-</RemoveCircleOutlineIcon>
                {name.count}
                <AddCircleOutlineIcon onClick={(e) => {
                    e.stopPropagation();
                    dispatch(increase(item[i].id));
                    handleChange(item[i].label)
                    }}>+</AddCircleOutlineIcon>
                </StyledButtonWrapper>
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
}


const StyledButtonWrapper = styled.div`
    display : flex;
    gap : 4px;
`