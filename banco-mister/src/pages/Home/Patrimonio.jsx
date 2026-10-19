const servicos = ['Planejamento patrimonial', 'Carteiras sob medida', 'Sucessão e legado']

export default function Patrimonio() {
  return (
    <section id="patrimonio" className="section section--sand">
      <div className="container patrimonio">
        <div className="arch">
          <div className="arch__top"><span>Arquitetura patrimonial</span><span>M / 02</span></div>
          <h3>Patrimônio não é uma soma. É um desenho.</h3>
          <p className="arch__sub"><span className="line" /> Renda fixa · fundos · câmbio · sucessão</p>
          <div className="arch__labels"><span>Preservar</span><span>Expandir</span><span>Legar</span></div>
        </div>
        <div id="experiencia">
          <p className="eyebrow">Investimentos com contexto</p>
          <h2>O mercado muda. A sua bússola permanece.</h2>
          <p className="muted">
            Acesso às melhores oportunidades do Brasil e do mundo, organizado em uma estratégia que faz sentido para o seu momento — não para a manchete do dia.
          </p>
          <ul className="list">
            {servicos.map((s) => (
              <li key={s}><a href="#contato">{s}<span aria-hidden="true">›</span></a></li>
            ))}
          </ul>
        </div>
      </div>
    </section>
  )
}
