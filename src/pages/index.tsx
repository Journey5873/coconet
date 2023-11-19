import React, { useState, useCallback } from 'react'
import useFetch from '../hooks/useFetch'
import MultiStackSelector from '../components/organisms/multiStackSelector/multiStackSelector'
import CustomCarousel from '../components/organisms/Carousel'
import styled from 'styled-components'
import Tab from '../components/organisms/customTabs/Tab'
import Tabs from '../components/organisms/customTabs/Tabs'
import Card from '../components/molecules/card/card'
import FilterSelect from '../components/atoms/Select/filterSelect'
import { DummyData, dummyData } from '../data/data'

const Index = () => {
  const [selected, setSelected] = useState<string[]>([])
  const [selectedPosition, setSelectedPosition] = useState<string>('')

  /**
   * 기술을 선택하는 함수
   */
  const hanldeSelected = useCallback(
    (value: string) => {
      if (checkFor()) {
        const filteredSelected = selected.filter((skill) => skill !== value)
        setSelected(filteredSelected)
      }

      if (!checkFor()) {
        setSelected((prev) => [...prev, value])
      }

      function checkFor() {
        return selected.indexOf(value) >= 0
      }
    },
    [selected],
  )

  /**
   * 포지션을 선택하는 함수
   */
  const handlePosiionSelected = useCallback((value: string) => {
    setSelectedPosition(value)
  }, [])

  return (
    <>
      <div>
        <StyledContents>
          <CustomCarousel />
          <Tabs
            options={[
              { name: '전체' },
              { name: '프로젝트' },
              { name: '스터디' },
            ]}
          >
            <Tab value="전체">
              <div style={{ display: 'flex', columnGap: '1rem' }}>
                <MultiStackSelector
                  handleSelected={hanldeSelected}
                  selected={selected}
                />
                <FilterSelect
                  title="포지션"
                  selected={selectedPosition}
                  handleSelected={handlePosiionSelected}
                  options={['프론트엔드', '백엔드', '데브옵스', '디자이너']}
                />
                {/* <FilterSelect
                  title="진행방식"
                  options={['온라인', '오프라인', '온/오프라인']}
                /> */}
              </div>
              <StyledItemWrpper>
                {[dummyData].map((item) => (
                  <Card item={item} />
                ))}
              </StyledItemWrpper>
            </Tab>
            <Tab value="프로젝트">dd</Tab>
            <Tab value="스터디">dd</Tab>
          </Tabs>
        </StyledContents>
      </div>
    </>
  )
}

export default Index

const StyledContents = styled.div`
  width: 1220px;
  margin: 0 auto;
`

const StyledItemWrpper = styled.div`
  margin-top: 1rem;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
`
