[![Java CI with Maven](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml/badge.svg)](https://github.com/fmgolos/TestTask/actions/workflows/maven.yml)

## Steps to launch project:
1) #### Start postgres in docker
```console
docker run \
--name postgres \
-e POSTGRES_PASSWORD=test \
-p 5432:5432 \
-d postgres
```
2) #### Pull project from https://hub.docker.com/
```console
docker pull  TODO
```
3) #### Start project container
```console
docker start project TODO
```

You can check functionality use swagger (http://localhost:8888/swagger-ui/index.html#/)
or make curl requests


## Curl requests:
### create new user
```console
curl -X 'POST' \
  'http://localhost:8888/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "login": "PUT YOUR USER NAME HERE",
  "password": "PUT YPUR PASSWORD HERE"
}'
```
### authorization (in response you get token, that you need to use in history requests)

```console
curl -X 'POST' \
  'http://localhost:8888/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "login": "NAME THAT YOU SET IN `CREATE USER` STEP",
  "password": "PASSWORD THAT YOU SET IN `CREATE USER` STEP"
}'
```
### send message


```console
curl -X 'POST' \
  'http://localhost:8888/send' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "NAME THAT YOU SET IN `CREATE USER` STEP",
  "message": "YOUR TEXT HERE"
}'
```
### history request
```console
TODO
```


