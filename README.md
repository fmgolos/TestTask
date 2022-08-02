[![Java CI with Maven](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml/badge.svg)](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml)

## Steps to launch project:

### 1)  Need to create or download  **_`docker-compose.yml`_** from git repo and put it in project root directory.

##### ↓ docker-compose.yml content ↓

```
version: "2"

services:
  postgresql:
    image: "postgres:alpine3.16"
    network_mode: host
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=test
  application:
    image: "fmgolos/test-task:application"
    network_mode: host
```

### 2) Execute command

```console
docker-compose up -d
```

As a result you will get 2 started containers. One of them - postgres, other - application

Service will be started automatically on http://localhost:8888/.

To see logs - execute command ↓

```console
docker logs -f --tail 200 testtask_application_1
```

You can check functionality use swagger (http://localhost:8888/swagger-ui/index.html#/)
or make curl requests

------------------
> * At first need to create user, because database has foreign key, and next steps are impossible
> * Authorize use login and password, that you set in
>* If you use swagger for check app functionality - make sure that you set up token from "login" to special
   > field that name "Authorize"
-------------------

## Curl requests:

### 1) Create new user

```console
curl -X 'POST' \
  'http://localhost:8888/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "login": "NewUser",
  "password": "password"
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
  "login": "NewUser",
  "password": "password"
}'
```
### 3) Send message

##### Not needed to set your name in curl request because your name already included in the JWT token from authorization step.

```console
  curl -X 'POST' \
  'http://localhost:8888/api/message/send' \
  -H 'accept: */*' \
  -H 'Authorization: YOUR TOKEN FROM AUTHORIZATION' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "Test message"
}'
```
### 4) History request

##### Not needed to set your name in curl request because your name already included in the JWT token from authorization step.
```console
curl -X 'GET' \
  'http://localhost:8888/api/message/history?limit=10' \
  -H 'accept: */*' \
  -H 'Authorization: YOUR_TOKEN_FROM_AUTHORIZATION'
```

