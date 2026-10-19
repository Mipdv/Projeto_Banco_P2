import { Routes, Route } from 'react-router-dom'
import Layout from '../components/Layout'
import PrivateRoute from './PrivateRoute'
import Home from '../pages/Home/Home'
import Login from '../pages/Login/Login'
import Cadastro from '../pages/Cadastro/Cadastro'

// Nova página: crie a pasta em src/pages e registre a rota aqui.
export default function AppRoutes() {
  return (
    <Routes>
      {/* Telas de acesso: layout próprio, sem header/footer do site */}
      <Route path="/login" element={<Login />} />
      <Route path="/cadastro" element={<Cadastro />} />

      {/* Site institucional */}
      <Route element={<Layout />}>
        <Route path="/" element={<Home />} />
        {/* Páginas que exigem login ficam dentro de PrivateRoute */}
        <Route element={<PrivateRoute />}>
          {/* <Route path="/conta" element={<Conta />} /> */}
        </Route>
      </Route>
    </Routes>
  )
}
