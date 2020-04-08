# Secure Kafka Streams with Quarkus and Java

A Kafka Streams example app that shows how to create secure Java REST endpoints with Quarkus and communicate securely with Kafka. 

Please read [Secure Kafka Streams with Quarkus and Java](http://developer.okta.com/blog/2020/04/08/kafka-streams) to see how this app was created.

**Prerequisites:** [Java 11](https://adoptopenjdk.net/).

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage, and secure users + roles in any application.

* [Getting Started](#getting-started)
* [Links](#links)
* [Help](#help)
* [License](#license)

## Getting Started

To install this example application, run the following commands:

```bash
git clone https://github.com/oktadeveloper/okta-kafka-streams-quarkus-example.git
cd okta-kafka-streams-quarkus-example/quarkus-client
```

This will get a copy of the project installed locally. To run the example, run the following command:
 
```bash
./mvnw compile quarkus:dev
```

### Create an OIDC App in Okta

You will need to create an OIDC App in Okta to get a `clientId` to generate a JWT for authentication. 

Log in to your Okta Developer account (or [sign up](https://developer.okta.com/signup/) if you don’t have an account) and navigate to **Applications** > **Add Application**. 

- Select **Web** and click **Next** 
- Change the **Name** to be `Quarkus Client` 
- Change the **Login redirect URIs** to `http://localhost:8080/`
- Scroll down and click **Done**

Put your Okta domain name into `src/main/resources/application.properties`, along with your client ID and secret. 

```properties
quarkus.oidc.auth-server-url=https://{yourOktaDomain}/oauth2/default
quarkus.oidc.client-id={yourClientId}
quarkus.oidc.credentials.secret={yourClientSecret}
quarkus.oidc.authentication.scopes=openid,profile
quarkus.oidc.application-type=web-app
quarkus.oidc.authentication.redirect-path=/
```

**NOTE:** The value of `{yourOktaDomain}` should be something like `dev-123456.okta.com`. Make sure you don't include `-admin` in the value!

After modifying this file, restart your app and it'll be protected by Okta! See this repo's [blog post](http://developer.okta.com/blog/2020/04/08/kafka-streams) to learn how to connect this app with a running Kafka server.

## Links

This example uses [Quarkus's OpenID Connect Adapter](https://quarkus.io/guides/security-openid-connect) to integrate with Okta.

## Help

Please post any questions as comments on the [blog post](http://developer.okta.com/blog/2020/04/08/kafka-streams), or visit our [Okta Developer Forums](https://devforum.okta.com/). 

## License

Apache 2.0, see [LICENSE](LICENSE).
