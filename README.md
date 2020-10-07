Remarks: 
The Java code bases from this Java Blog https://www.javainuse.com/spring/springboot_session_redis, and then I add some customization to make it run at container environment working with Amazon ElastiCache Redis  

1.) Build:

mvn clean install

2.) Eclipse project:

mvn eclipse:eclipse

3.) Local test

mvn spring-boot:run

4.) Test at PostMan

http://localhost:8080/

5.) Docker build:

docker build -t cheungtom/redis-web .

6.) Run docker container

docker run -d --name redis-web -p 8080:8080 cheungtom/redis-web

docker ps -a

7.) Logs

docker logs -f redis-web

8.) Docker Image push

docker login

docker push cheungtom/redis-web

9.) Kubernetes deploy

kubectl apply -f redis-web-replicaset.yaml

kubectl get pods

kubectl logs -f redis-web-p6xrb

kubectl get rs


kubectl apply -f redis-web-service.yaml

kubectl get svc

http://localhost:80

11.a) Install OpenShift on AWS by CloudFormtaion
Follow this link:

https://aws.amazon.com/quickstart/architecture/openshift/

Web OCP Admin console link:

https://red-hat-o-openshif-19mmubql4jye5-283815188.ap-southeast-2.elb.amazonaws.com/console/catalog

Connect to ansible-configserver
ssh -A -i "sydney_key.pem" ec2-user@ec2-3-25-75-82.ap-southeast-2.compute.amazonaws.com

Admin login:
sudo -s
oc whoami
You should be cluster admin system:admin
exit

Normal user login:
oc login
Server: https://Red-Hat-O-OpenShif-19MMUBQL4JYE5-283815188.ap-southeast-2.elb.amazonaws.com:443 (openshift)
User: admin
Pwd: When you set admin password for CloudFormation template
 
oc logout

The following section assume you use normal user login

11.b) Deploy to OpenShift - Manually build
oc new-project redis-ui
oc project redis-ui

Deploy Web API Microservice:
oc apply -f redis-web-replicaset.yaml
oc get pods

oc get rs

oc apply -f redis-web-service.yaml
oc get svc

oc expose service/redis-web
oc get routes

oc create -f nginx-pod.yml
oc exec -it nginx bash
apt update
apt install iputils-ping
apt install curl
apt install jq

Get by Service ELB
curl http://add58f6d0a66711ea97f802415f432a5-1812126023.ap-southeast-2.elb.amazonaws.com:80/

Get by K8s service name
curl http://redis-web:80/

Get by OpenShift Inbound Traffic Router
curl http://redis-web-redis-ui.router.default.svc.cluster.local:80/

oc delete all --all

12.) Deploy by S2I image builder (Only apply for Maven jar project, war not support)
oc new-project redis-s2i
oc project redis-s2i

S2I can deploy jar only, not war. The follow not work

Deploy Web microservice
oc new-app java~https://github.com/cheungtom/Spring-Boot-Session-Example-Redis.git

oc delete all --all

13.) Deploy by S2I image builder template (Only apply for Maven jar project, war not support)
oc new-project redis-ui
oc project redis-ui

We use openjdk18-web-basic-s2i template
Same as your select image builder "OpenJDK 8" at OC Admin UI
Admin UI build limit: cannot pass env var

oc process --parameters openjdk18-web-basic-s2i -n openshift

oc export template openjdk18-web-basic-s2i -n openshift

S2I can deploy jar only, not war. The follow not work

Deploy Web microservice
oc new-app --template=openjdk18-web-basic-s2i -p APPLICATION_NAME=spring-boot-session-example-redis \
-p SOURCE_REPOSITORY_URL=https://github.com/cheungtom/Spring-Boot-Session-Example-Redis.git \
-p SOURCE_REPOSITORY_REF=master \
-p CONTEXT_DIR=

oc delete all --all
