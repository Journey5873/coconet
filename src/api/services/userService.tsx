import { User } from '../../models/user'
import { PropsWithChildren, createContext, useContext } from 'react'
import { BaseRepository } from '../baseRepository'

export class UserService {
  private userRepository: BaseRepository<User>

  constructor(userRepository: BaseRepository<User>) {
    this.userRepository = userRepository
  }

  async getMyProfile() {
    return this.userRepository.get('member-service/api/my-profile')
  }

  async updateMyProfile(data: any) {
    return this.userRepository.updateMultiPart(
      'member-service/api/my-profile',
      data,
    )
  }

  async createUser<Dto>(item: Dto) {
    return this.userRepository.createMultiPart<Dto>(
      'member-service/open-api/register',
      item,
    )
  }

  async updateUser<Dto>(data: Dto, id: string) {
    return this.userRepository.update<Dto>('', id, data)
  }

  async deleteUser(id: string) {
    return this.userRepository.delete('', id)
  }

  async getUserById(memberUUID: string) {
    return this.userRepository.get(`member-service/open-api/${memberUUID}`)
  }

  async checkUsername(name: string) {
    return this.userRepository.get(
      `member-service/open-api/memberNameCheck/${name}`,
    )
  }
}

const UserContext = createContext(new UserService(new BaseRepository()))

export const UserProvier = ({ children }: PropsWithChildren) => {
  const userService = new UserService(new BaseRepository())

  return (
    <UserContext.Provider value={userService}>{children}</UserContext.Provider>
  )
}

export const useUserService = () => useContext(UserContext)
