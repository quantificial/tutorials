## generate the private key

openssl genrsa -out jwt.pem 2048
openssl rsa -in jwt.pem 



## generate the public key

openssl rsa -in jwt.pem -pubout 


## generate jwt keystore

keytool -genkeypair -alias jwt -keyalg RSA -dname "CN=HK, L=HK, S=HK, C=HK" -keypass mySecretKey -keystore jwt.jks -storepass mySecretKey

## extract public key from jks to text pem 
 
keytool -export -alias jwt -file jwt-pub.der -keystore jwt.jks

openssl x509 -inform der -in jwt-pub.der -out jwt-pub.pem


quick gen: keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey -noout

## extract private key from jwt to text pem

keytool -importkeystore -srckeystore jwt.jks -destkeystore jwt.p12 -deststoretype PKCS12

openssl pkcs12 -in jwt.p12  -nodes -nocerts -out jwt.key


## reference

https://support.symantec.com/us/en/article.tech242030.html

