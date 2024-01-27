import axios, {
  AxiosError,
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
} from 'axios'

export abstract class HttpClient {
  protected instance: AxiosInstance | undefined

  protected createInstance(): AxiosInstance {
    const requestConfig: AxiosRequestConfig = {
      baseURL: 'http://localhost:8000',
      timeout: 2500,
      headers: {
        'Content-Type': 'application/json',
      },
    }

    this.instance = axios.create(requestConfig)

    this.instance.interceptors.request.use(
      (config) => {
        const token = localStorage?.getItem('token')
        if (token) {
          config.headers.common['Authorization'] = `Bearer ${token}`
        }
        return config
      },
      (error: AxiosError) => {
        console.log(error)
        return Promise.reject(error)
      },
    )

    this.instance.interceptors.response.use(
      this.handleResponse,
      this.handleError,
    )

    return this.instance
  }

  private handleResponse = ({ data }: AxiosResponse) => data

  private handleError = (error: any) => Promise.reject(error)
}
