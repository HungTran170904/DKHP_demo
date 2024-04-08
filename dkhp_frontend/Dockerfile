# build stage
FROM node:16-alpine as build-stage
COPY . .
RUN npm install
RUN npm run build
#production stage
FROM nginx:alpine as production-stage
COPY --from=build-stage /nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage ./build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]