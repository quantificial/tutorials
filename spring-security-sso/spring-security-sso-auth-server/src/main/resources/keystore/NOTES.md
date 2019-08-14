## generate the private key

openssl genrsa -out jwt.pem 2048
openssl rsa -in jwt.pem 



## generate the public key

openssl rsa -in jwt.pem -pubout 


