export default function Sobre() {
  return (
    <section id="banco" className="section section--cream">
      <div className="container sobre">
        <div>
          <p className="eyebrow">Uma casa, não uma plataforma</p>
          <h2>Clareza para cuidar do que levou tempo para construir.</h2>
          <p className="muted">
            O Mister nasce para tornar decisões importantes mais simples. Não oferecemos prateleiras de produtos; oferecemos uma visão inteira do seu patrimônio.
          </p>
          <a href="#experiencia" className="link-arrow">Conheça nosso jeito <span aria-hidden="true">↗</span></a>
        </div>
        <div className="sobre__right">
          <div className="sobre__cols">
            <article>
              <span className="num">01</span>
              <h3>Uma pessoa do seu lado</h3>
              <p className="muted">Seu contexto não cabe em um algoritmo. Seu especialista acompanha cada etapa e traduz o complexo sem atalhos.</p>
            </article>
            <article>
              <span className="num">02</span>
              <h3>Decisões com horizonte</h3>
              <p className="muted">Liquidez hoje e serenidade amanhã: construímos uma arquitetura financeira que respeita seus ciclos.</p>
            </article>
          </div>
          <figure className="quote">
            <figcaption className="eyebrow">Nosso compromisso</figcaption>
            <blockquote>“A melhor recomendação é aquela que você entende — e reconhece como sua.”</blockquote>
            <cite>Carta Mister · 2024</cite>
          </figure>
        </div>
      </div>
    </section>
  )
}
