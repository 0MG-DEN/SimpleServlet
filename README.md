>Tomcat may redirect requests sent with partial URLs, i.e.
>`http://localhost:8080/simpleservlet` instead of
>`http://localhost:8080/simpleservlet/`.
>Redirected request method may change to `GET`, losing original content.

* [Function](docs/Send-Request.ps1) to send HTTP requests with PowerShell 1.0.

* [State diagram](docs/parser.md) corresponding to `Parser` and `ParserMap`.

* [HTML form](docs/web/index.html) to send `multipart/form-data` requests.

* `server.xml` snippet to [configure SSL](https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html):

```xml
<!-- ${user.home} points to the current user's home directory. -->
<Connector protocol="org.apache.coyote.http11.Http11NioProtocol"
           secure="true" scheme="https" port="8443"
           keystoreFile="${user.home}/.keystore"
           keystorePass="changeit"
           SSLEnabled="true" />
```
