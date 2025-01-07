function Send-Request (
    [string] $url,
    [string] $method      = "GET",
    [byte[]] $content     = $null,
    [string] $contentType = $null
) {
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
