import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'

export default function PrivateRoute() {
  const { autenticado } = useAuth()
  return autenticado ? <Outlet /> : <Navigate to="/login" replace />
}
