import styled from "styled-components"


// type BadgeType = 'study' | 'project' | 'new' | 'hot' | 'deadline';

// export default function CardListBadge({ type }: Props) {
//   let defaultBadge = 응답 받은 쿼리 = 프로젝트 ? '🗂 프로젝트': '✏️ 스터디';
//   let addBadge = '';
//   switch (badgeType) {
//     case 마감코앞:
//       addBadge = '🔥 마감코앞';
//       break;
//     case 인기:
//       addBadge = '💙 인기';
//       break;
//     case 새글:
//       addBadge = '🍞 따끈따끈 새 글';
//       break;
//   }
//     return (
//         <StyledBadge>
//           <StyledDefaultBadge>{defaultBadge}</StyledDefaultBadge>
//           <StyledAddBadge type={type}>{addBadge && addBadge}</StyledAddBadge>
//         </StyledBadge>
//     )
// }

const StyledBadge = styled.div`
    display: flex;
    align-items: center;
    grid-gap: 3px;
    gap: 3px;
    font-weight: 800;
    font-size: 12px;
    line-height: 16px;
    letter-spacing: -.04em;
`

const StyledDefaultBadge = styled.div`
    border-radius: 20px;
    padding: 4px 12px;
    background: #efefef;
    color: #656565;
`

const StyledAddBadge = styled.div<{type:string}>`
    border-radius: 20px;
    padding: 4px 12px;
      // 타입이 새 글일 떄
    background: #fff9d5;
    color: #fdb900;
  // 타입이 인기일 때
    background: #f1f7ff;
    color: #74a0e2;
  // 타입이 마감 코 앞일 때
    background: #ffe8e8;
    color: #ea726f;
`