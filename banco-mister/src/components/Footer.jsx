import Logo from './Logo'

export default function Footer() {
  return (
    <footer className="footer">
      <div className="container">
        <div className="footer__top">
          <div>
            <Logo light />
            <p>Private banking com mais proximidade, clareza e tempo para o que realmente importa.</p>
          </div>
          <nav className="footer__nav" aria-label="Rodapé">
            <a href="#banco">O banco</a>
            <a href="#patrimonio">Patrimônio</a>
            <a href="#conteudo">Caderno</a>
            <a href="#contato">Contato</a>
          </nav>
        </div>
        <div className="footer__bottom">
          <span>© 2026 Mister Banco Privado</span>
          <a href="#">Termos e privacidade</a>
        </div>
      </div>
    </footer>
  )
}
