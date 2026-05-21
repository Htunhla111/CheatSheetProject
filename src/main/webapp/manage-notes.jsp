<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage User Notes</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

	<%-- <%@ include file="admin-navbar.jsp" %> --%>
	<%@ include file="navbar.jsp"%>

	<div class="container mt-5">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2 class="fw-bold text-dark m-0">
				<i class="fas fa-sticky-note text-primary me-2"></i> Manage User
				Personal Notes
			</h2>
			<a href="home"
				class="btn btn-outline-secondary rounded-pill px-4 fw-bold shadow-sm">
				<i class="fas fa-arrow-left me-2"></i> Back to Home
			</a>
		</div>

		<c:if test="${not empty sessionScope.succMsg}">
			<div
				class="alert alert-success alert-dismissible fade show rounded-3 shadow-sm"
				role="alert">
				<i class="fas fa-check-circle me-2"></i> ${sessionScope.succMsg}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<c:remove var="succMsg" scope="session" />
		</c:if>

		<div class="card shadow border-0 rounded-3">
			<div class="card-body p-0">
				<div class="table-responsive">
					<table class="table table-hover align-middle mb-0">
						<thead class="table-dark text-uppercase fs-7">
							<tr>
								<th class="ps-4" style="width: 8%;">ID</th>
								<th style="width: 20%;">User Name</th>
								<th style="width: 25%;">Cheat Sheet Title</th>
								<th style="width: 35%;">Personal Remark (Note)</th>
								<th class="text-center pe-4" style="width: 12%;">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty allNotes}">
									<c:forEach var="note" items="${allNotes}">
										<tr>
											<td class="ps-4 fw-bold text-secondary">#${note.id}</td>
											<td><span
												class="badge bg-info text-dark rounded-pill px-3 fw-semibold fs-6">
													<i class="fas fa-user me-1"></i> ${note.userName}
											</span></td>
											<td class="fw-bold text-dark">${note.sheetTitle}</td>
											<td><c:choose>
													<c:when
														test="${not empty note.personalRemark && note.personalRemark.trim().length() > 0}">
														<p class="mb-0 text-muted text-truncate"
															style="max-width: 350px;" title="${note.personalRemark}">
															${note.personalRemark}</p>
													</c:when>
													<c:otherwise>
														<span class="text-danger fst-italic fs-7">(Empty
															Note)</span>
													</c:otherwise>
												</c:choose></td>
											<td class="text-center pe-4"><a
												href="${pageContext.request.contextPath}/delete-user-note?id=${note.id}"
												class="btn btn-sm btn-danger rounded-pill px-3"
												onclick="return confirm('Are you sure you want to delete this user note?');">
													<i class="fas fa-trash-alt me-1"></i> Delete
											</a></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5" class="text-center py-5 text-muted"><i
											class="fas fa-folder-open fa-3x mb-3 text-secondary"></i>
											<p class="mb-0 fw-bold">No user personal notes found in
												database.</p></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>