<%@ page language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Send Request</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/index.css">
</head>
<body>
	<script defer src="${pageContext.servletContext.contextPath}/js/index.js"></script>
	<div>
		<form method="post" enctype="text/plain"
			action="${pageContext.servletContext.contextPath}/default">
			<p>
				<label>Body:</label> <input type="text" name="text" />
			</p>
			<p>
				<button type="submit">Send Request</button>
			</p>
		</form>
		<form method="post" enctype="multipart/form-data"
			action="${pageContext.servletContext.contextPath}/default">
			<p>
				<label>Body:</label> <input type="file" name="file" />
			</p>
			<p>
				<button type="submit">Send Request</button>
			</p>
		</form>
		<form name="dump" method="post" enctype="text/plain"
			action="${pageContext.servletContext.contextPath}/dumpmedia">
			<p>
				<label>URL:</label> <input type="text" name="url" />
			</p>
			<p>
				<label>HTML:</label>
			</p>
			<p>
				<textarea rows="20" cols="50" name="html"></textarea>
			</p>
			<p>
				<button type="submit">Dump Media</button>
			</p>
		</form>
	</div>
</body>
</html>
