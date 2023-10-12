import React, { useState } from 'react';

import styled from 'styled-components';

import Select, { SelectChangeEvent } from '@mui/material/Select';

import DeleteIcon from '@mui/icons-material/Delete';

import MenuItem from '@mui/material/MenuItem';

import CustomInput from '../atoms/Input';

import Label from '../atoms/Label';

type TLinkList = {
  id: string;
  iconUrl: string;
  type: string; // github, notion, blog ...
  url: string;
};

interface Props {
  onSubmit: () => void;
}

const linkTypeList = ['GitHub', 'notion', 'Blog', 'facebook'];

const LinkList: React.FC<Props> = ({ onSubmit }) => {
  const [linkList, setLinkList] = useState<TLinkList[]>([]);

  const onClickAdd = () => {
    const newLinkItem: TLinkList = {
      iconUrl: '',
      id: Date.now().toString(),
      type: '',
      url: '',
    };

    setLinkList((prev) => [...prev, newLinkItem]);
  };

  const onClickDelete = (id: string) => {
    const newList = [...linkList].filter((linkItem) => linkItem.id !== id);
    setLinkList(newList);
  };

  const onChangeInputValue = (e: any, id: string) => {
    const copyList = [...linkList];
    const value = e.target.value;
    const newList = copyList.map((listItem) => {
      if (listItem.id === id) {
        return {
          ...listItem,
          url: value,
        };
      }

      return { ...listItem };
    });

    setLinkList(newList);
  };

  const onChangeType = (e: any, id: string) => {
    const copyList = [...linkList];
    const type = e.target.value;
    const newList = copyList.map((listItem) => {
      if (listItem.id === id) {
        return {
          ...listItem,
          type,
        };
      }

      return { ...listItem };
    });

    setLinkList(newList);
  };

  return (
    <StyledForm onSubmit={(e) => e.preventDefault()}>
      <Label isRequired={false} text="링크" />
      <StyledLinkListContainer>
        {linkList.map((linkItem) => (
          <StyledLinkItem key={linkItem.id}>
            <CustomInput
              value={linkItem.url}
              onChange={(e) => onChangeInputValue(e, linkItem.id)}
            />
            <Select onChange={(e) => onChangeType(e, linkItem.id)}>
              {linkTypeList.map((type) => (
                <MenuItem key={type} value={type}>
                  {type}
                </MenuItem>
              ))}
            </Select>
            <StyledDeleteBtn onClick={() => onClickDelete(linkItem.id)}>
              <DeleteIcon />
            </StyledDeleteBtn>
          </StyledLinkItem>
        ))}
      </StyledLinkListContainer>
      <StyledAddBtn onClick={onClickAdd}>+ 추가</StyledAddBtn>
    </StyledForm>
  );
};

export default LinkList;

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

const StyledLinkListContainer = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 16px;
`;

const StyledLinkItem = styled.div`
  display: flex;
  column-gap: 4px;
`;

const StyledDeleteBtn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  background-color: transparent;
  cursor: pointer;
`;

const StyledAddBtn = styled(StyledDeleteBtn)``;
