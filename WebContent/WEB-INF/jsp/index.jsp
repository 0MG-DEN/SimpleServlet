<%@ page language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Send Request</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/index.css">
</head>
<body>
	<script defer src="${pageContext.servletContext.contextPath}/js/index.js"></script>
	<div class="header">
		<span>Send Request</span>
	</div>
	<div class="form">
		<form method="post" enctype="multipart/form-data"
			action="${pageContext.servletContext.contextPath}/default">
			<p>
				<label>Body:</label> <input type="file" name="file" multiple />
			</p>
			<p>
				<button type="submit">Send Request</button>
			</p>
		</form>
	</div>
	<div class="form">
		<form method="post" enctype="text/plain"
			action="${pageContext.servletContext.contextPath}/default">
			<p>
				<label>Body:</label>
			</p>
			<p>
				<textarea rows="20" cols="50" name="text"></textarea>
			</p>
			<p>
				<button type="submit">Send Request</button>
			</p>
		</form>
	</div>
	<div class="header">
		<span>Dump Media</span>
	</div>
	<div class="form">
		<form name="dump" method="post" enctype="text/plain"
			action="${pageContext.servletContext.contextPath}/dumpmedia">
			<p>
				<label>URL:</label> <input type="text" name="url" />
			</p>
			<p>
				<button type="submit">Dump Media</button>
			</p>
		</form>
	</div>
	<div class="form">
		<form name="dump" method="post" enctype="text/plain"
			action="${pageContext.servletContext.contextPath}/dumpmedia">
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
