# Banco Mister (front-end)

React + Vite. Requer Node 18+.

```bash
npm install
cp .env.example .env
npm run dev        # http://localhost:5173
```

## Integração com o back-end Java
- `src/api/httpClient.js`: único ponto de acesso ao back-end (baseURL, JWT, tratamento de 401).
- `src/api/*Api.js`: um arquivo por recurso (auth, contatos...). Ajuste caminhos e JSON conforme o contrato com o colega.
- Em dev, o `vite.config.js` encaminha `/api` para `http://localhost:8080` (sem CORS). Em produção, defina `VITE_API_URL` e libere a origem do front (CORS) no Spring.

## Nova página
1. Crie `src/pages/NomeDaPagina/NomeDaPagina.jsx`.
2. Registre a rota em `src/routes/index.jsx` (dentro de `PrivateRoute` se exigir login).
3. Se precisar de dados, crie `src/api/recursoApi.js`.
