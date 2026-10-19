import { Link } from 'react-router-dom'
import Logo from '../Logo'
import { ArrowLeft } from '../icons'
import './auth.css'

/**
 * Layout dividido das telas de acesso (login e cadastro).
 * aside: { topRight, eyebrow, title, text, bottom }  -> painel azul
 * back:  { to | onClick, label }                      -> link de voltar
 */
export default function AuthLayout({ aside, back, topRight, footer, width = 'md', children }) {
  return (
    <div className="auth">
      <aside className="auth__aside">
        <span className="auth__circle auth__circle--lg" aria-hidden="true" />
        <span className="auth__circle auth__circle--sm" aria-hidden="true" />
        <div className="auth__aside-top">
          <Link to="/" aria-label="Mister, página inicial"><Logo tone="gold" size={28} /></Link>
          {aside.topRight}
        </div>
        <div className="auth__aside-main">
          <p className="auth__eyebrow auth__eyebrow--gold">{aside.eyebrow}</p>
          <h2>{aside.title}</h2>
          <p className="auth__aside-text">{aside.text}</p>
        </div>
        <div className="auth__aside-bottom">{aside.bottom}</div>
      </aside>

      <section className="auth__main">
        <div className="auth__main-top">
          {back.to ? (
            <Link to={back.to} className="auth__back"><ArrowLeft /> {back.label}</Link>
          ) : (
            <button type="button" className="auth__back" onClick={back.onClick}><ArrowLeft /> {back.label}</button>
          )}
          {topRight}
        </div>
        <div className={`auth__content auth__content--${width}`}>{children}</div>
        <div className="auth__footer">{footer}</div>
      </section>
    </div>
  )
}
