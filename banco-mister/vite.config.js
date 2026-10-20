import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Em dev, o proxy encaminha /api para o back-end Java (evita CORS localmente).
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    // O back-end não usa o prefixo /api, então ele é removido antes de encaminhar.
    proxy: { '/api': { target: 'http://localhost:8080', changeOrigin: true, rewrite: (path) => path.replace(/^\/api/, '') } },
  },
})
