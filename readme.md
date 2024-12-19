# Spring Boot + Mongo DB Application

This repository is part of a Containerization assesment with a spring boot backend implemntation of a REST API fetching data from Mongo DB. 

The Spring boot application is running a single GET books endpoint with the route "/books" that gets a list of books. 
The mongo database has a books db that is called by the endpoint to list all books.

## Project Setup

This section will give a guide on how to setup the application and have it running on your local instance using Docker 
and deploying it on Minikube. Additionally, it provides details about the CI/CD pipeline implemented in GitHub Actions

### Instructions to run the Application Locally Using Docker
#### Prerequisites
* Install [Docker](https://docs.docker.com/engine/install/ubuntu/) on your local machine.
* Clone the project repository from GitHub

#### Setup Instructions 
After successful cloning navigate to the project root directory and **build the docker image**.

Start the Application Using Docker Compose:
Run the following command to build and start the application using docker-compose.yml:
```bash  
docker-compose up --build
```
This will:

* Build the Spring Boot application image using the Dockerfile.
* Start the Spring Boot application and MongoDB containers.

Access the Application:Open a browser and visit:
```bash 
http://localhost:8080/books
or 
curl http://localhost:8080/books
```
The expected response is a blank array 

To simulate a response with actual sample data ,add Sample Data to MongoDB:
* Access the MongoDB terminal :
```bash
docker exec -it mongodb mongosh
```
* Switch to the database and insert sample data:
```bash
use books;
db.books.insertMany([
  { title: "Book One", author: "Author One", publishedYear: 2020 },
  { title: "Book Two", author: "Author Two", publishedYear: 2021 }
]);
```
* Make an endpoint call either via postman or directly via the browser
```bash 
http://localhost:8080/books
or 
curl http://localhost:8080/books
```

### Instructions to Deploy on Minikube

#### Prerequisites

* Install [Kubernetes](https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/)
* Install [Minikube](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download)

#### Minikube Setup 

* Start Minikube:
```bash
minikube start
```

#### Minikube Deployment Steps
The minikube configuration files are separated at an application level with the deployment and service configuration of the 
springboot backend file in the springboot.yaml and the mongodb deployment and service configuration in the mongo.yaml

* Apply MongoDB Configuration: Deploy MongoDB using the provided mongo.yaml file:
```bash
kubectl apply -f mongo.yaml 
```
* Apply Spring Boot Backend Configuration: Deploy the backend using the springboot.yaml file:
```bash
kubectl apply -f springboot.yaml 
```

* Verify the deployment is running 
```bash
kubectl get pods 
```

* To access the applications on localhost port-fowarding was implemented to ensure that the application is reachable
  * Springboot Port-fowarding
```bash
kubectl port-forward service/springboot-backend-service 8080:8080 
```
  * Mongo DB Port Fowarding
```bash
  kubectl port-forward service/mongodb-service 27017:27017 
  ```
* Once MongoDB has been port fowarded you can access it's terminal using this command 
```bash
mongo --host localhost --port 27017 
or 
mongosh --host localhost --port 27017
```
* Once logged in the command for sample data provided above can be used to simulate data


## CI/CD Pipeline in GitHub Actions

The CI/CD pipeline automates the building and deployment of the Docker image. It performs the following tasks:

1. Build the Docker Image:Uses the GitHub Actions docker/build-push-action to create the Docker image.
2. Push to Docker Registry:Automatically pushes the image to Docker Hub or GitHub Container Registry.
3. Status Notification:Sends success or failure notifications for each step. (To be implemented in next version)

### Workflow File

The main.yml file in the .github/workflows directory defines the CI/CD pipeline.

### Key Workflow Steps
1. Trigger on Push/PR: Automatically runs on push or pull request to the main branch.
2. Login to Docker Hub: Uses secrets to authenticate with Docker Hub.
3. Build and Push Image:
```bash
 - name: Build and push Docker image
  uses: docker/build-push-action@v4
  with:
    push: true
    tags: owira57/springboot-backend-api:latest
```

### Decisions, Assumptions, and Challenges

#### Decisions
* **ClusterIP for MongoDB**: Simplified internal service communication within the cluster.
* **Separate the dependency and service configurations into distinct YAML files for Spring Boot and MongoDB in 
Kubernetes:** Deployment Flexibility: Allows independent application of configurations :MongoDB can be deployed and verified first, ensuring the database is ready before starting the Spring Boot application.

#### Assumptions
* The MongoDB pod uses a persistent volume (emptyDir) for temporary storage.
* All endpoints and functionality were tested locally before containerization.

#### Challenges 
* **Database Connectivity:** Ensured MongoDB and the Spring Boot application were correctly connected by configuring SPRING_DATA_MONGODB_URI in springboot.yaml.
* **Port Conflict in port fowarding** To avoid port conflict ensure that the local instance of mongodb is switched off before running the image and port foward
