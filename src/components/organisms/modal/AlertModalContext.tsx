import { createContext, PropsWithChildren, useState, useRef } from 'react'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'
import Modal from '@mui/material/Modal'

interface OnOpenProps {
  title: string
  content: string
  onConfirm: () => void
}

interface Props {
  onOpen: (props: OnOpenProps) => void
  onClose: () => void
}

export const AlertContext = createContext<Props | null>(null)

const AlertProvider = ({ children }: PropsWithChildren) => {
  const [title, setTitle] = useState<string>('')
  const [content, setContent] = useState<string>('')
  const [isOpen, setIsOpen] = useState<boolean>(false)

  const handleConfirm = useRef(() => console.log(''))

  const handleClose = () => setIsOpen(false)

  const handleOpen = ({ content, onConfirm, title }: OnOpenProps) => {
    setTitle(title)
    setContent(content)
    handleConfirm.current = onConfirm
    setIsOpen(true)
  }

  const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    display: 'flex',
    flexDirection: 'column',
    boxShadow: 24,
    p: 4,
  }

  const value: Props = {
    onClose: handleClose,
    onOpen: handleOpen,
  }
  return (
    <AlertContext.Provider value={value}>
      {children}
      <Modal
        open={isOpen}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            {title}
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            {content}
          </Typography>

          <div
            style={{
              display: 'flex',
              gap: '1rem',
              alignSelf: 'end',
            }}
          >
            <Button onClick={() => handleConfirm.current()}>확인</Button>
            <Button onClick={handleClose}>취소</Button>
          </div>
        </Box>
      </Modal>
    </AlertContext.Provider>
  )
}

export default AlertProvider
