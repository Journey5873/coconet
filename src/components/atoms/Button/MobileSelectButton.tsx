import styled from "styled-components"
import { CategoryOptionProps } from "../../molecules/MobileCategoryButtonList";


export default function MobileCategoryButton(props: CategoryOptionProps) {
    const { children,categoryOption, value, index,checked,handleChangeOption, ...other } = props;
    return (
        <StyledMobileSelectButton
         role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
                {...other}
            checked={checked as boolean}
            onClick={() => handleChangeOption(categoryOption)}
        >   
                <label>{categoryOption}</label>
        </StyledMobileSelectButton>
    )
}

const StyledMobileSelectButton = styled.li<{checked:boolean}>`
    display: flex;
    padding: 10px;
    justify-content: center;
    align-items: center;
    color: ${({checked})=> checked ? 'rgb(110, 209, 192)' : 'rgb(153, 153, 153)'};
    border-radius: 30px;
    border: ${({checked}) => checked ? '1px solid rgb(110, 209, 192)' : '1px solid rgb(153, 153, 153)'};
    font-size: 13px;
    font-weight : 700;
`

