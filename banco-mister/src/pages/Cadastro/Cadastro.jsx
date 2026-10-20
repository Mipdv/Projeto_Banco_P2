import { useState } from 'react'
import { Link } from 'react-router-dom'
import AuthLayout from '../../components/auth/AuthLayout'
import Stepper from '../../components/auth/Stepper'
import { Shield } from '../../components/icons'
import StepDados from './StepDados'
import StepInvestimento, { OBJETIVOS, VOLUMES, PERFIS } from './StepInvestimento'
import StepConfirmacao from './StepConfirmacao'

// Textos do painel azul e do rodapé para cada passo.
const PASSOS = {
  1: {
    eyebrow: 'Primeiro passo',
    title: 'Uma relação mais inteligente com o seu dinheiro começa com uma conversa.',
    text: 'Preencha seus dados. Em seguida, alguém do nosso time entra em contato para conhecer a sua história.',
    seguranca: 'Seus dados são tratados com confidencialidade.',
    footer: ['Ambiente protegido', '© Mister Banco Privado'],
  },
  2: {
    eyebrow: 'Segundo passo',
    title: 'Entender o seu momento nos ajuda a guiar o seu amanhã.',
    text: 'Para planejar a melhor arquitetura financeira, precisamos conhecer seus objetivos patrimoniais e sua experiência atual.',
    seguranca: 'Seus dados são tratados com estrita confidencialidade.',
    footer: ['Ambiente protegido por criptografia de ponta', '© Mister Banco Privado de Investimentos'],
  },
  3: {
    eyebrow: 'Terceiro passo',
    title: 'Uma boa conversa é o começo de uma nova perspectiva.',
    text: 'Obrigado pela confiança. Nosso time vai conhecer sua história e ajudar a planejar seus próximos passos.',
    seguranca: 'Seus dados são tratados com estrita confidencialidade.',
    footer: ['Ambiente protegido por criptografia de ponta', '© Mister Banco Privado de Investimentos'],
  },
}

export default function Cadastro() {
  const [passo, setPasso] = useState(1)
  const [dados, setDados] = useState({ nome: '', cpf: '', email: '', celular: '', dataNascimento: '', senha: '' })
  const [investimento, setInvestimento] = useState({ objetivo: OBJETIVOS[0], volume: VOLUMES[1], perfil: PERFIS[2] })
  const p = PASSOS[passo]

  const voltar = passo === 2 ? { onClick: () => setPasso(1), label: 'Passo anterior' } : { to: '/', label: 'Voltar ao início' }

  return (
    <AuthLayout
      back={voltar}
      footer={<><span>{p.footer[0]}</span><span>{p.footer[1]}</span></>}
      aside={{
        eyebrow: p.eyebrow,
        title: p.title,
        text: p.text,
        topRight: passo === 1 ? <Link to="/login">Já sou cliente</Link> : null,
        bottom: (
          <div>
            <Stepper atual={passo} />
            <p className="auth__security"><Shield /> {p.seguranca}</p>
          </div>
        ),
      }}
    >
      {passo === 1 && <StepDados dados={dados} setDados={setDados} onNext={() => setPasso(2)} />}
      {passo === 2 && <StepInvestimento dados={dados} investimento={investimento} setInvestimento={setInvestimento} onDone={() => setPasso(3)} />}
      {passo === 3 && <StepConfirmacao />}
    </AuthLayout>
  )
}
