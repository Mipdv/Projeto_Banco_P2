import { useState } from 'react'
import { Field } from '../../components/auth/Field'
import SubmitButton from '../../components/auth/SubmitButton'
import { maskCpf, maskCelular, cpfValido, emailValido, celularValido } from '../../utils/formatters'

function validar(d) {
  const e = {}
  if (d.nome.trim().split(/\s+/).length < 2) e.nome = 'Informe nome e sobrenome.'
  if (!cpfValido(d.cpf)) e.cpf = 'Informe um CPF válido.'
  if (!emailValido(d.email)) e.email = 'Informe um e-mail válido.'
  if (!celularValido(d.celular)) e.celular = 'Informe DDD e número.'
  if (!d.dataNascimento) e.dataNascimento = 'Informe sua data de nascimento.'
  if (d.senha.length < 6) e.senha = 'A senha deve ter pelo menos 6 caracteres.'
  return e
}

export default function StepDados({ dados, setDados, onNext }) {
  const [erros, setErros] = useState({})
  const set = (campo, mask = (v) => v) => (e) => setDados({ ...dados, [campo]: mask(e.target.value) })

  function enviar(e) {
    e.preventDefault()
    const novos = validar(dados)
    setErros(novos)
    if (!Object.keys(novos).length) onNext()
  }

  return (
    <>
      <p className="auth__eyebrow">Cadastro Mister · 01 de 03</p>
      <h1>Seu novo banco de investimentos: abra sua conta</h1>
      <p className="auth__lead">
        Comece a investir com os especialistas do Mister, <strong>aproveite benefícios exclusivos e um portfólio completo.</strong>
      </p>
      <form onSubmit={enviar} noValidate>
        <div className="form-grid">
          <Field label="Nome completo" autoComplete="name" placeholder="Como podemos chamar você?" value={dados.nome} error={erros.nome} onChange={set('nome')} />
          <Field label="CPF" inputMode="numeric" placeholder="123.456.789-10" value={dados.cpf} error={erros.cpf} onChange={set('cpf', maskCpf)} />
          <Field label="E-mail" type="email" autoComplete="email" placeholder="voce@email.com" value={dados.email} error={erros.email} onChange={set('email')} />
          <Field label="Celular" type="tel" autoComplete="tel" placeholder="(11) 99999-0000" value={dados.celular} error={erros.celular} onChange={set('celular', maskCelular)} />
          <Field label="Data de nascimento" type="date" autoComplete="bday" value={dados.dataNascimento} error={erros.dataNascimento} onChange={set('dataNascimento')} />
          <Field label="Senha" type="password" autoComplete="new-password" placeholder="Crie uma senha" value={dados.senha} error={erros.senha} onChange={set('senha')} />
          <div className="span-2"><SubmitButton>Continuar</SubmitButton></div>
        </div>
      </form>
    </>
  )
}
