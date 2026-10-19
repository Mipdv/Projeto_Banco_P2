import { Link } from 'react-router-dom'
import Logo from './Logo'

const links = [
  { label: 'O banco', href: '#banco' },
  { label: 'Patrimônio', href: '#patrimonio' },
  { label: 'Experiência', href: '#experiencia' },
  { label: 'Conteúdo', href: '#conteudo' },
]

export default function Header() {
  return (
    <>
      <div className="topbar">
        Atendimento pessoal para o que importa. <a href="#contato">Fale com um especialista</a>
      </div>
      <header className="header">
        <div className="container header__inner">
          <Link to="/" aria-label="Mister, página inicial"><Logo /></Link>
          <nav className="header__nav" aria-label="Principal">
            {links.map((l) => <a key={l.href} href={l.href}>{l.label}</a>)}
          </nav>
          <div className="header__actions">
          <Link to="/cadastro" className="header__link">Abra sua conta</Link>
            <Link to="/login" className="btn btn--outline btn--sm">Acessar conta</Link>
          </div>
        </div>
      </header>
    </>
  )
}
