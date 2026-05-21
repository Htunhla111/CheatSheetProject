<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Technologies</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<style>
body {
	background-color: #f8f9fa;
}
/* Card Hover Effect */
.tech-card {
	transition: all 0.3s ease;
	border: none;
	border-radius: 15px;
}

.tech-card:hover {
	transform: translateY(-10px);
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1) !important;
}
/* Icon Styling */
.icon-box {
	font-size: 4rem;
	margin-bottom: 15px;
	transition: 0.3s;
}
.admin-actions {
		opacity: 0;
		visibility: hidden;
		transition: all 0.3s ease;
	}

	/* Card ပေါ် Mouse တင်လိုက်မှ ခလုတ်တွေ ညင်သာစွာ ပေါ်လာမယ် */
	.tech-card:hover .admin-actions {
		opacity: 1;
		visibility: visible;
	}

	/* Card ကို Hover လုပ်ရင် အပေါ်ကြွတက်တဲ့ animation */
	.tech-card {
		transition: transform 0.3s ease, box-shadow 0.3s ease;
		border: none !important;
	}

	.tech-card:hover {
		transform: translateY(-8px);
		box-shadow: 0 12px 24px rgba(0,0,0,0.15) !important;
	}
/* Technology Icons Color Logic */
.fa-java { color: #f89820; }
.fa-python { color: #3776ab; }
.fa-js { color: #f7df1e; }
.fa-react { color: #61dafb; }
.fa-php { color: #777bb4; }
.fa-hashtag { color: #68217a; } /* C# */
.fa-node-js { color: #339933; }
.fa-html5 { color: #e34f26; }
.fa-css3-alt { color: #1572b6; }

/* Header Buttons Gap */
.header-actions {
	display: flex;
	gap: 10px;
}
</style>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container py-5">
		<div class="d-flex justify-content-between align-items-center mb-5">
			<h2 class="fw-bold text-dark m-0">
				<i class="fas fa-layer-group me-2"></i>Available Technologies
			</h2>

			<div class="header-actions">
				<%-- Admin Role ရှိမှသာ Add New Technology ခလုတ် ပေါ်မည် --%>
				<c:if test="${sessionScope.user.role == 'Admin'}">
					<a href="add-sub-category.jsp?catId=${param.catId}"
						class="btn btn-primary shadow-sm px-4"> <i
						class="fas fa-plus-circle me-1"></i> Add New Technology
					</a>
				</c:if>

				<a href="home" class="btn btn-outline-secondary shadow-sm px-4">
					<i class="fas fa-arrow-left me-1"></i> Back to Home
				</a>
			</div>
		</div>

		<div class="row g-4">
	<c:forEach items="${subList}" var="sub">
		<div class="col-md-3">
			<div class="card tech-card h-100 shadow-sm text-center p-4 d-flex flex-column justify-content-between position-relative">
				
				<!-- နည်းပညာအသေးစိတ်ကြည့်ရန် Link -->
				<a href="sheets?subId=${sub.id}" class="text-decoration-none text-dark flex-grow-1 mb-3">
					<div class="icon-box mb-3">
						<i class="${sub.icon}"></i>
					</div>
					<h4 class="fw-bold text-dark mb-2">${sub.name}</h4>
					<div class="text-muted small">Click to view cheat sheets</div>
				</a>

				<%-- Admin Control: Mouse တင်မှ ပေါ်လာမည့် Edit နှင့် Delete ခလုတ်များ --%>
				<c:if test="${sessionScope.user.role == 'Admin'}">
					<div class="admin-actions border-top pt-3 d-flex gap-2">
						<!-- Edit -->
						<a href="manage-tech?action=edit&id=${sub.id}&catId=${param.catId}" 
						   class="btn btn-sm btn-outline-warning w-50 rounded-pill">
							<i class="fas fa-edit me-1"></i>Edit
						</a>
						
						<!-- Delete -->
						<a href="manage-tech?action=delete&id=${sub.id}&catId=${param.catId}" 
						   class="btn btn-sm btn-outline-danger w-50 rounded-pill"
						   onclick="return confirm('${sub.name} ကို ဖျက်ရန် သေချာပါသလား?')">
							<i class="fas fa-trash me-1"></i>Delete
						</a>
					</div>
				</c:if>

			</div>
		</div>
	</c:forEach>
</div>

		<c:if test="${empty subList}">
			<div class="text-center mt-5 text-muted">
				<i class="fas fa-info-circle fa-2x mb-3"></i>
				<p>No technologies found.</p>
			</div>
		</c:if>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>