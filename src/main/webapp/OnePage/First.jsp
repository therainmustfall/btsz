<!DOCTYPE HTML>
<html>
	<head>
	<meta charset='utf-8'>
	<title>First Jsp</title>
	</head>
	<body>
		<form>
			<p>This is a simple HTML page that has a form in it.</p>
			<p>
			The hobby was recieved as: <strong>${param.hobby}</strong>
			<p>Hobby: <input type='text' name='hobby' value='${param.hobby}'>
			<input type='submit' name='confirmButton' value='Confirm'>
		</form>

	</body>
</html>
