<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${not empty pageTitle ? pageTitle : 'Available Cheat Sheets'}</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" 
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
:root {
	--primary-color: #764ba2; /* မင်းရဲ့ Theme အရောင် ခရမ်းရောင်လေးနဲ့ ကိုက်ညီအောင် ညှိထားပါတယ် */
	--bg-color: #f8f9fa;
}

body {
	background-color: var(--bg-color);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.list-header {
	background: white;
	padding: 2rem 0;
	border-bottom: 1px solid #e9ecef;
	margin-bottom: 3rem;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.02);
}

.custom-card {
	border: none;
	border-radius: 16px;
	transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
	background: #fff;
	height: 100%;
}

.custom-card:hover {
	transform: translateY(-10px);
	box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1) !important;
}

.icon-box {
	width: 70px;
	height: 70px;
	background: #f0f3ff;
	border-radius: 14px;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 0 auto 1.5rem;
	color: var(--primary-color);
}

.text-truncate-2 {
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
	overflow: hidden;
	min-height: 3rem;
}

.empty-state {
	padding: 5rem 0;
	color: #adb5bd;
}
</style>
</head>
<body>

	<c:import url="navbar.jsp" />

	<header class="list-header">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md-8">
					<div class="d-flex align-items-center mb-1">
						<i class="bi bi-layers-fill me-2" style="font-size: 2.5rem; color: #764ba2;"></i>
						<h1 class="fw-bold m-0">${not empty pageTitle ? pageTitle : 'Cheat Sheets'}</h1>
					</div>
					<p class="text-muted small ms-5 mb-0">Explore all available technical guides for ${not empty pageTitle ? pageTitle : 'Cheat Sheets'}</p>
				</div>

				<div class="col-md-4 text-md-end mt-3 mt-md-0">
					<div class="d-inline-flex gap-2">
						<c:if test="${not empty sessionScope.user and sessionScope.user.role eq 'Admin'}">
							<a href="add-sheet?subId=${param.subId}"
								class="btn btn-primary rounded-pill px-4 shadow-sm" style="background: linear-gradient(to right, #667eea, #764ba2); border: none;"> 
								<i class="fa fa-plus me-2"></i> Create New
							</a>
						</c:if>

						<a href="sub-categories?catId=${param.catId != null ? param.catId : 1}"
							class="btn btn-outline-secondary rounded-pill px-4"> 
							<i class="fa fa-arrow-left me-1"></i> Back
						</a>
					</div>
				</div>			</div>
			
			<div class="row">
				<div class="col-12">
					<hr class="text-muted opacity-25 mt-3 mb-0">
				</div>
			</div>
		</div>
	</header>

	<main class="container mb-5">
		<c:choose>
			<c:when test="${not empty sheets}">
				<div class="row g-4">
					<c:forEach var="sheet" items="${sheets}">
						<div class="col-xl-3 col-lg-4 col-md-6">
							<div class="card custom-card shadow-sm p-4 d-flex flex-column justify-content-between">
								<div class="card-body text-center p-0 d-flex flex-column h-100">

									<div class="icon-box">
										<c:set var="t" value="${sheet.title.toLowerCase()}" />
										<c:choose>
											<c:when test="${t.contains('java') and !t.contains('javascript')}">
												<i class="fab fa-java fa-3x"></i>
											</c:when>
											<c:when test="${t.contains('python')}">
												<i class="fab fa-python fa-3x"></i>
											</c:when>
											<c:when test="${t.contains('js') or t.contains('javascript')}">
												<i class="fab fa-js fa-3x"></i>
											</c:when>
											<c:when test="${t.contains('html')}">
												<i class="fab fa-html5 fa-3x"></i>
											</c:when>
											<c:when test="${t.contains('css')}">
												<i class="fab fa-css3-alt fa-3x"></i>
											</c:when>
											<c:when test="${t.contains('sql')}">
												<i class="fa-solid fa-database fa-3x"></i>
											</c:when>
											<c:otherwise>
												<i class="fa-solid fa-file-code fa-3x"></i>
											</c:otherwise>
										</c:choose>
									</div>

									<h5 class="fw-bold text-dark mb-2">${sheet.title}</h5>
									<p class="text-muted small text-truncate-2 mb-4">${sheet.content}</p>

									<div class="mt-auto w-100">
										<a href="detail?id=${sheet.id}&subId=${param.subId}&catId=${param.catId}"
											class="btn btn-primary w-100 rounded-pill shadow-sm" style="background: linear-gradient(to right, #667eea, #764ba2); border: none;"> 
											View Details
										</a>
									</div>

								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:when>

			<c:otherwise>
				<div class="empty-state text-center">
					<i class="fa-regular fa-folder-open fa-5x mb-4" style="color: #ccc;"></i>
					<h3>No Cheat Sheets Found</h3>
					<p class="text-muted">There are no items to display in this list at the moment.</p>
				</div>
			</c:otherwise>
		</c:choose>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>