<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Cheat Sheets List</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<nav class="navbar navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand" href="home">CheatSheet Central</a>
		</div>
	</nav>

	<div class="container"></div>
	<div class="row">
		<c:forEach var="sheet" items="${sheets}">
			<div class="col-md-4 mb-3">
				<div class="card shadow-sm h-100">
					<div class="card-body d-flex flex-column">
						<h5 class="card-title">${sheet.title}</h5>
						<p class="card-text text-muted small">Learn more about
							${sheet.title}</p>
						<a href="detail?id=${sheet.id}" class="btn btn-primary mt-auto">
							View Content </a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<c:if test="${empty sheets}">
		<div class="alert alert-info">No cheat sheets found in this
			category.</div>
	</c:if>

	<a href="home" class="btn btn-secondary mt-3">Back to Categories</a>
</body>
</html>