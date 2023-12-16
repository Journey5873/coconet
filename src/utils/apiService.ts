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

  /**
   * 임시 로그인
   * @returns
   */
  public async login() {
    return {
      token: '123',
      name: 'hello1',
      career: 10,
      profileImg: 'member-service/src/main/resources/memberProfilePics/70.png',
      bio: 'hello1-updated',
      githubLink: 'github link',
      blogLink: 'blog_link',
      notionLink: 'notion_link',
      roles: ['Backend', 'Frontend'],
      stacks: ['Git', 'Java', 'Mysql'],
    }
  }

  public async register(regiserInfo: RegisterDto, config?: IConfig) {
    return this.api.post(
      '/member-service/open-api/register',
      regiserInfo,
      config,
    )
  }

  public async getProfile(config?: IConfig) {
    return this.api.get('', config)
  }

  public async getPosts(config?: IConfig) {
    return this.api.get('', config)
  }
}

export default new ApiService()
