<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<h1>Bienvenue sur home</h1>
	<div class="container">
		<div class="list-group">
			<c:forEach items="${ persons }" var="person">
				<div class="list-group-item list-group-item-action">
					<p class="mb-1">
						<c:out value="${ person.firstName }" />
					</p>
					<small class="text-muted"><c:out
							value="${ person.lastName }" /></small> <small class="text-muted"><c:out
							value="${ person.age }" /></small>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>