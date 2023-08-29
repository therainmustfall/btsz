<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE HTML>
<html>
	<head>
	<meta charset='utf-8'>
	<title>(ch5) Process Page</title>
	</head>
	<body>
            <p> Thank you for your information, your hobby <strong>${helper.data.hobby}</strong>  with ${helper.data.daysPerWeek} days per week and aversion <strong>${helper.data.aversion}</strong> will be added to our records, eventually.
        
        <core:forEach var="row" items="${database}">
                ${row.id},
                ${row.hobby},
                ${row.aversion},
                ${row.daysPerWeek}<br>
        </core:forEach>
	</body>
</html>
