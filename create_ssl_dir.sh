#!/bin/bash
# Password used for all the certs, keys, and stores
PASS=test1234
# Broker server host
SERVER_HOST=localhost
# Client server host
CLIENT_HOST=localhost

# Create the root CA
openssl req -new -x509 -keyout ca-key -out ca-cert -days 365 \
-passout pass:$PASS -batch \
-subj "/C=US/ST=Oregon/L=Portlad/O=Okta/CN=CARoot"

# Import the root CA into server truststore
keytool -keystore server.truststore.jks -alias CARoot -import -file ca-cert -storepass $PASS -noprompt

# Import the root CA into the client truststore
keytool -keystore client.truststore.jks -alias CARoot -import -file ca-cert -storepass $PASS -noprompt

# Create the server keystore with a private key and unsigned certificate.
keytool -keystore server.keystore.jks -alias server \
-validity 365 -keyalg RSA -genkey -storepass $PASS -ext SAN=DNS:$SERVER_HOST \
-dname "CN=$SERVER_HOST,OU=Kafka-Spring,O=Okta,L=Portland,S=Oregon,C=US"

# Export server cert
keytool -keystore server.keystore.jks -alias server -certreq -file cert-file-server -storepass $PASS

# Sign the server cert with the root CA
openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file-server -out cert-signed-server -days 365 -CAcreateserial -passin pass:$PASS

# Import server cert and root CA into server keystore
keytool -keystore server.keystore.jks -alias CARoot -import -file ca-cert -storepass $PASS -noprompt
keytool -keystore server.keystore.jks -alias server -import -file cert-signed-server -storepass $PASS -noprompt

keytool -keystore client.keystore.jks -alias client \
-validity 365 -keyalg RSA -genkey -storepass $PASS -ext SAN=DNS:$CLIENT_HOST \
-dname "CN=$CLIENT_HOST,OU=Kafka-Spring,O=Okta,L=Portland,S=Oregon,C=US"

# Export client cert
keytool -keystore client.keystore.jks -alias client -certreq -file cert-file-client -storepass $PASS

# Sign the client cert
openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file-client -out cert-signed-client -days 365 -CAcreateserial -passin pass:$PASS

# Import client cert and CA into client keystore
keytool -keystore client.keystore.jks -alias CARoot -import -file ca-cert -storepass $PASS -noprompt
keytool -keystore client.keystore.jks -alias client -import -file cert-signed-client -storepass $PASS -noprompt

# Create the Kafka server config
cat <<EOT >> kafka_server_jaas.conf
KafkaServer {
      org.apache.kafka.common.security.plain.PlainLoginModule required
      username="admin"
      password="admin-secret"
      user_admin="admin-secret"
      user_alice="alice-secret";
};
EOT

# Create the Kafka client config
cat <<EOT >> kafka_client_jaas.conf
KafkaClient {
      org.apache.kafka.common.security.plain.PlainLoginModule required
      username="alice"
      password="alice-secret";
};
EOT
