<!DOCTYPE HTML>
<html>
	<head>
	<meta charset='utf-8'>
	<title>(ch3) Simple Edit Page</title>
	</head>
	<body>
		<form action='Controller'>
			<p>This is a simple HTML page that has a form in it.</p>
			<p>
			<p>Hobby: <input type='text' name='hobby' value='${helper.data.hobby}'>
            <p>Aversion: <input type='text' name='aversion' value='${helper.data.aversion}'>
            <input type='submit' name='confirmButton' value='Confirm'>
		</form>

	</body>
</html>
