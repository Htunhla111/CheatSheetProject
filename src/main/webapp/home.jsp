<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Cheatography Clone</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Header Title Style */
.hero-section {
	padding: 60px 0;
	background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
	margin-bottom: 40px;
	border-radius: 0 0 50% 50%/10%;
}

/* Category Card Style */
.category-card {
	background-color: white;
	padding: 40px 20px;
	text-align: center;
	border-radius: 16px;
	margin-bottom: 30px;
	cursor: pointer;
	transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
	border: 1px solid rgba(0, 0, 0, 0.05);
	height: 100%;
}

/* Card Hover Logic */
.category-card:hover {
	transform: translateY(-10px);
	box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1) !important;
	background-color: #ff8c00;
	color: white;
}

.category-card i {
	font-size: 50px;
	margin-bottom: 20px;
	color: #ff8c00;
	transition: 0.3s;
}

.category-card:hover i {
	color: white;
	transform: scale(1.1);
}

.category-card h4 {
	font-weight: 700;
	margin-bottom: 10px;
}

.category-card p {
	opacity: 0.8;
	font-size: 0.9rem;
	margin-bottom: 0;
}

/* Link reset */
.card-link {
	text-decoration: none;
	color: inherit;
}
</style>
</head>

<body class="bg-light">
	<%@ include file="navbar.jsp"%>

	<div class="hero-section text-center shadow-sm">
		<div class="container">
			<h1 class="display-3 fw-bold">CheatSheet Central</h1>
			<p class="lead text-muted">Over 6,000 Free Cheat Sheets, Revision
				Aids and Quick References!</p>
		</div>
	</div>

	<div class="container mt-2">
		<div class="row g-4">
			<c:forEach var="cat" items="${categories}">
				<div class="col-lg-4 col-md-6">
					<a href="sub-categories?catId=${cat.id}" class="card-link">
						<div class="category-card shadow-sm">
							<!-- fa ဆိုတာကို ဖြုတ်ပြီး variable တစ်ခုတည်းပဲ ထားကြည့်ပါ -->
							<i class="${cat.icon} fa-3x"></i>
							<h4>${cat.name}</h4>
							<p>${cat.name}Commands & Tips</p>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>

	<footer class="text-center py-5 mt-5 text-muted">
		<p>&copy; 2026 CheatSheet Project Presentation</p>
		</div>
</body>
</html>