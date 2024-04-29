# JWT Springboot for Flip.id Test
This project for Flip.id test only.pleae install mySQL and restore the table first.
Make sure your IDE lombok support.

## Usage

```python
run project: mvn spring-boot:run
or 
java -jar target/iwan-test-0.0.1-SNAPSHOT.jar
```

## Endpoints
```python
POST /authenticate

curl --location 'http://localhost:9999/authenticate' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=561DB700056684814CA3380CC0AE54C2' \
--data '{
    "username":"[username]",
    "password":"password"
}'

Response:
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpd2FuLXRlc3QiLCJleHAiOjE3MTAzNjQyNTAsImlhdCI6MTcxMDM0NjI1MH0.aOFWig5FbcE2LhcpbgZFuICjUNsW6eDmOXr6z9iLJgAdSQtg5luEEG7005mcq5wQ1-Onn77WScJw-UD18c065g"
}

```

```python
GET /v1/balance_read

curl --location 'http://localhost:9999/v1/balance_read' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWZhIiwiZXhwIjoxNzE0NDQwODIxLCJpYXQiOjE3MTQzNTQ0MjF9.LT2s-JQOOxvaJNPFP7QY_DWGE5HLxVIA95cfXpcVX6SMRMLWXSM883ZrkiuGni_lVLBJzhrTjvgcTr1cvyfAkg'

Response:
{
    "timestamp": 1714354609017,
    "status": 200,
    "message": "Balance read success",
    "data": [
        {
            "balance": 0
        }
    ]
}
```


```python
POST /v1/create_user

curl --location 'http://localhost:9999/v1/create_user' \
--header 'Content-Type: application/json' \
--data '{
    "name":"iwan abdur",
    "username":"fafa",
    "phone":"081902512555"
}'
Response:
{
    "timestamp": 1714354420771,
    "status": 200,
    "message": "OK",
    "data": [
        {
            "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWZhIiwiZXhwIjoxNzE0NDQwODIxLCJpYXQiOjE3MTQzNTQ0MjF9.LT2s-JQOOxvaJNPFP7QY_DWGE5HLxVIA95cfXpcVX6SMRMLWXSM883ZrkiuGni_lVLBJzhrTjvgcTr1cvyfAkg"
        }
    ]
}
```

```python
POST /v1/transfer

curl --location 'http://localhost:9999/v1/transfer' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3b254IiwiZXhwIjoxNzE0Mzc1OTE5LCJpYXQiOjE3MTQzNTc5MTl9.COF6eaKxf7aUNBE4OTMUeDsnCLYClOaRfqYFVMvFxiPUHP1PvcTtfojdnC8kf5scyciiwT3bBkLBiwODWz47kw' \
--header 'Content-Type: application/json' \
--data '{
    "to_username":"fafa",
    "amount":45000
}'

Response:
{
    "timestamp": 1714359955530,
    "status": 204,
    "message": "Transfer success"
}
```


```python
POST /v1/balance_topup

curl --location 'http://localhost:9999/v1/balance_topup' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3b254IiwiZXhwIjoxNzE0Mzc1OTE5LCJpYXQiOjE3MTQzNTc5MTl9.COF6eaKxf7aUNBE4OTMUeDsnCLYClOaRfqYFVMvFxiPUHP1PvcTtfojdnC8kf5scyciiwT3bBkLBiwODWz47kw' \
--header 'Content-Type: application/json' \
--data '{
    "amount":560000
}'

Response:
{
    "timestamp": 1714359853194,
    "status": 204,
    "message": "Topup successful"
}
```
```python
GET /v1/top_users

curl --location 'http://localhost:9999/v1/top_users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWZhIiwiZXhwIjoxNzE0Mzc4MDAzLCJpYXQiOjE3MTQzNjAwMDN9.9a0_AmKRgLj5j5pU9xkiqebbXT8f018OJJBlI6B1Yji4XYHPMXbJgoUr7_fQhhiONqlqJr_GiJgdH1pOf2FS6w'

Response:
{
    "timestamp": 1714361296365,
    "status": 200,
    "message": "OK",
    "data": [
        {
            "username": "wonx",
            "transacted_value": 260000
        },
        {
            "username": "fafa",
            "transacted_value": 97000
        }
    ]
}

```

```python
GET /v1/top_transactions_per_user

curl --location 'http://localhost:9999/v1/top_transactions_per_user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWZhIiwiZXhwIjoxNzE0Mzc4MDAzLCJpYXQiOjE3MTQzNjAwMDN9.9a0_AmKRgLj5j5pU9xkiqebbXT8f018OJJBlI6B1Yji4XYHPMXbJgoUr7_fQhhiONqlqJr_GiJgdH1pOf2FS6w'

Response:
{
    "timestamp": 1714361914587,
    "status": 200,
    "message": "OK",
    "data": [
        {
            "username": "fafa",
            "amount": 720000
        },
        {
            "username": "wonx",
            "amount": 560000
        },
        {
            "username": "wonx",
            "amount": 245000
        },
        {
            "username": "fafa",
            "amount": 245000
        },
        {
            "username": "fafa",
            "amount": 97000
        },
        {
            "username": "andi",
            "amount": 97000
        },
        {
            "username": "wonx",
            "amount": 15000
        },
        {
            "username": "budi",
            "amount": 15000
        }
    ]
}

```
