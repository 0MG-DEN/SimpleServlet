**Note:**
Tomcat may redirect requests sent with partial URLs, i.e.
`http://localhost:8080/simpleservlet` instead of
`http://localhost:8080/simpleservlet/`.
Redirected request method may change to `GET`, losing original content.

* Script to send `POST` request with PowerShell 1.0:

```powershell
$body = [System.Text.Encoding]::UTF8.GetBytes("body")

$request = [System.Net.WebRequest]::Create("http://localhost:8080/simpleservlet/")
$request.Method = "POST"
$request.ContentType = "application/octet-stream"
$request.ContentLength = $body.Length

$stream = $request.GetRequestStream()
$stream.Write($body, 0, $body.Length)
$stream.Flush()
$stream.Close()

$response = $request.GetResponse()
$response
```

* HTML form to send `multipart/form-data` request:

```html
<form action="http://localhost:8080/simpleservlet/" method="post" enctype="multipart/form-data">
    <p><input type="text" name="text">
    <p><input type="file" name="file">
    <p><button type="submit">Submit</button>
</form>
```

* `server.xml` snippet to [configure SSL](https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html):

```xml
<Connector protocol="org.apache.coyote.http11.Http11NioProtocol"
           secure="true" scheme="https" port="8443"
           keystoreFile="${user.home}/.keystore"
           keystorePass="changeit"
           SSLEnabled="true" />
```

**Note:**
`${user.home}` points to the current user's home directory. 