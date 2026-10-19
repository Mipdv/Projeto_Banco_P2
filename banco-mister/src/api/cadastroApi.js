import http from './httpClient'

// AJUSTAR endpoint e nomes dos campos conforme o contrato com o back-end Java.
export const cadastroApi = {
  criar: (dados) => http.post('/cadastros', dados).then((r) => r.data),
}
