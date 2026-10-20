import http from './httpClient'

// O back-end autentica por e-mail + senha e devolve o token como texto: "Bearer <jwt>".
export const authApi = {
  login: (credenciais) => http.post('/usuario/login', credenciais).then((r) => r.data),
}
