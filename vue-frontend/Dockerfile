FROM node:24-alpine AS builder

WORKDIR /app

#COPY env.d.ts env.d.ts
#COPY eslint.config.ts eslint.config.ts
#COPY index.html index.html
#COPY package.json package.json
#COPY package-lock.json package-lock.json
#COPY tsconfig.app.json tsconfig.app.json
#COPY tsconfig.json tsconfig.json
#COPY tsconfig.node.json tsconfig.node.json
#COPY vite.config.ts vite.config.ts
#
#COPY src /app/src/
#COPY public /app/public/

copy . .

RUN npm install
RUN npm run build

RUN tree

FROM httpd:latest AS runner

COPY --from=builder /app/dist /usr/local/apache2/htdocs

EXPOSE 80
