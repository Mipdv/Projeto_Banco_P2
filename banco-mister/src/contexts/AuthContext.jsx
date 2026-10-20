import { createContext, useContext, useState, useCallback } from 'react'
import { authApi } from '../api/authApi'
import { tokenStorage } from '../api/httpClient'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [autenticado, setAutenticado] = useState(Boolean(tokenStorage.get()))

  const entrar = useCallback(async (credenciais) => {
    // O back-end devolve "Bearer <jwt>"; guardamos só o jwt, o httpClient recoloca o prefixo.
    const resposta = await authApi.login(credenciais)
    tokenStorage.set(resposta.replace(/^Bearer\s+/, ''))
    setAutenticado(true)
  }, [])

  const sair = useCallback(() => {
    tokenStorage.clear()
    setAutenticado(false)
  }, [])

  return (
    <AuthContext.Provider value={{ autenticado, entrar, sair }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
