FROM node:4.6
WORKDIR /app
ADD . /app
RUN echo " this is a test "

EXPOSE 3000
CMD npm start
