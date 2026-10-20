import http from './httpClient'

// POST /usuario no back-end Java (corpo no formato da entity Usuario).
export const cadastroApi = {
  criar: (dados) => http.post('/usuario', dados).then((r) => r.data),
}
