import http from './httpClient'

// O login usa CPF (somente dígitos) + senha, como no design.
// AJUSTAR os caminhos e o formato do JSON conforme o contrato combinado com o back-end.
export const authApi = {
  login: (credenciais) => http.post('/auth/login', credenciais).then((r) => r.data),
  me: () => http.get('/auth/me').then((r) => r.data),
}
