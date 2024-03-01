import { PropsWithChildren, createContext, useContext } from 'react'
import { BaseRepository } from '../baseRepository'
import { Chat } from '../../models/chat'

export class ChatService {
  private chatRepository: BaseRepository<Chat>

  constructor(articleRepository: BaseRepository<Chat>) {
    this.chatRepository = articleRepository
  }

  async createChatRoom(data: Chat) {
    return this.chatRepository.create<any>(
      `chat-service/api/room/create/test`,
      data,
    )
  }
}

const ChatContext = createContext(new ChatService(new BaseRepository()))

export const ChatProvider = ({ children }: PropsWithChildren) => {
  const userService = new ChatService(new BaseRepository())

  return (
    <ChatContext.Provider value={userService}>{children}</ChatContext.Provider>
  )
}

export const useArticleService = () => useContext(ChatContext)
