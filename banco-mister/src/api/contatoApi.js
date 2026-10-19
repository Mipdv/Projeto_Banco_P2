import http from './httpClient'

// Usado pelo bloco "Quero conversar". AJUSTAR endpoint/campos com o back-end.
export const contatoApi = {
  solicitarContato: (dados) => http.post('/contatos', dados).then((r) => r.data),
}
