import { createSlice } from '@reduxjs/toolkit'

// positionList : 선택된 포지션을 담고 있는 어레이

const positionList = createSlice({
  name: 'positionList',
  initialState: [
    { value: 'Backend', label: 'Backend', count: 0, id: 0 },
    { value: 'Frontend', label: 'Frontend', count: 0, id: 1 },
    { value: 'Designer', label: 'Designer', count: 0, id: 2 },
    { value: 'IOS', label: 'IOS', count: 0, id: 3 },
    { value: 'Android', label: 'Android', count: 0, id: 4 },
    { value: 'PM', label: 'PM', count: 0, id: 5 },
    { value: 'QA', label: 'QA', count: 0, id: 6 },
    { value: 'GameDev', label: 'GameDev', count: 0, id: 7 },
    { value: 'DevOps', label: 'DevOps', count: 0, id: 8 },
  ],
  reducers: {
    increase(state, action) {
      const index = state.findIndex((item) => item.id === action.payload)
      state[index].count += 1
    },
    decrease(state, action) {
      const index = state.findIndex((item) => item.id === action.payload)
      if (state[index].count > 0) {
        state[index].count -= 1
      }
    },
  },
})

export const { increase, decrease } = positionList.actions

export default positionList
