import { useId } from 'react'

export function Field({ label, error, right, className = '', ...input }) {
  const id = useId()
  return (
    <div className={`field ${error ? 'field--error' : ''} ${className}`}>
      <label htmlFor={id} className="field__label">{label}</label>
      <div className="field__control">
        <input id={id} aria-invalid={Boolean(error)} aria-describedby={error ? `${id}-erro` : undefined} {...input} />
        {right}
      </div>
      {error && <p id={`${id}-erro`} className="field__error" role="alert">{error}</p>}
    </div>
  )
}

export function SelectField({ label, options, className = '', ...select }) {
  const id = useId()
  return (
    <div className={`field ${className}`}>
      <label htmlFor={id} className="field__label">{label}</label>
      <div className="field__control">
        <select id={id} {...select}>
          {options.map((o) => <option key={o} value={o}>{o}</option>)}
        </select>
      </div>
    </div>
  )
}
