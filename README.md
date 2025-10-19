# Course Registration App

Building a course registration website using ReactJS and Spring boot. This build covers basic funtionalities of a course registration application such as register new courses, cancel enrolled courses, allow admin to add new courses

## Tentative technologies and frameworks
 - Java 17
 - Spring boot 3
 - ReactJS
 - MySQL
 - Kubernetes
 - Prometheus, Grafana and AlertManager
 - EFK stack

### Architecture

1. I have deployed the project on minikube for testing purpose. For more detail about infrastructure, visit at "k8s/application" folder. Here is the architect of my course registration system on k8s:

![Architect Image](images/architect.png)

2. This is the database design of my course registration project:

![Database Image](images/database.png)

### Installation on k8s cluster using helm chart
1. Install kubectl and Helm CLI

   Follow the official installation guides below:

   - kubectl:
     👉 [https://kubernetes.io/docs/tasks/tools/](https://kubernetes.io/docs/tasks/tools/)

   - Helm:
     👉 [https://helm.sh/docs/intro/install/](https://helm.sh/docs/intro/install/)

2. Install nginx ingress
    There is a ingress-nginx helm chart with custom yaml file (values.custom.yaml) in k8s folder that you can use. You may need to modify the file values.custom.yaml or the default values.yaml if needed:
    ```bash
        cd ./k8s
        kubectl create ns ingress-nginx
        helm install ingress-nginx ingress-nginx -f ingress-nginx/values.custom.yaml -n ingress-nginx
    ```
    After deploying nginx ingress, Azure will automatically create a Load Balancer connecting to the nginx ingress. If Azure does not create Load balancer, ask ChatGPT for help!!

2. Update dependencies for helm charts
    Update dependencies for helm chart mysql, backend, frontend by these commands:
    ```bash
    cd ./k8s/application
    helm dependency update mysql --skip-refresh
    helm dependency update backend --skip-refresh
    helm dependency update frontend --skip-refresh
    ```

3. Deploy helm charts
    Deploy helm chart mysql, backend and frontend to the k8s cluster. Please install them in correct order:
    + Install mysql:
    ```bash
    helm install mysql mysql
    ```
    + After checking the mysql Pod runs stable, we can install backend helm chart. Note: ensure that the mysql database is running successfully before deploying the backend. If not, the backend application may get crashed.
    ```bash 
    helm install backend backend
    ```
    + Then install frontend helm chart:
    ```bash 
    helm install frontend frontend
    ```