
## work for quick key

keytool -genkeypair -alias jwt -keyalg RSA -keypass abcd1234 -keystore jwt.jks -storepass abcd1234

keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey

view key 

keytool -list -v --keystore jwt.jks


## work for v3 key

```
# The below command will create a file named 'server.pass.key' and place it in the same folder where the command is executed. 
$ openssl genrsa -des3 -passout pass:x -out server.pass.key 2048
# The below command will use the 'server.pass.key' file that just generated and create 'server.key'.
$ openssl rsa -passin pass:x -in server.pass.key -out server.key
# We no longer need the 'server.pass.key'
$ rm server.pass.key
$


# The below command will ask you for information that would be included in the certificate. Since this is a self-signed certificate, there is no need to provide the 'challenge password' (to leave it blank, press enter).
$ openssl req -new -key server.key -out server.csr

# create v3.ext

authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment



$ openssl x509 -req -sha256 -extfile v3.ext -days 365 -in server.csr -signkey server.key -out server.crt
Signature ok
subject=/C=<country>/ST=<state>/L=<locality>/O=<organization-name>/OU=<organization-unit-name>/CN=<common-name-probably-server-fqdn>/emailAddress=<email-address-provided-while-generating-csr>
Getting Private key
$

# To P12

openssl pkcs12 -export -name servercert -in server.crt -inkey server.key -out myp12keystore.p12


# P12 to JKS

keytool -importkeystore -destkeystore mykeystore.jks -srckeystore myp12keystore.p12 -srcstoretype pkcs12 -alias servercert




# get public key

$ keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey -noout > public.txt



keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey


```


## reference

https://support.symantec.com/us/en/article.tech242030.html

https://dzone.com/articles/creating-self-signed-certificate

---

## study below

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




## more example for exporting public key (may not work)

 keytool -importkeystore -srckeystore jwt.jks -srcstorepass abcd1234 -srckeypass abcd1234 -srcalias jwt -destalias jwt -destkeystore jwt. p12 -deststoretype PKCS12 -deststorepass abcd1234 -destkeypass abcd1234
Importing keystore jwt.jks to jwt.p12...
Existing entry alias jwt exists, overwrite? [no]:  yes


extract private and public key

openssl pkcs12 -in jwt.p12 -nodes -nocerts -out jwt-private.pem

openssl pkcs12 -in jwt.p12 -nokeys -out jwt-pub.pem




