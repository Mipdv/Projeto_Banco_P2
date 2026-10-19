import { createContext, useContext, useState, useCallback } from 'react'
import { authApi } from '../api/authApi'
import { tokenStorage } from '../api/httpClient'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [autenticado, setAutenticado] = useState(Boolean(tokenStorage.get()))

  const entrar = useCallback(async (credenciais) => {
    const { token } = await authApi.login(credenciais) // ajustar ao retorno do Java
    tokenStorage.set(token)
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
