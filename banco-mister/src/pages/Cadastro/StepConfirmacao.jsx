import { Link } from 'react-router-dom'
import SubmitButton from '../../components/auth/SubmitButton'
import { Mail } from '../../components/icons'

export default function StepConfirmacao() {
  return (
    <>
      <p className="auth__eyebrow">Cadastro Mister · 03 de 03</p>
      <h1>Recebemos o seu cadastro.</h1>
      <p className="auth__lead" style={{ marginBottom: 36 }}>
        Seu cadastro foi enviado com sucesso. A partir de agora, nosso time se prepara para a primeira conversa com você.
      </p>
      <div className="next-steps">
        <p className="auth__eyebrow">O que acontece agora</p>
        <div className="next-steps__item">
          <span className="next-steps__icon"><Mail /></span>
          <div>
            <h3>Confirmação por e-mail</h3>
            <p>Enviamos uma confirmação para o e-mail informado no cadastro. Confira também a pasta de spam ou lixo eletrônico.</p>
          </div>
        </div>
      </div>
      <SubmitButton as={Link} to="/">Voltar ao início</SubmitButton>
    </>
  )
}
