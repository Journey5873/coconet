import React from 'react';

import { Input, InputProps } from '@mui/material';

const CustomInput : React.FC<InputProps> = (props) => {
    return (
        <Input {...props}/>
    );
};

export default CustomInput;