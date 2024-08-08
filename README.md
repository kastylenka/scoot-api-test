# ScootApi Test

## Functionality
There is one endpoint that consumes start and end points as coordinates with latitude and longitude, 
and responds with distance between them in kilometers as floating number with up to 4 decimals.

POST http://{host}/scoot/api/calculations/distance 

Request body:
```json
{
  "startPoint": {
    "latitude": 10.10,
    "longitude": 10.10
  },
  "endPoint": {
    "latitude": 20.10,
    "longitude": 20.10
  }
}
```

Response body:
```json
{
  "distance": 1541.5110
}
```

## Rate limiting
Rate limiting is developed in two ways.
In case of exceeding the number (10r/m) - 429 status code is returned.
### Application level in memory rate limiting mechanism
The first one is a workaround with Resilience4j rate limiting feature.
It's im memory mechanism, that creates RateLimiter object with specific name based on api-key. 
In current state it stores rate limiting states in memory, and is not possible to share between multiple instances.
Possible improvement - store states in database or shared cache (e.g. Redis). 

Run as a standalone application with application.yaml property aspect.rate-limited.enabled: true

### Nginx proxy level rate limiting mechanism
Second mechanism is implemented using nginx proxy.
First of all it separates rate limiting functionality from application.
Second - is that we can configure it under multiple instances of application, because rate limiting happens before traffic balancing.

Run in docker (from project root directory):
- docker-compose build
- docker-compose up -d
