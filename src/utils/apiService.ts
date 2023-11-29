import axios, { AxiosError } from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { RegisterDto } from '../components/organisms/modal/additionalModal'

type IConfig = AxiosRequestConfig

const requestConfig: AxiosRequestConfig = {
  baseURL: 'http://localhost:8000',
  timeout: 2500,
  headers: {
    'Content-Type': 'application/json',
  },
}

class ApiService {
  private api: AxiosInstance

  constructor() {
    this.api = axios.create(requestConfig)

    // 인터셉트 처리
    this.api.interceptors.request.use(
      (config) => {
        return config
      },
      (error: AxiosError) => {
        console.log(error)
        return Promise.reject(error)
      },
    )

    this.api.interceptors.response.use(
      (res: AxiosResponse) => {
        return res
      },
      (error: AxiosError) => {
        console.log(error)
        return Promise.reject(error)
      },
    )
  }

  public async register(regiserInfo: RegisterDto, config?: IConfig) {
    return this.api.post('', regiserInfo, config)
  }

  public async getProfile(config?: IConfig) {
    return this.api.get('', config)
  }

  public async getPosts(config?: IConfig) {
    return this.api.get('', config)
  }
}

export default new ApiService()
