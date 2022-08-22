# Fibonacci API
    It's a sample of service with a exposed controller to calculate Fibonacci Sequence.
    It was used Spring Webflux to keep the application more elastic and hold high throughput. 
    Minekube was choosed as tool to deploy this application and Nginx as tool of ingress.

## Requirements
    Java: JDK 17
    Gradle
    Docker
    Kubernetes: Minikube on local

## First setup

#### Build Fibonacci Api
```
$ cd fibonacci
$ ./gradlew build
```

#### Create docker images for Fibonacci
```
$ cd fibonacci
$ docker build -t fib-api:latest .
```

#### Start Minikube
```
$ minikube start
```

#### Create service and deployment
```
$ cd k8s
$ kubectl apply -f fib.yml
```

#### Check service
```
$ minikube service fib-api --url
```
Give me url `http://{url-returned}:{port-returned}` and Check rest-api http://{url-returned}:{port-returned}/fib?n=90000

#### Accessing API documentation
* URL: `http://{url-returned}:{port-returned}/swagger-ui/`


## Setup Ingress
Create Ingress with some basic security against DDos atack

### Create Ingress
```
$ cd k8s
$ kubectl apply -f ingress.yml
```

#### Check Ingress
```
$ kubectl describe ing
```

#### Enable Ingress
```
$ minikube addons enable ingress
```

```
$ minikube ip
Example of return: 192.168.59.100
```

#### Add fibonacci.com into /etc/hosts
```
{minikube-ip-returned} fibonacci.com
```
Check browser: `http://fibonacci.com/fib?n=10`