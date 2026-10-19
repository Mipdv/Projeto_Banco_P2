import { Check } from '../icons'

const PASSOS = ['Seus dados', 'Investimento', 'Confirmação']

export default function Stepper({ atual }) {
  return (
    <ol className="stepper" aria-label="Etapas do cadastro">
      {PASSOS.map((nome, i) => {
        const n = i + 1
        const estado = n < atual ? 'done' : n === atual ? 'active' : 'todo'
        return (
          <li key={nome} className={`stepper__item stepper__item--${estado}`} aria-current={estado === 'active' ? 'step' : undefined}>
            <span className="stepper__box">{estado === 'done' ? <Check /> : String(n).padStart(2, '0')}</span>
            <span>{nome}</span>
          </li>
        )
      })}
    </ol>
  )
}
