FROM node:24-alpine AS builder

WORKDIR /app

COPY index.html index.html
COPY package.json package.json
COPY package-lock.json package-lock.json
COPY tsconfig.json tsconfig.json
COPY tsconfig.node.json tsconfig.node.json
COPY vite.config.ts vite.config.ts

COPY src /app/src/
COPY public /app/public/

RUN npm install
RUN npm run build

RUN tree

FROM httpd:latest AS runner

COPY --from=builder /app/dist /usr/local/apache2/htdocs

EXPOSE 80
