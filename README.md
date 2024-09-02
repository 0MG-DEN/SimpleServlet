**Note:**
Tomcat may redirect requests sent with partial URLs, i.e.
`http://localhost:8080/simpleservlet` instead of
`http://localhost:8080/simpleservlet/`.
Redirected request method may change to `GET`, losing original content.

* Function to send HTTP requests with PowerShell 1.0:

```powershell
function Send-Request ([string] $url, [string] $method = "GET", [byte[]] $content = $null, [string] $contentType = $null) {
    $request = [System.Net.WebRequest]::Create($url)
    $request.Method = $method

    if ($method -ieq "POST" -and $content -ne $null) {
        $request.ContentType = $contentType
        $request.ContentLength = $content.Length

        $stream = $request.GetRequestStream()
        $stream.Write($content, 0, $content.Length)
        $stream.Flush()
        $stream.Close()
    }
    try {
        $response = $request.GetResponse()
        $stream = $response.GetResponseStream()
        $reader = New-Object -TypeName "System.IO.StreamReader" -ArgumentList $stream
        $result = $reader.ReadToEnd()
        $reader.Close()
    }
    catch {
        $result = $_.ToString()
    }
    return $result
}
```

* HTML form to send `multipart/form-data` requests:

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

* State diagram corresponding to the
[`Parser`](src/simpleservlet/util/parsing/Parser.java) and
[`ParserMap`](src/simpleservlet/util/parsing/ParserMap.java):

```mermaid
flowchart
subgraph " "
id0001[NONE]
id0002[TAG_START]
id000S["parse()"] -.-> id0001
style id000S fill:orange
end

subgraph " "
id0004[TAG_INNER]
id0008[TAG_CLOSE]
id000P["processContent()"] -.-> id0001
style id000P fill:orange
end

subgraph Image
id0001-1{" "} -.-> id0001
id0020[TAG_IMG_I]
id0040[TAG_IMG_M]
id0080[TAG_IMG_G]
end

subgraph Video
id0001-2{" "} -.-> id0001
id0400[TAG_VIDEO_V]
id0800[TAG_VIDEO_I]
id1000[TAG_VIDEO_D]
id2000[TAG_VIDEO_E]
id4000[TAG_VIDEO_O]
end

id0001 ===>|"[ < ]"| id0002
id0001 -.-> id0001

id0002 ===>|"[ i ]"| id0020
id0002 ===>|"[ v ]"| id0400
id0002 -.-> id0001

id0004 ===>|"[ / ]"| id0008
id0004 ===>|"[ > ]"| id000P
id0004 ===> id0004

id0008 ===>|"[ > ]"| id000P
id0008 ===> id0004

%% Image.
id0020 ===>|"[ m ]"| id0040
id0020 -.-> id0001-1

id0040 ===>|"[ g ]"| id0080
id0040 -.-> id0001-1

id0080 ===>|"[   ]"| id0004
id0080 -.-> id0001-1

%% Video.
id0400 ===>|"[ i ]"| id0800
id0400 -.-> id0001-2

id0800 ===>|"[ d ]"| id1000
id0800 -.-> id0001-2

id1000 ===>|"[ e ]"| id2000
id1000 -.-> id0001-2

id2000 ===>|"[ o ]"| id4000
id2000 -.-> id0001-2

id4000 ===>|"[   ]"| id0004
id4000 -.-> id0001-2
```
