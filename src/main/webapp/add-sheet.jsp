<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Add New Cheat Sheet</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">
	<%@ include file="navbar.jsp"%>
	<div class="container mt-5">
		<div class="card shadow border-0 rounded-3 p-4 mx-auto" style="max-width: 800px;">
			<h2 class="mb-4 fw-bold text-primary">
				<i class="fa-solid fa-file-circle-plus me-2"></i>Create New Cheat Sheet
			</h2>
			
			<form action="add-sheet" method="post">
				<input type="hidden" name="subId" value="${param.subId}">

				<div class="mb-3">
					<label class="form-label fw-bold text-secondary">Sheet Title</label> 
					<input type="text" name="title" class="form-control rounded-3"
						placeholder="e.g. Java Basics" required>
				</div>
				
				<div class="mb-3">
					<label class="form-label fw-bold text-secondary">Content</label>
					<textarea name="content" class="form-control rounded-3" rows="12"
						placeholder="Write your notes here..." required></textarea>
				</div>
				
				<div class="d-flex pt-2">
					<button type="submit" class="btn btn-primary rounded-pill px-4 me-2">
						<i class="fa fa-save me-1"></i> Save Now
					</button>
					
					<a href="sheets?subId=${param.subId}"
						class="btn btn-outline-danger rounded-pill px-4"> 
						<i class="fa fa-times me-1"></i> Cancel
					</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>