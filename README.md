[![Java CI with Maven](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml/badge.svg)](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml)

## Steps to launch project:

### 1)  Clone repository

### 2) Execute command from project root directory

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
> * At first need to create user, because database has is empty, and next steps are impossible
> * Authenticate, use login and password, that you set in 'create user' endpoint
>* If you use swagger for check app functionality - make sure that you set up token from "login" to special
   > field that name "Authorize"
-------------------

## Curl requests:

### 1) Create new user

```console
curl -X 'POST' \
  -v \
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

##### No need to set your name in curl request because your name already included in the JWT token from authorization step.

```console
  curl -X 'POST' \
  -v \
  'http://localhost:8888/api/message/send' \
  -H 'accept: */*' \
  -H 'Authorization: YOUR TOKEN FROM AUTHORIZATION' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "Test message"
}'
```
### 4) History request

##### No need to set your name in curl request because your name already included in the JWT token from authorization step.
```console
curl -X 'GET' \
  'http://localhost:8888/api/message/history?limit=10' \
  -H 'accept: */*' \
  -H 'Authorization: YOUR_TOKEN_FROM_AUTHORIZATION'
```

