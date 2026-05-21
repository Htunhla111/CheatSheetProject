<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Cheat Sheet - ${sheet.title}</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<link rel="stylesheet" 
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-light">

	<%-- Navbar --%>
	<%@ include file="navbar.jsp"%>

	<div class="container mt-5 mb-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card shadow border-0">
					<div class="card-header bg-success text-white py-3">
						<h4 class="mb-0">
							<i class="bi bi-pencil-square"></i> Edit Cheat Sheet
						</h4>
					</div>
					<div class="card-body p-4">

						<%-- Error Message ပြရန် --%>
						<c:if test="${not empty param.error}">
							<div class="alert alert-danger alert-dismissible fade show" role="alert">
								<i class="bi bi-exclamation-triangle-fill me-2"></i> ${param.error}
								<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
							</div>
						</c:if>

						<form action="edit-sheet" method="post">
							<%-- အရေးကြီးဆုံး Hidden Fields များ --%>
							<input type="hidden" name="id" value="${sheet.id}">
							<input type="hidden" name="subCategoryId" value="${sheet.subCategoryId}">

							<%-- Title Input --%>
							<div class="mb-3">
								<label class="form-label fw-bold">Title</label> 
								<input type="text" name="title" class="form-control"
									placeholder="e.g. Java Streams API"
									value="${sheet.title}" required>
							</div>

							<%-- Category Display (Read-only) --%>
							<div class="mb-3">
								<label class="form-label text-secondary fw-bold">Category</label>
								<div class="input-group">
									<span class="input-group-text bg-light border-end-0"> 
										<i class="fa fa-tag text-muted"></i>
									</span> 
									<input type="text"
										class="form-control bg-light border-start-0"
										value="${sheet.subCategoryName}" readonly>
								</div>
								<div class="form-text text-muted">
									<small><i class="bi bi-info-circle"></i> Category not transfer.</small>
								</div>
							</div>

							<%-- Content Textarea --%>
							<div class="mb-4">
								<label class="form-label fw-bold">Content (Code/Info)</label>
								<textarea name="content" class="form-control" rows="12"
									style="font-family: 'Courier New', monospace; font-size: 0.95rem;" 
									required>${sheet.content}</textarea>
							</div>

							<%-- Form Buttons --%>
							<div class="d-grid gap-2 d-md-flex justify-content-md-end border-top pt-3">
								<a href="detail?id=${sheet.id}"
									class="btn btn-outline-secondary px-4 rounded-pill me-md-2">
									Cancel
								</a>
								<button type="submit" class="btn btn-success px-4 rounded-pill">
									<i class="bi bi-check-circle me-1"></i> Update Sheet
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>