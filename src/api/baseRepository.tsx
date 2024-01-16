import { AxiosResponse } from 'axios'
import { HttpClient } from './httpClient'

export interface ApiResponse<T> {
  data?: T
  succeeded?: boolean
  errors: any
}

export interface IBaseRepository<T> {
  get(path: string, id: string): Promise<ApiResponse<T>>
  getMany(path: string): Promise<ApiResponse<T[]>>
  create<Dto>(path: string, item: Dto): Promise<ApiResponse<T>>
  update<Dto>(path: string, id: string, item: Dto): Promise<ApiResponse<T>>
  delete(path: string, id: string): Promise<ApiResponse<T>>
}

/**
 * 완료된 요청을 ApiResponse 형태로 변경
 * @param response
 * @returns
 */
const transform = (response: AxiosResponse): Promise<ApiResponse<any>> => {
  return new Promise((resolve, _) => {
    const result: ApiResponse<any> = {
      data: response.data,
      succeeded: response.status === 200,
      errors: response.data.errors,
    }
    resolve(result)
  })
}

export class BaseRepository<T>
  extends HttpClient
  implements IBaseRepository<T>
{
  constructor() {
    super()
  }

  public async get(path: string, id: string): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.get(`/${path}/${id}`).then(transform)
    return result as ApiResponse<T>
  }

  public async getMany(path: string): Promise<ApiResponse<T[]>> {
    const instance = this.createInstance()
    const result = await instance.get(`/${path}`).then(transform)
    return result as ApiResponse<T[]>
  }

  public async create<Dto>(path: string, item: Dto): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.post(`/${path}`, item).then(transform)
    return result as ApiResponse<T>
  }

  public async update<Dto>(
    path: string,
    id: string,
    item: Dto,
  ): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.put(`/${path}/id`, item).then(transform)
    return result as ApiResponse<T>
  }

  public async delete(path: string, id: string): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.delete(`/${path}/${id}`).then(transform)
    return result as ApiResponse<T>
  }
}
