import { createSlice } from "@reduxjs/toolkit";

// positionList : 선택된 포지션을 담고 있는 어레이

let positionList = createSlice({
  name: 'positionList',
  initialState: [
  { value: 'JavaScript', label: 'JavaScript', count : 0, id : 0 },
  { value: 'TypeScript', label: 'TypeScript', count : 0, id : 1 },
  { value: 'React', label: 'React', count : 0, id : 2 },
  { value: 'Vue', label: 'Vue', count : 0, id : 3 },
  { value: 'Nodejs', label: 'Nodejs', count : 0, id : 4 },
  { value: 'Java', label: 'Java', count : 0, id : 5 },
  { value: 'Spring', label: 'Spring', count : 0, id : 6},
  { value: 'Kotlin', label: 'Kotlin', count : 0, id : 7 },
  { value: 'C++', label: 'C++', count : 0, id : 8 },
  { value: 'Go', label: 'Go', count : 0, id : 9},
  { value: 'Python', label: 'Python', count : 0, id : 10 },
  { value: 'Django', label: 'Django', count : 0, id : 11 },
  { value: 'Flutter', label: 'Flutter', count : 0, id : 12},
  { value: 'Swift', label: 'Swift', count : 0, id : 13 },
  { value: 'ReactNative',label: 'ReactNative',count : 0, id : 14},
  { value: 'Unity', label: 'Unity', count : 0, id : 15 },
  { value: 'AWS', label: 'AWS', count : 0, id : 16},
  { value: 'Kubernetes', label: 'Kubernetes', count : 0 , id : 17},
  { value: 'Docker', label: 'Docker', count : 0, id : 18 },
  { value: 'Git', label: 'Git', count : 0, id : 19 },
  { value: 'Figma', label: 'Figma', count : 0, id : 20 },
  { value: 'Zeplin', label: 'Zeplin', count : 0, id : 21 },
  { value: 'Jest', label: 'Jest', count : 0, id : 22 },
  ],
  reducers: {

    increase(state, action) {
      const index = state.findIndex((item) => item.id === action.payload)
      state[index].count += 1;;
    },
    decrease(state, action) {
      const index = state.findIndex((item) => item.id === action.payload)
      if (state[index].count > 0) {
        state[index].count -= 1;
      }
    },
  }
})

export let { increase, decrease } = positionList.actions;


export default positionList;