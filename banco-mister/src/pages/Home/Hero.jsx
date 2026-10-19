import { Link } from 'react-router-dom'

export default function Hero() {
  return (
    <section className="hero">
      <div className="container hero__grid">
        <div className="hero__text">
          <p className="eyebrow eyebrow--light">Private banking brasileiro</p>
          <h1>
            O seu dinheiro, <br />
            <em>com nome</em> e <br />
            sobrenome.
          </h1>
          <p className="hero__lead">
            Uma casa financeira para quem prefere conversar antes de decidir. Estratégia, liquidez e presença em cada movimento.
          </p>
          <div className="hero__cta">
            <Link to="/cadastro" className="btn btn--gold">Começar uma conversa <span aria-hidden="true">›</span></Link>
            <a href="#banco" className="hero__more"><span className="line" /> Conheça o Mister</a>
          </div>
        </div>

        <div className="hero__visual" aria-hidden="true">
          <div className="account-card">
            <div className="account-card__head">
              <div>
                <span className="eyebrow eyebrow--light">Conta Mister</span>
                <p>Patrimônio acompanhado</p>
              </div>
              <span className="tag">Privado</span>
            </div>
            <p className="account-card__label">Saldo disponível</p>
            <p className="account-card__value">R$ 248.730,20</p>
            <svg viewBox="0 0 300 70" preserveAspectRatio="none" className="account-card__chart">
              <defs>
                <linearGradient id="g" x1="0" x2="0" y1="0" y2="1">
                  <stop offset="0" stopColor="#C9A86A" stopOpacity=".35" />
                  <stop offset="1" stopColor="#C9A86A" stopOpacity="0" />
                </linearGradient>
              </defs>
              <path d="M0 55 C30 50 40 60 70 48 S110 40 140 44 S190 20 220 28 S270 10 300 6 V70 H0Z" fill="url(#g)" />
              <path d="M0 55 C30 50 40 60 70 48 S110 40 140 44 S190 20 220 28 S270 10 300 6" fill="none" stroke="#C9A86A" strokeWidth="1.2" />
            </svg>
            <div className="account-card__foot"><span>Jan 24</span><span>Hoje</span><span>+12,8%</span></div>
          </div>
          <div className="follow-card">
            <span className="eyebrow eyebrow--gold">Acompanhamento</span>
            <p className="follow-card__big">1:1</p>
            <p>Uma pessoa que conhece a sua história.</p>
          </div>
        </div>
      </div>
      <div className="container hero__scroll"><span>Scroll para explorar</span><span className="line" /></div>
    </section>
  )
}
