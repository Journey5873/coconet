import { AxiosResponse } from 'axios'
import { HttpClient } from './httpClient'

export interface ApiResponse<T> {
  data?: T
  succeeded?: boolean
  errors: any
}

export interface ApiResponsePageable<T> {
  data?: T
  succeeded?: boolean
  errors: any
  pageable: Record<string, any>
  totalPages: number
  totalElements: number
}

export interface IBaseRepository<T> {
  get(path: string): Promise<ApiResponse<T>>
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

/**
 * 페이징 형태의 응답을 변경
 * TODO : 필요한 타입 만들기
 * @param response
 * @returns
 */
const transformPageable = (
  response: any,
): Promise<ApiResponsePageable<any>> => {
  return new Promise((resolve, _) => {
    const result: ApiResponsePageable<any> = {
      data: response.data.content,
      pageable: response.data.pageable,
      totalPages: response.data.totalPages,
      totalElements: response.data.totalElements,
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

  public async get(path: string): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.get(`/${path}`).then(transform)
    return result as ApiResponse<T>
  }

  public async getMany(path: string): Promise<ApiResponse<T[]>> {
    const instance = this.createInstance()
    const result = await instance.get(`/${path}`).then(transform)
    return result as ApiResponse<T[]>
  }

  public async getManyPagableWithBody<Dto>(
    path: string,
    item: Dto,
  ): Promise<ApiResponsePageable<T[]>> {
    const instance = this.createInstance()
    const result = await instance.post(`/${path}`, item).then(transformPageable)
    return result as ApiResponsePageable<T[]>
  }

  public async getManyPagable(path: string): Promise<ApiResponsePageable<T[]>> {
    const instance = this.createInstance()
    const result = await instance.get(`/${path}`).then(transformPageable)
    return result as ApiResponsePageable<T[]>
  }

  public async create<Dto>(path: string, item: Dto): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.post(`/${path}`, item).then(transform)
    return result as ApiResponse<T>
  }

  public async createMultiPart<Dto>(
    path: string,
    item: Dto,
  ): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance
      .post(`/${path}`, item, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(transform)
    return result as ApiResponse<T>
  }

  public async updateMultiPart<Dto>(
    path: string,
    item: Dto,
  ): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance
      .put(`/${path}`, item, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(transform)
    return result as ApiResponse<T>
  }

  public async update<Dto>(path: string, item: Dto): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.put(`/${path}`, item).then(transform)
    return result as ApiResponse<T>
  }

  public async updateExceptBody(path: string): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.put(`/${path}`).then(transform)
    return result as ApiResponse<T>
  }

  public async delete(path: string, item?: any): Promise<ApiResponse<T>> {
    const instance = this.createInstance()
    const result = await instance.delete(`/${path}`, item).then(transform)
    return result as ApiResponse<T>
  }
}
