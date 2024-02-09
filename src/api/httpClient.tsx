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
      headers: {
        'Content-Type': 'application/json',
      },
    }

    this.instance = axios.create(requestConfig)

    this.instance.interceptors.request.use(
      (config) => {
        const accessToken = localStorage?.getItem('accessToken')
        console.log(accessToken, 'accessToken')
        if (accessToken) {
          config.headers.Authorization = `Bearer ${accessToken}`
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

  private handleResponse = ({ data }: AxiosResponse): AxiosResponse => data

  private handleError = (error: AxiosError): Promise<AxiosError> => {
    // const originalRequest = error.config;
    // if (error.response?.status === 401 && !originalRequest?._retry) {
    //   originalRequest._retry = true;
    //   try {
    //     const newToken = await this.refreshToken();
    //     localStorage.setItem('accessToken', newToken);
    //     return this.instance.request(originalRequest);
    //   } catch (refreshError) {
    //     return Promise.reject(refreshError);
    //   }
    // }
    return Promise.reject(error)
  }
}
