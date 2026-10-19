import { ArrowRight } from '../icons'

export default function SubmitButton({ children, loading = false, as: Tag = 'button', ...props }) {
  return (
    <Tag className="auth-btn" disabled={loading} {...props}>
      <span>{loading ? 'Enviando…' : children}</span>
      <ArrowRight />
    </Tag>
  )
}
