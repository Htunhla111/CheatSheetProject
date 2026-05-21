<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">

<style>
.navbar-custom {
	background-color: #2c3e50; /* Cheatography dark theme စတိုင် */
	padding: 0.8rem 1rem;
}

.nav-link {
	font-weight: 500;
	transition: color 0.3s;
}

.nav-link:hover {
	color: #f1c40f !important; /* Yellow hover effect */
}

.btn-register {
	background-color: #e67e22; /* Orange-ish Register button */
	border: none;
	font-weight: bold;
	border-radius: 20px;
	padding: 5px 20px;
}

.btn-register:hover {
	background-color: #d35400;
}

.search-input {
	border-radius: 20px 0 0 20px;
	border: none;
}

.search-btn {
	border-radius: 0 20px 20px 0;
	background-color: #34495e;
	border: none;
}

/* 💡 JavaScript ကင်းလွတ်ခွင့်ရစေရန် Custom CSS Hover Notification စတိုင် */
.custom-noti-dropdown {
	position: relative;
}

.custom-noti-dropdown:hover .custom-noti-menu {
	display: block !important;
	opacity: 1;
	visibility: visible;
	transform: translateY(0);
}

.custom-noti-menu {
	display: none;
	position: absolute;
	right: 0;
	top: 100%;
	z-index: 5000;
	width: 320px;
	max-height: 380px;
	overflow-y: auto;
	border-radius: 10px;
	margin-top: 5px;
}

.custom-noti-menu a:hover {
	background-color: #f8f9fa !important;
}
</style>
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-dark navbar-custom shadow-sm mb-4">
		<div class="container">
			<a class="navbar-brand fw-bold fs-4" href="home"> <i
				class="bi bi-file-earmark-code text-warning"></i> CheatSheet <span
				class="text-warning">Central</span>
			</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link px-3" href="home"><i
							class="fa-solid fa-house me-1"></i> Home</a></li>

					<%-- ၁။ 💡 Admin ရော User ရော နှလုံးသားတူတူ မြင်ရမည့် My Favorite အပိုင်း (Condition ကို ခွဲလိုက်ပါတယ်) --%>
					<c:if test="${not empty sessionScope.userId}">
						<li class="nav-item"><a class="nav-link px-3" href="my-notes">
								<i class="fa-solid fa-star text-warning me-1"></i> My Favorite
						</a></li>
					</c:if>

					<%-- ၂။ 🛡️ သာမန် User သီးသန့်ပဲ မြင်ရမည့် My Notebook အပိုင်း (Admin ကို ပိတ်ထားဆဲပါ) --%>
					<c:if
						test="${not empty sessionScope.userId and sessionScope.userRole ne 'Admin'}">
						<li class="nav-item"><a class="nav-link px-3"
							href="personal-notes"> <i
								class="fa-solid fa-book-open text-success me-1"></i> My Notebook
						</a></li>
					</c:if>

					<c:if
						test="${not empty sessionScope.adminUser && sessionScope.userRole == 'Admin'}">
						<li class="nav-item"><a class="nav-link px-3"
							href="manage-categories"> <i class="fa-solid fa-can me-1"></i>
								Manage Category
						</a></li>
					</c:if>
					<c:if test="${sessionScope.user.role == 'Admin'}">
						<li class="nav-item"><a class="nav-link fw-bold"
							href="userList"> <i class="fas fa-users-cog me-1"></i> User
								Management
						</a></li>
					</c:if>
				</ul>

				<form action="search" method="get"
					class="d-flex me-lg-3 my-2 my-lg-0">
					<input type="search" id="searchInput" name="query"
						class="form-control" placeholder="Search..."
						onkeyup="if(event.key === 'Enter') { window.location.href='search?query=' + this.value; }">
					<button class="btn btn-dark btn-sm search-btn" type="submit">
						<i class="bi bi-search"></i>
					</button>
				</form>

				<div class="navbar-nav align-items-center gap-2">

					<div class="nav-item dropdown custom-noti-dropdown me-2">
						<%-- 🛡️ Login ဝင်ထားတဲ့ User (user != null) ဖြစ်မှသာ ခေါင်းလောင်းကို ပြပါမယ် --%>
						<c:if test="${not empty sessionScope.user}">
							<button
								class="btn btn-sm position-relative rounded-circle border-0"
								type="button"
								style="width: 40px; height: 40px; background-color: #34495e;">
								<i class="fa-solid fa-bell text-warning fa-lg"></i>

								<%-- Unread Noti ရှိမှသာ အနီရောင်အဝိုင်းလေး ပြမယ့်နေရာ --%>
								<c:if test="${unreadNotiCount > 0}">
									<span
										class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
										style="font-size: 0.65rem;"> ${unreadNotiCount} </span>
								</c:if>
							</button>
						</c:if>
						<ul
							class="dropdown-menu dropdown-menu-end shadow border-0 p-0 custom-noti-menu">
							<c:if test="${sessionScope.role == 'Admin'}">

								<li
									class="p-3 text-white fw-bold d-flex justify-content-between align-items-center"
									style="background-color: #e67e22; border-radius: 10px 10px 0 0;">

									<span>Notifications</span> <span
									class="badge bg-white text-dark rounded-pill small">
										${unreadNotiCount} New </span>

								</li>

							</c:if>

							<c:forEach var="noti" items="${notiList}">
								<li><c:choose>
										<c:when test="${fn:contains(noti.message, 'New Cheat Sheet')}">
											<c:set var="targetUrl" value="sheets?subId=${noti.sheetId}" />
										</c:when>
										<c:otherwise>
											<c:set var="targetUrl" value="detail?id=${noti.sheetId}" />
										</c:otherwise>
									</c:choose> <a
									class="dropdown-item text-decoration-none d-block p-3 border-bottom text-dark"
									href="${targetUrl}"
									style="white-space: normal; background-color: #ffffff;">
										<div class="d-flex align-items-start gap-2">
											<c:choose>
												<c:when test="${fn:contains(noti.message, 'comments')}">
													<i class="fa-solid fa-comment text-info mt-1"></i>
												</c:when>
												<c:when test="${fn:contains(noti.message, 'Reply')}">
													<i class="fa-solid fa-reply text-success mt-1"></i>
												</c:when>
												<c:when test="${fn:contains(noti.message, 'star')}">
													<i class="fa-solid fa-star text-warning mt-1"></i>
												</c:when>
												<c:otherwise>
													<i class="fa-solid fa-file-circle-plus text-primary mt-1"></i>
												</c:otherwise>
											</c:choose>

											<div>
												<p class="mb-1 small text-dark fw-semibold"
													style="line-height: 1.4; font-size: 0.85rem; text-align: left;">
													${noti.message}</p>
												<small class="text-muted d-block text-start"
													style="font-size: 0.72rem;">${noti.createdAt}</small>
											</div>
										</div>
								</a></li>
							</c:forEach>

							<c:if test="${empty notiList}">
								<li class="p-4 text-center text-muted small bg-white"
									style="border-radius: 0 0 10px 10px;"><i
									class="fa-regular fa-bell-slash fa-2x mb-2 text-secondary opacity-50"></i>
									<p class="mb-0">No new notifications</p></li>
							</c:if>
						</ul>
					</div>

					<c:choose>
						<c:when
							test="${empty sessionScope.adminUser && empty sessionScope.userId}">
							<a class="nav-link me-2" href="login">LOGIN</a>
							<span class="text-secondary d-none d-lg-inline">or</span>
							<a class="btn btn-warning btn-register ms-lg-2" href="register">REGISTER</a>
						</c:when>
						<c:otherwise>
							<span class="text-light me-3 small d-none d-md-inline">
								Hi, <strong>${not empty adminUser ? adminUser : userName}</strong>
							</span>
							<a class="btn btn-sm btn-outline-danger rounded-pill px-3"
								href="logout">Logout</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</nav>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>