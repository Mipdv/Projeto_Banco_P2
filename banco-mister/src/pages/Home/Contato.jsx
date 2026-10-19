import { Link } from 'react-router-dom'

export default function Contato() {
  return (
    <section id="contato" className="section section--sage">
      <div className="container contato">
        <div>
          <p className="eyebrow">O próximo capítulo começa aqui</p>
          <h2>Seu futuro financeiro começa com uma boa escolha.</h2>
        </div>
        <div>
          <p className="muted">Abra sua conta Mister e tenha um novo jeito de cuidar do seu dinheiro, com soluções pensadas para os seus planos.</p>
          <Link to="/cadastro" className="btn btn--navy">Abra sua conta <span aria-hidden="true">↗</span></Link>
        </div>
      </div>
    </section>
  )
}