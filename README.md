# DIST_LAB_3
Making a naming service and deploying it on a node ring.

## Server
### Requisites
The server has to be able to:
- map the name of nodes and files with their respective ID, this will be done using hashing.
- communicate with nodes using a RESTful API.

## Client
### Requisites
The client has to:
- store local/replicated files
- be deployed on the nodes
- communicate with the server in order to find files
- communicate with the server in order to show existing files
