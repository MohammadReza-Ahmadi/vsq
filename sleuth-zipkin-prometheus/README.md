# Spring Cloud Project 
This document is intended to help you quickly setup this project and other dependencies that are required to showcase features of a *Spring cloud application* such as:

* Multi-module spring boot application using *maven*
* **Spring Cloud Config** to provide centrilized configuration
* Spring Boot **Feign Clients** configuration and sample services
* Spring web for providing **RESTful** Application Programming Interface
* Spring Boot **Actuator** Setup and Configuration
* Spring Cloud **Zipkin** integration
* Configuration for exposing **Actuator** endpoints with *Prometheus* format
* Setup and Configuration of **Prometheus** server using docker (baking configuration into docker image)
* Setup and Configuration of **Zipkin** using docker
* Setup and Configuration of **Grafana**

# Getting Started
This section guides you through the process of getting application up and running on your local machine

### Dependencies
* You need a [Docker](https://docs.docker.com/engine/install/ubuntu/) installation (tldr; `sudo apt install docker.io`)
* You need JDK 11 on your system (`sudo apt install openjdk-11-jdk`)

### Setting up Zipkin
Run the following command, and you should be able to access Zipkin on *http://localhost:9411/* :  
`docker run -d -p 9411:9411 openzipkin/zipkin`

### Setting up Prometheus
There are several ways for [configuration](https://prometheus.io/docs/prometheus/latest/installation/) of Prometheus server. Here we create our custom image and will bake our configuration into the image.
1. Open the prometheus directory at the root of this project.
1. You will find a *Dockerfile* that adds the configuration image. In most cases you do not need to edit this:
    > `FROM prom/prometheus`  
     `ADD prometheus.yml /etc/prometheus/`
1. *prometheus.yml* contains the configuration to connect to eureka server instance running on the host machine. All the configuration and customization happens here. (See the [documentation](https://prometheus.io/docs/prometheus/latest/configuration/configuration/).) 
1. Now we build the image with the following command:  
`sudo docker build -t vsq-prometheus .`  
(*Do not miss the `.` at the **end** of the command*)

### Setting up Grafana
1. Create a storage volume for Grafana:  
`sudo docker volume create grafana-storage`
1. Start Grafana server:  
`sudo docker run -d -p 3000:3000 --name=grafana --volume grafana-storage:/var/lib/grafana grafana/grafana`

### Running Spring Boot instances
This project uses maven for project management and build automation.  
To build the project, execute the following command in a terminal while you are at the root directory of the project:  
 `./mvnw clean package`  
Now, that you have built the project, you can start the microservices you need.  
 > *You need to make sure, that the **config** service is up and running before starting any other servers. because our instances are running with the **fail-fast** configuration enabled and will fail to start, if the config server is not available*

To start the instances you can use *spring boot maven plugin*:  
`./mvnw spring-boot:run -pl APPLICATION_NAME`  
>e.g.  `./mvnw spring-boot:run -pl config -Dspring-boot.run.profiles=local`  
>*We are passing `-Dspring-boot.run.profiles=local` to enable **local** configuration for bootstrapping instances with the correct config server address. To add a new profile, You need to edit the **bootstrap.yml** file in the resource directory of microservices and provide the **config server** configuration needed for that profile.*    

You can also start instances directly from built jar files. (See [documentation](https://spring.io/guides/gs/multi-module/).)

Now you should be able to run the project and see log tracing and actuator statistics in Zipkin and Grafana. 