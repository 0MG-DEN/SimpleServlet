**Note:**
Tomcat may redirect requests sent with partial URLs, i.e.
`http://localhost:8080/simpleservlet` instead of
`http://localhost:8080/simpleservlet/`.
Redirected request method may change to `GET`, losing original content.

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
