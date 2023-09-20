import React from 'react';

import { Button, ButtonProps } from '@mui/material';

const CustomButton: React.FC<ButtonProps> = (props) => {
  const { children, ...rest } = props;
  return <Button {...rest}>{props.children}</Button>;
};

export default CustomButton;
