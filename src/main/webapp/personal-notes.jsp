<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Personal Notes</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
.note-card {
	background-color: #fff;
	border-radius: 12px;
	border-left: 5px solid #e67e22; /* လှပသော လိမ္မော်ရောင် Border ဘေးလိုင်း */
	transition: all 0.3s ease;
}
.note-card:hover {
	transform: translateY(-3px);
	box-shadow: 0 10px 20px rgba(0,0,0,0.08) !important;
}
.note-content-box {
	background-color: #f8f9fa;
	font-family: 'Courier New', Courier, monospace;
	font-size: 0.9rem;
	white-space: pre-wrap;
	border-radius: 6px;
	padding: 12px;
	max-height: 200px;
	overflow-y: auto;
	border: 1px solid #e9ecef;
}
</style>
</head>
<body class="bg-light">

	<%@ include file="navbar.jsp"%>

	<div class="container mt-4 mb-5">
		<div class="row g-4">
			
			<div class="col-lg-4">
				<div class="card shadow-sm border-0 p-4 sticky-top" style="top: 20px; border-radius: 16px;">
					<h4 class="fw-bold mb-3 text-dark">
						<i class="fa-solid fa-pen-to-square text-warning me-2"></i>Create New Note
					</h4>
					<hr class="text-muted opacity-20">
					
					<form action="personal-notes" method="post">
						<div class="mb-3">
							<label class="form-label fw-semibold text-secondary">Note Title</label>
							<input type="text" name="title" class="form-control shadow-sm" placeholder="ဥပမာ - Java Loop အကြောင်း" required style="border-radius: 8px;">
						</div>
						
						<div class="mb-3">
							<label class="form-label fw-semibold text-secondary">Content</label>
							<textarea name="content" class="form-control shadow-sm" rows="8" placeholder="မှတ်သားလိုသည်များကို ဤနေရာတွင် ရေးချနိုင်ပါသည်..." required style="border-radius: 8px;"></textarea>
						</div>
						
						<button type="submit" class="btn text-white w-100 fw-bold shadow-sm py-2" style="background-color: #e67e22; border-radius: 25px;">
							<i class="fa-solid fa-plus me-2"></i>Add to My Notes
						</button>
					</form>
				</div>
			</div>
			
			<div class="col-lg-8">
				<div class="d-flex justify-content-between align-items-center mb-4">
					<h3 class="fw-bold text-dark mb-0">My Notebook</h3>
					<span class="badge bg-dark rounded-pill px-3 py-2 fs-6 shadow-sm">${userNotes.size()} Notes</span>
				</div>
				
				<div class="row g-3">
					<c:forEach var="note" items="${userNotes}">
						<div class="col-12">
							<div class="note-card card shadow-sm p-4 border-0">
								<div class="d-flex justify-content-between align-items-start mb-2">
									<h5 class="fw-bold text-dark mb-1"><i class="fa-regular fa-file-lines me-2 text-secondary"></i>${note.title}</h5>
									<small class="text-muted"><i class="fa-regular fa-clock me-1"></i>${note.createdAt}</small>
								</div>
								<hr class="my-2 opacity-10">
								<div class="note-content-box text-secondary mt-2">${note.content}</div>
							</div>
						</div>
					</c:forEach>
					
					<c:if test="${empty userNotes}">
						<div class="col-12 text-center py-5 bg-white shadow-sm rounded-4 mt-2">
							<i class="fa-regular fa-folder-open fa-3x mb-3 text-secondary opacity-40"></i>
							<h5 class="text-muted fw-semibold">No personal notes found</h5>
							<p class="text-muted small mb-0"></p>
						</div>
					</c:if>
				</div>
			</div>
			
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>