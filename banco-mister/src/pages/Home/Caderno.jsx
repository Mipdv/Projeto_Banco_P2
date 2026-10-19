// Quando o back-end tiver o endpoint de artigos, troque este array por uma chamada em src/api/artigosApi.js.
const artigos = [
  { id: 1, categoria: 'Perspectiva', titulo: 'Renda fixa em um mundo de mudanças', resumo: 'O que observar além da taxa.' },
  { id: 2, categoria: 'Patrimônio', titulo: 'Legado é o que continua quando você não está', resumo: 'Uma conversa que vale começar cedo.' },
]

export default function Caderno() {
  return (
    <section id="conteudo" className="section section--paper">
      <div className="container">
        <div className="caderno__head">
          <div>
            <p className="eyebrow">Caderno Mister</p>
            <h2>Pensar também é investir.</h2>
          </div>
          <a href="#conteudo" className="link-arrow">Ver todos os artigos <span aria-hidden="true">↗</span></a>
        </div>
        <div className="caderno__grid">
          <article className="destaque">
            <div className="destaque__top"><span>Leitura · 06 min</span><span>01 / 03</span></div>
            <div>
              <h3>A tranquilidade de ter um plano antes de ter uma resposta.</h3>
              <p>Por que boas decisões financeiras começam com perguntas mais honestas.</p>
            </div>
          </article>
          <div className="caderno__list">
            {artigos.map((a) => (
              <article key={a.id}>
                <span className="eyebrow">{a.categoria}</span>
                <h4>{a.titulo}</h4>
                <p className="muted">{a.resumo}</p>
              </article>
            ))}
          </div>
        </div>
      </div>
    </section>
  )
}
