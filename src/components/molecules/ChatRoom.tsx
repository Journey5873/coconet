import { CompatClient, Stomp } from '@stomp/stompjs'
import { useEffect, useRef, useState } from 'react'
import { useAppSelector } from '../../store/RootReducer'
import SockJS from 'sockjs-client'
import styled from 'styled-components'

const tempUrl = 'http://localhost:8000'

const ChatRoom = ({ roomId, userId }: { roomId: string; userId: string }) => {
  const [inputMessage, setInputMessage] = useState('')
  const [messages, setMessages] = useState<any[]>([])
  const token = useAppSelector((state) => state.reducer.auth.token)
  // const name = useAppSelector((state) => state.reducer.auth.name)
  const client = useRef<CompatClient>()

  const connectHanlder = () => {
    client.current = Stomp.over(() => {
      const sock = new SockJS(`${tempUrl}/chat-service/api/ws/chat`)
      return sock
    })

    client.current.connect(
      // {
      //   Authorization: `bearer ${token}`,
      // },
      {},
      () => {
        if (!client.current) return

        client.current.subscribe(`/queue/room/${roomId}`, (messageResponse) => {
          console.log('Connected!!')
          // getMessages(messageResponse.body)
        })
      },
    )
  }

  const getMessages = (messageResponse: any) => {
    if (messageResponse.senderUUID === userId) {
      setMessages((prev) => [...prev, { ...messageResponse, isMine: true }])
    } else {
      setMessages((prev) => [...prev, { ...messageResponse, isMine: false }])
    }
  }

  const sendHandler = () => {
    if (!client.current) return

    client.current.send(
      `/message`,
      {
        Authorization: `bearer ${token}`,
      },
      JSON.stringify({
        roomUUID: roomId,
        senderUUID: userId,
        message: inputMessage,
      }),
    )
  }

  const disconnectHanlder = () => {
    debugger
    if (client.current) {
      client.current.disconnect()
    }
  }

  useEffect(() => {
    connectHanlder()

    // return () => {
    //   disconnectHanlder()
    // }
  }, [])

  return (
    <StyledChatRoom>
      <StyledMessageList>
        {messages.map((message, index) => (
          <StyledMessage key={index} isMine={message.isMine}>
            {message.message}
          </StyledMessage>
        ))}
      </StyledMessageList>
      <div>
        <StyledInput
          type="text"
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
        />
        <StyledButton onClick={sendHandler}>Send</StyledButton>
      </div>
    </StyledChatRoom>
  )
}

export default ChatRoom

const StyledChatRoom = styled.div`
  padding: 20px;
  background-color: #f7f7f7;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  margin: auto;
`

const StyledMessageList = styled.div`
  max-height: 300px;
  overflow-y: auto;
  padding: 10px;
  background: #fff;
  border: 1px solid #ddd;
  margin-bottom: 10px;
  border-radius: 4px;
`

const StyledMessage = styled.div<{
  isMine: boolean
}>`
  background: ${(props) => (props.isMine ? '#dcf8c6' : '#fff')};
  padding: 5px 10px;
  margin-bottom: 10px;
  border-radius: 4px;
  border: 1px solid #ddd;
  max-width: 70%;
  align-self: ${(props) => (props.isMine ? 'flex-end' : 'flex-start')};
`

const StyledInput = styled.input`
  width: calc(100% - 90px);
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ddd;
  margin-right: 10px;
`

const StyledButton = styled.button`
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: #45a049;
  }
`
