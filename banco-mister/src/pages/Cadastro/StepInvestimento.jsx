import { useState } from 'react'
import { SelectField } from '../../components/auth/Field'
import SubmitButton from '../../components/auth/SubmitButton'
import { cadastroApi } from '../../api/cadastroApi'
import { somenteDigitos } from '../../utils/formatters'

export const OBJETIVOS = [
  'Preservação de capital e sucessão patrimonial',
  'Crescimento do patrimônio no longo prazo',
  'Geração de renda recorrente',
  'Planejamento de aposentadoria',
  'Diversificação internacional',
]
export const VOLUMES = [
  'Até R$ 1.000.000',
  'Entre R$ 1.000.000 e R$ 5.000.000',
  'Entre R$ 5.000.000 e R$ 20.000.000',
  'Acima de R$ 20.000.000',
]
export const PERFIS = [
  'Ainda não invisto',
  'Invisto por conta própria',
  'Possuo carteira diversificada com assessoria',
  'Tenho gestão exclusiva de patrimônio',
]

export default function StepInvestimento({ dados, investimento, setInvestimento, onDone }) {
  const [enviando, setEnviando] = useState(false)
  const [erro, setErro] = useState('')
  const set = (campo) => (e) => setInvestimento({ ...investimento, [campo]: e.target.value })

  async function enviar(e) {
    e.preventDefault()
    setEnviando(true)
    setErro('')
    try {
      await cadastroApi.criar({
        nome: dados.nome.trim(),
        cpf: somenteDigitos(dados.cpf),
        email: dados.email.trim(),
        celular: somenteDigitos(dados.celular),
        ...investimento, // objetivo, volume, perfil
      })
      onDone()
    } catch (err) {
      setErro(
        err.response?.status === 409
          ? 'Já existe um cadastro com este CPF ou e-mail.'
          : 'Não foi possível concluir o cadastro. Tente novamente em instantes.',
      )
    } finally {
      setEnviando(false)
    }
  }

  return (
    <>
      <p className="auth__eyebrow">Cadastro Mister · 02 de 03</p>
      <h1>Compartilhe seus objetivos e seu momento</h1>
      <p className="auth__lead" style={{ marginBottom: 28 }}>
        Essas informações nos ajudam a preparar estratégias alinhadas à sua realidade e com foco no seu horizonte de tempo antes da nossa primeira reunião de assessoria.
      </p>
      <form onSubmit={enviar}>
        <div className="form-grid">
          <SelectField className="span-2" label="Qual o seu principal objetivo com os investimentos?" options={OBJETIVOS} value={investimento.objetivo} onChange={set('objetivo')} />
          <SelectField label="Volume estimado para assessorar" options={VOLUMES} value={investimento.volume} onChange={set('volume')} />
          <SelectField label="Como você investe atualmente?" options={PERFIS} value={investimento.perfil} onChange={set('perfil')} />
          <div className="span-2"><SubmitButton loading={enviando}>Concluir cadastro e solicitar contato</SubmitButton></div>
        </div>
        {erro && <p role="alert" className="auth__alert">{erro}</p>}
        <p className="auth__note">
          Ao clicar em concluir, você concorda com nossos termos de privacidade. Enviaremos uma confirmação no seu e-mail cadastrado e um assessor exclusivo entrará em contato em até 24 horas úteis.
        </p>
      </form>
    </>
  )
}
