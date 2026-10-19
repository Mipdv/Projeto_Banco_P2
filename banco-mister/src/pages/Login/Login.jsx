import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthLayout from '../../components/auth/AuthLayout'
import { Field } from '../../components/auth/Field'
import SubmitButton from '../../components/auth/SubmitButton'
import { Lock, Eye, EyeOff, Help } from '../../components/icons'
import { useAuth } from '../../contexts/AuthContext'
import monograma from '../../assets/monograma-dourado.png'
import { maskCpf, somenteDigitos, cpfValido } from '../../utils/formatters'

export default function Login() {
  const { entrar } = useAuth()
  const navigate = useNavigate()
  const [form, setForm] = useState({ cpf: '', senha: '' })
  const [erros, setErros] = useState({})
  const [mostrar, setMostrar] = useState(false)
  const [enviando, setEnviando] = useState(false)
  const [erroGeral, setErroGeral] = useState('')

  async function enviar(e) {
    e.preventDefault()
    const novos = {}
    if (!cpfValido(form.cpf)) novos.cpf = 'Informe um CPF válido.'
    if (!form.senha) novos.senha = 'Digite sua senha.'
    setErros(novos)
    setErroGeral('')
    if (Object.keys(novos).length) return

    setEnviando(true)
    try {
      await entrar({ cpf: somenteDigitos(form.cpf), senha: form.senha })
      navigate('/') // TODO: trocar pela área logada (ex.: /conta) quando existir
    } catch (err) {
      setErroGeral(err.response?.status === 401 ? 'CPF ou senha incorretos.' : 'Não foi possível entrar agora. Tente novamente em instantes.')
    } finally {
      setEnviando(false)
    }
  }

  return (
    <AuthLayout
      width="sm"
      back={{ to: '/', label: 'Voltar ao início' }}
      topRight={<a href="#ajuda"><Help /> Precisa de ajuda?</a>}
      footer={<><span>Ambiente protegido</span><span>© Mister Banco Privado</span></>}
      aside={{
        eyebrow: 'Área exclusiva',
        title: 'Sua casa financeira, sempre por perto.',
        text: 'Acesse sua conta, acompanhe seu patrimônio e fale com quem conhece suas escolhas.',
        bottom: (
          <div className="auth__brand-foot">
            <div>
              <img src={monograma} alt="" />
              <small>Confiança que se constrói</small>
            </div>
            <p>Segurança, transparência e presença em cada detalhe.</p>
          </div>
        ),
      }}
    >
      <div className="lock-box"><Lock /></div>
      <p className="auth__eyebrow">Acesso à conta</p>
      <h1>Bem-vindo de volta.</h1>
      <p className="auth__lead" style={{ marginBottom: 32 }}>Entre com seus dados para continuar de onde parou.</p>

      <form onSubmit={enviar} noValidate>
        <div style={{ display: 'grid', gap: 28 }}>
          <Field
            label="CPF" inputMode="numeric" autoComplete="username" placeholder="000.000.000-00"
            value={form.cpf} error={erros.cpf}
            onChange={(e) => setForm({ ...form, cpf: maskCpf(e.target.value) })}
          />
          <Field
            label="Senha" type={mostrar ? 'text' : 'password'} autoComplete="current-password" placeholder="Digite sua senha"
            value={form.senha} error={erros.senha}
            onChange={(e) => setForm({ ...form, senha: e.target.value })}
            right={
              <button type="button" className="field__toggle" onClick={() => setMostrar(!mostrar)} aria-label={mostrar ? 'Ocultar senha' : 'Mostrar senha'}>
                {mostrar ? <EyeOff /> : <Eye />}
              </button>
            }
          />
        </div>
        <div style={{ display: 'grid' }}><a href="#esqueci" className="auth__small-link">Esqueci minha senha</a></div>
        <SubmitButton loading={enviando}>Acessar minha conta</SubmitButton>
        {erroGeral && <p role="alert" className="auth__alert">{erroGeral}</p>}
      </form>

      <hr className="auth__divider" />
      <p className="auth__switch">Ainda não é cliente? <Link to="/cadastro">Conheça o Mister</Link></p>
    </AuthLayout>
  )
}
