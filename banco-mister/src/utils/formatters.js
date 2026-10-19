export const somenteDigitos = (v = '') => v.replace(/\D/g, '')

export function maskCpf(v) {
  const d = somenteDigitos(v).slice(0, 11)
  return d
    .replace(/^(\d{3})(\d)/, '$1.$2')
    .replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3')
    .replace(/\.(\d{3})(\d)/, '.$1-$2')
}

export function maskCelular(v) {
  const d = somenteDigitos(v).slice(0, 11)
  if (d.length <= 2) return d
  if (d.length <= 6) return `(${d.slice(0, 2)}) ${d.slice(2)}`
  if (d.length <= 10) return `(${d.slice(0, 2)}) ${d.slice(2, 6)}-${d.slice(6)}`
  return `(${d.slice(0, 2)}) ${d.slice(2, 7)}-${d.slice(7)}`
}

export function cpfValido(valor) {
  const c = somenteDigitos(valor)
  if (c.length !== 11 || /^(\d)\1+$/.test(c)) return false
  const dv = (len) => {
    let soma = 0
    for (let i = 0; i < len; i++) soma += Number(c[i]) * (len + 1 - i)
    const r = (soma * 10) % 11
    return r === 10 ? 0 : r
  }
  return dv(9) === Number(c[9]) && dv(10) === Number(c[10])
}

export const emailValido = (v) => /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/.test(v)
export const celularValido = (v) => [10, 11].includes(somenteDigitos(v).length)
