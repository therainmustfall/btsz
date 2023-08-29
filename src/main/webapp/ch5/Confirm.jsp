<!DOCTYPE HTML>
<html>
	<head>
	<meta charset='utf-8'>
	<title>(ch5)Confirm Page with Edit Option</title>
	</head>
	<body>
		<p>the value of the hobby that was sent to this page is: hobby of <b>${helper.data.hobby}</b>
        with ${helper.data.daysPerWeek} days per week and aversion of <b>${helper.data.aversion}</b>.
		<form method='POST' action="Controller">
			<p> if there is an error, please select <em>Edit</em>.<br>
			<input type='submit' name='editButton' value='Edit'>
			<input type='submit' name='processButton' value='Process'>
		</form>
	</body>
</html>
