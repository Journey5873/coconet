import * as React from "react";
import { Theme, useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
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
  const [count, setCount] = React.useState(0);
    
  const item = useSelector((state : RootState) => state.positionList);

  let dispatch = useDispatch();


  const handleChange = (event: SelectChangeEvent<typeof personName>) => {
    event.preventDefault();

    const {
      target: { value }
      } = event;
      setPersonName(
      // On autofill we get a stringified value.
      typeof value === "string" ? value.split(",") : value
    );
  };

  return (
    <div>
      <FormControl sx={{ m: 1, width: 300 }}>
        <InputLabel id="demo-multiple-chip-label">Chip</InputLabel>
        <Select
          labelId="demo-multiple-chip-label"
          id="demo-multiple-chip"
          multiple
          value={personName}
          onChange={handleChange}
          input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
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
              style={getStyles(name.value, personName, theme)}
            >
              {name.value}
                  <RemoveCircleOutlineIcon onClick={(e) => {
                      e.stopPropagation();
                      dispatch(decrease(item[i].id));
                  }}>-</RemoveCircleOutlineIcon>
              {name.count}
                  <AddCircleOutlineIcon onClick={(e) => {
                      e.stopPropagation();
                      dispatch(increase(item[i].id));
                  }}>+</AddCircleOutlineIcon>
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
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

