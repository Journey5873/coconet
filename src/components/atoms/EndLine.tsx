import React from 'react';
import styled from 'styled-components';

interface Props {
  endline : string;
}

const Endline = ({endline}:Props) => {
    return(
        <StyledScheduleWrppaer>
            <StyledScheduleTitle>마감일 |</StyledScheduleTitle>
            <StyledSchedule>{endline}</StyledSchedule>
        </StyledScheduleWrppaer>
    )
}

const StyledScheduleWrppaer = styled.div`
  margin-top: 20px;
  display: flex;
  align-items : center;
  grid-gap: 8px;
  gap: 8px;
`;

const StyledScheduleTitle = styled.p`
  font-size: 14px;
  color: #999;
  font-weight: 700;  
`;

const StyledSchedule = styled.p`
  margin: 0;
  font-size: 14px;
  color: #999;
  font-weight: 700;  
`;
export default Endline;