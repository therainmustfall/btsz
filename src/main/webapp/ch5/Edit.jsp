<!DOCTYPE HTML>
<html>
	<head>
	<meta charset='utf-8'>
	<title>(ch5) Simple Edit Page</title>
	</head>
	<body>
		<form method='POST' action='Controller'>
			<p>This is a simple HTML page that has a form in it.</p>
			<p>
            <p>Hobby ${helper.errors.hobby}: <input type='text' name='hobby' value='${helper.data.hobby}'>
            <p>Aversion ${helper.errors.aversion}: <input type='text' name='aversion' value='${helper.data.aversion}'>
            <p>Days per Week ${helper.errors.daysPerWeek}: <input type='text' name='daysPerWeek' value='${helper.data.daysPerWeek}'>
            <input type='submit' name='confirmButton' value='Confirm'>
		</form>

	</body>
</html>
