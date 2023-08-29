<!DOCTYPE HTML>
<html>
	<head>
	<meta charset='utf-8'>
	<title>(Servlet)Confirm Page with Edit Option</title>
	</head>
	<body>
		<p>the value of the hobby that was sent to this page is: <b>${param.hobby}</b>.
		<form action="Controller">
			<p> if there is an error, please select <em>Edit</em>.<br>
			<input type='hidden' name='hobby' value='${param.hobby}'>
			<input type='submit' name='editButton' value='Edit'>
			<input type='submit' name='processButton' value='Process'>
		</form>
	</body>
</html>
