[![Java CI with Maven](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml/badge.svg)](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml)

## Steps to launch project:

1) #### Start postgres in docker

```console
docker run \
--name postgres \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=test \
-p 5432:5432 \
-d postgres
```

1) #### Start postgres in docker

```console
docker-compose up -d
```

You can check functionality use swagger (http://localhost:8888/swagger-ui/index.html#/)
or make curl requests

## Curl requests:
### 1) Create new user

```console
curl -X 'POST' \
  'http://localhost:8888/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "login": 'PUT YOUR USER NAME HERE',
  "password": 'PUT YPUR PASSWORD HERE'
}'
```
### 2) Authorization 
##### in response, you get token, that you need to use in send message and history requests
```console
curl -X 'POST' \
  'http://localhost:8888/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "login": 'NAME THAT YOU SET IN `CREATE USER` STEP',
  "password": 'PASSWORD THAT YOU SET IN `CREATE USER` STEP'
}'
```
### 3) Send message
##### you do not need to set your name in curl request because your name already include to the token from authorization step.


```console
curl -X 'POST' \
  'http://localhost:8888/api/message/send' \
  -H 'accept: */*' \
  -H 'Authorization: YOUR TOKEN FROM AUTHORIZATION' \
  -H 'Content-Type: application/json' \
  -d 'YOUR MESSAGE'
```
### 4) History request
##### you do not need to set your name in curl request because your name already include to the token from authorization step.
```console
curl -X 'GET' \
  'http://localhost:8888/api/message/history?limitOfMessage='AMOUNT OF MESSAGE' \
  -H 'accept: */*' \
  -H 'Authorization: YOUR_TOKEN_FROM_AUTHORIZATION'
```


