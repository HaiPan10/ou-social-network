# FROM node:alpine
# WORKDIR /app
# COPY package.json ./
# COPY package-lock.json ./
# RUN npm install --legacy-peer-deps
# COPY ./ ./
# CMD ["npm", "start"]

FROM node:alpine AS development
ENV NODE_ENV development
WORKDIR /react-app
COPY ./package.json /react-app
RUN npm install --legacy-peer-deps
COPY . .
CMD npm start