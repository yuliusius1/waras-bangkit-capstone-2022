FROM node:16
RUN apt-get update -y \
    && apt-get clean
WORKDIR /usr/src/app
COPY package.json /usr/src/app
RUN npm install 
COPY . /usr/src/app
ENV port=8080
EXPOSE 8080
CMD ["npm", "start"]