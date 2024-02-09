import { useEffect, useRef } from 'react'

interface Props {
  callBack: () => void
}

export default function useClose({ callBack }: Props) {
  const ref = useRef(null)

  const onClickSideBarClose = (e: any) => {
    if (ref.current == null) return

    const current = ref.current as any

    if (!(current instanceof HTMLElement)) return

    if (!current.contains(e.target)) {
      callBack()
    }
  }

  useEffect(() => {
    document.addEventListener('mousedown', onClickSideBarClose)

    return () => {
      document.removeEventListener('mousedown', onClickSideBarClose)
    }
  }, [])

  return {
    ref,
  }
}
