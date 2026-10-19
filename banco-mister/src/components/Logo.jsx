import escuro from '../assets/monograma.png'
import claro from '../assets/monograma-claro.png'
import dourado from '../assets/monograma-dourado.png'

// tone: 'dark' (fundos claros) | 'light' (branca, rodapé) | 'gold' (painéis azuis das telas de acesso)
export default function Logo({ tone = 'dark', light = false, showText = true, size = 30 }) {
  const t = light ? 'light' : tone
  const src = { dark: escuro, light: claro, gold: dourado }[t]
  return (
    <span className={`logo logo--${t}`}>
      <img src={src} alt="" className="logo__img" style={{ height: size }} />
      {showText && <span className="logo__text">Mister</span>}
    </span>
  )
}
