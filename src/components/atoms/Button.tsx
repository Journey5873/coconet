import React from 'react';

import { Button, ButtonProps } from '@mui/material';

const CustomButton: React.FC<ButtonProps> = (props) => {
  const { children, ...rest } = props;
  return (
    <Button
      style={{ padding: '20px 16px', textAlign: 'center', border: 'none' }}
      {...rest}
    >
      {props.children}
    </Button>
  );
};

export default CustomButton;
