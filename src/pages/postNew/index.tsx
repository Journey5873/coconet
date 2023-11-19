import styled from 'styled-components'
import SingleSelect from '../../components/atoms/Select/SingleSelect'
import { useState } from 'react'
import { SelectValue } from '../setting'
import { DemoContainer } from '@mui/x-date-pickers/internals/demo'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import MultipleSelect from '../../components/atoms/Select/MultipleSelect'
import MultipleSelectWithCount from '../../components/atoms/Select/MultipleSelectWithCount'

export default function PostNew() {
  // 모집 구분
  const [category, setCategory] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handleCategory = (value: SelectValue) => setCategory(value)

  // 모집 인원
  const [personne, setPersonne] = useState<SelectValue>({
    label: '',
    value: '',
  })

  const handlePersonne = (value: SelectValue) => setPersonne(value)

  // 진행 방식
  const [onOffline, setOnOffline] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handleOnOffline = (value: SelectValue) => setOnOffline(value)

  // 진행 기간
  const [period, setPeriod] = useState<SelectValue>({
    label: '',
    value: '',
  })
  const handlePeriod = (value: SelectValue) => setPeriod(value)

  return (
    <PostRegisterWrapper>
      <section>
        <StyledSelectSection>
          <StyledSectionNumber>1</StyledSectionNumber>
          <StyledSelectSectionTitle>
            프로젝트 기본 정보를 입력해주세요.
          </StyledSelectSectionTitle>
        </StyledSelectSection>
        <StyledPostSelect>
          <StyledPostSelectList>
            <SingleSelect
              label="모집구분"
              value={category}
              onChange={handleCategory}
              placeholder="스터디/프로젝트"
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <MultipleSelectWithCount
              label="모집 포지션/인원"
              placeholder="프론트엔드/0명"
            />
          </StyledPostSelectList>
        </StyledPostSelect>
        <StyledPostSelect>
          <StyledPostSelectList>
            <SingleSelect
              label="진행 방식"
              value={onOffline}
              onChange={handleOnOffline}
              placeholder={'온라인/오프라인'}
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <SingleSelect
              label="진행 기간"
              value={period}
              onChange={handlePeriod}
              placeholder={'기간 미정~6개월 이상'}
            />
          </StyledPostSelectList>
        </StyledPostSelect>
        <StyledPostSelect>
          <StyledPostSelectList>
            <MultipleSelect
              label="기술 스택"
              value={category}
              onChange={handleCategory}
              placeholder={'프로젝트 사용 스택'}
            />
          </StyledPostSelectList>
          <StyledPostSelectList>
            <label htmlFor="">모집 마감일</label>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <StyledDemoContainer
                components={['DatePicker']}
                sx={{ '.MuiTextField-root': { width: '100%' } }}
              >
                <DatePicker />
              </StyledDemoContainer>
            </LocalizationProvider>
          </StyledPostSelectList>
        </StyledPostSelect>
      </section>
      <section>
        <StyledSelectSection>
          <StyledSectionNumber>2</StyledSectionNumber>
          <StyledSelectSectionTitle>
            프로젝트에 대해 소개해주세요.
          </StyledSelectSectionTitle>
        </StyledSelectSection>
      </section>
    </PostRegisterWrapper>
  )
}

const PostRegisterWrapper = styled.div`
  max-width: 1040px;
  display: flex;
  flex-direction: column;
  padding: 60px 16px;
  width: 1024px;
  margin: 0 auto;
  color: #333;
  grid-gap: 50px;
  gap: 50px;
  position: relative;
`

const StyledSelectSection = styled.div`
  display: flex;
  align-items: center;
  padding: 16px;
  margin-bottom: 36px;
  border-bottom: 3px solid #f2f2f2;
`

const StyledSectionNumber = styled.span`
  margin-right: 8px;
  width: 28px;
  height: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  background: #b0dedb;
  font-size: 16px;
  font-weight: 700;
  line-height: 19px;
  color: #fff;
`

const StyledSelectSectionTitle = styled.h2`
  font-weight: 700;
  font-size: 24px;
  line-height: 40px;
  letter-spacing: -0.05em;
  margin: 0;
`
const StyledPostSelect = styled.ul`
  margin-top: 40px;
  display: flex;
  grid-gap: 15px;
  gap: 15px;
  list-style: none;
`

const StyledPostSelectList = styled.li`
  flex: 1;
`

const StyledDemoContainer = styled(DemoContainer)`
  /* .css-4jnixx-MuiStack-root > .MuiTextField-root { */
  min-width: 100%;
  /* } */
`
