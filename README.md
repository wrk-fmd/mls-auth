# MLS Auth Service

TODO Document everything

## Generation of JWT keys

The JWT are signed with asymmetric keys to allow verification without the private key.
The keys must be stored in *DER* format for Java to be able to read them.

* Generate a private key:
  `openssl ecparam -name prime256v1 -genkey -noout -out name.pem`
* Transform the private key to PKCS8 in DER format:
  `openssl pkcs8 -topk8 -outform DER -nocrypt -in name.pem -out name.key`
* Extract the public key in DER format:
  `openssl ec -pubout -outform DER -in name.pem -out name.pub`
