import React, { ChangeEvent, useState } from 'react';
import { useForm } from 'react-hook-form';
import RegisterImage from '../../components/atoms/RegisterImage';
import styled from 'styled-components';
import Labelnput from '../../components/molecules/Labelnput';
import SingleSelect from '../../components/atoms/Select/SingleSelect';
import LinkList from '../../components/organisms/LinkList';
import GreenButton from '../../components/atoms/Button/GreenButton';
import MultipleSelect from '../../components/atoms/Select/MultipleSelect';
import useFetch from '../../hooks/useFetch';

export interface SettingFormType {
  name: string;
  career: number;
  roles: string[];
  stacks: string[];
  githubLink?: string;
  blogLink?: string;
  notionLink?: string;
}

interface SelectValue {
  value: string;
  label: string;
}

const SettingPage = () => {
  const [name, setName] = useState('');
  const [job, setJob] = useState<SelectValue>({
    label: '',
    value: '',
  });
  const [carrer, setCarrer] = useState<SelectValue>({
    label: '',
    value: '',
  });
  const [stack, setStack] = useState<SelectValue[]>([]);

  const handleName = (e: any) => {
    const target = e.target as HTMLInputElement;
    setName(target.value);
  };

  const handleJob = (value: SelectValue) => setJob(value);

  const handleCarrer = (value: SelectValue) => setCarrer(value);

  const handleStack = (value: SelectValue[]) => setStack(value);

  const onSave = async () => {
    try {
      const DTO = {
        name,
        roles: [job.value],
        statks: [...stack].map((item) => item.value),
        carrer: +carrer.value,
      };

      const response = await fetch('/member-service/open-api/my-profile', {
        body: JSON.stringify(DTO),
        method: 'PUT',
      });

      const result = await response.json();

      console.log(result.data);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Container>
      <div style={{ margin: '0 auto' }}>
        <RegisterImage />
      </div>
      <Labelnput text="닉네임" isRequired value={name} onChange={handleName} />
      <SingleSelect label="직무" onChange={handleJob} value={job} />
      <Labelnput text="소속" />
      <SingleSelect label="경력" onChange={handleCarrer} value={carrer} />
      <Labelnput text="자기소개" onChange={() => {}} />
      <MultipleSelect label={'관심스택'} onChange={handleStack} value={stack} />
      <LinkList onSubmit={() => {}} />
      <StyledButtonWrapper>
        <GreenButton buttonName="저장" onClick={() => onSave()} />
        <DeleteUserButton>회원탈퇴</DeleteUserButton>
      </StyledButtonWrapper>
    </Container>
  );
};

export default SettingPage;

const Container = styled.div`
  width: 100%;
  max-width: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 16px;
  gap: 16px;
  margin: 0px auto;
`;

const StyledButtonWrapper = styled.div`
  margin: 0 auto;
  width: 100%;
  display: flex;
  flex-direction: column;
  row-gap: 16px;
  align-items: center;
  justify-items: center;
  text-align: center;
`;

const DeleteUserButton = styled.div`
  font-size: 16px;
  font-weight: 700;
  line-height: 126.5%;
  letter-spacing: -0.51px;
  text-align: center;
  color: rgb(194, 198, 207);
  cursor: pointer;
`;
