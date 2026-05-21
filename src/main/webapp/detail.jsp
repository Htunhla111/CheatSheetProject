<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<meta charset="UTF-8">
<title>${sheet.title}-Details</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

pre {
	background-color: #f8f9fa;
	padding: 15px;
	border-radius: 5px;
	border: 1px solid #dee2e6;
	font-family: 'Courier New', Courier, monospace;
}
/* Admin Actions Hover Effect */
.admin-actions {
	opacity: 0;
	visibility: hidden;
	transition: all 0.3s ease;
}

.parent-comment-card:hover .admin-actions {
	opacity: 1;
	visibility: visible;
}
/* Discussion Toggle Button Style */
.discussion-toggle-btn {
	background-color: #ffffff;
	border: 1px solid #dee2e6;
	color: #212529;
	font-weight: 600;
	padding: 12px 24px;
	border-radius: 30px;
	transition: all 0.3s ease;
}

.discussion-toggle-btn:hover {
	background-color: #f8f9fa;
	border-color: #0d6efd;
	color: #0d6efd;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.discussion-toggle-btn[aria-expanded="true"] {
	background-color: #e7f1ff;
	color: #0d6efd;
	border-color: #b6d4fe;
}
/* 💡 Rating Star System Style */
.rating-form-stars {
	direction: rtl;
	display: inline-flex;
}

.rating-form-stars input {
	display: none;
}

.rating-form-stars label {
	color: #ddd;
	font-size: 1.4rem;
	padding: 0 2px;
	cursor: pointer;
	transition: color 0.2s ease;
}

.rating-form-stars label:hover, .rating-form-stars label:hover ~ label,
	.rating-form-stars input:checked ~ label {
	color: #ffc107;
}
</style>
</head>
<body class="bg-light">

	<%-- Navbar include --%>
	<%@ include file="navbar.jsp"%>

	<div class="container mt-5 mb-5">
		<div class="row justify-content-center">
			<div class="col-md-10">

				<div class="card shadow border-0 mb-4">
					<div class="card-header bg-primary text-white p-3"
						style="background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);">
						<div
							class="d-flex justify-content-between align-items-center flex-wrap gap-2">
							<div>
								<h3 class="mb-1 fw-bold">${sheet.title}</h3>

								<div class="d-flex align-items-center gap-2"
									style="font-size: 0.95rem;">
									<span class="text-warning"> <c:forEach begin="1" end="5"
											var="i">
											<c:choose>
												<c:when test="${i <= avgRating}">
													<i class="bi bi-star-fill"></i>
												</c:when>
												<c:when test="${i - 0.5 <= avgRating}">
													<i class="bi bi-star-half"></i>
												</c:when>
												<c:otherwise>
													<i class="bi bi-star"></i>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</span> <span class="small opacity-90">(Avg:
										${String.format("%.1f", avgRating)} / 5.0)</span>
								</div>
							</div>

							<div class="d-flex align-items-center gap-3">
								<c:if test="${not empty sessionScope.user && !hasRated}">
									<div
										class="bg-white text-dark px-3 py-1 rounded-pill shadow-sm d-flex align-items-center gap-2">
										<span class="small fw-semibold text-secondary">Rate
											this:</span>
										<form action="add-rating" method="post" id="ratingForm"
											class="rating-form-stars">
											<input type="hidden" name="sheetId" value="${sheet.id}">

											<input type="radio" name="ratingValue" id="star5" value="5"
												onclick="document.getElementById('ratingForm').submit();"><label
												for="star5" class="bi bi-star-fill"></label> <input
												type="radio" name="ratingValue" id="star4" value="4"
												onclick="document.getElementById('ratingForm').submit();"><label
												for="star4" class="bi bi-star-fill"></label> <input
												type="radio" name="ratingValue" id="star3" value="3"
												onclick="document.getElementById('ratingForm').submit();"><label
												for="star3" class="bi bi-star-fill"></label> <input
												type="radio" name="ratingValue" id="star2" value="2"
												onclick="document.getElementById('ratingForm').submit();"><label
												for="star2" class="bi bi-star-fill"></label> <input
												type="radio" name="ratingValue" id="star1" value="1"
												onclick="document.getElementById('ratingForm').submit();"><label
												for="star1" class="bi bi-star-fill"></label>
										</form>
									</div>
								</c:if>

								<c:if test="${hasRated}">
									<span
										class="badge bg-light text-success border border-success-subtle rounded-pill px-3 py-2 small">
										<i class="bi bi-check-circle-fill me-1"></i> Rated! Thanks
									</span>
								</c:if>

								<button onclick="copyCode()"
									class="btn btn-sm btn-light rounded-pill px-3">
									<i class="bi bi-clipboard"></i> Copy Code
								</button>
							</div>
						</div>
					</div>

					<div class="card-body p-4">
						<h5 class="text-muted mb-3">Cheat Sheet Content:</h5>
						<pre id="codeContent" style="white-space: pre-wrap;">${sheet.content}</pre>
					</div>

					<div class="card-footer bg-white py-3">
						<div class="d-flex flex-wrap align-items-center gap-2">

							<a href="sheets?subId=${sheet.subCategoryId}"
								class="btn btn-outline-secondary rounded-pill px-4"> <i
								class="fas fa-arrow-left me-1"></i> Go Back
							</a> <a href="add-note?sheetId=${sheet.id}"
								class="btn btn-warning text-dark fw-semibold rounded-pill px-4 d-inline-flex align-items-center shadow-sm">
								<i class="bi bi-star-fill me-2"></i> Add to Favorite
							</a>

							<c:if test="${not empty sessionScope.userId}">
								<button type="button"
									class="btn btn-success rounded-pill px-4 d-inline-flex align-items-center shadow-sm"
									data-bs-toggle="modal" data-bs-target="#saveNoteModal">
									<i class="fa-solid fa-pen-to-square me-2"></i> Take a Note
								</button>
							</c:if>
							<c:if test="${empty sessionScope.userId}">
								<a href="login"
									class="btn btn-success rounded-pill px-4 d-inline-flex align-items-center shadow-sm">
									<i class="fa-solid fa-pen-to-square me-2"></i> Take a Note
								</a>
							</c:if>

							<c:if test="${sessionScope.user.role eq 'Admin'}">
								<div class="ms-auto d-flex gap-2">
									<a href="edit-sheet?id=${sheet.id}"
										class="btn btn-warning rounded-pill px-4"> <i
										class="bi bi-pencil me-1"></i> Edit
									</a> <a href="delete-sheet?id=${sheet.id}"
										class="btn btn-danger rounded-pill px-4"
										onclick="return confirm('Are you sure you want to delete this?')">
										<i class="bi bi-trash me-1"></i> Delete
									</a>
								</div>
							</c:if>
						</div>
					</div>
				</div>

				<div class="text-center mb-4">
					<button class="btn discussion-toggle-btn shadow-sm" type="button"
						data-bs-toggle="collapse" data-bs-target="#discussionSection"
						aria-expanded="false" aria-controls="discussionSection">
						<i class="fa-regular fa-comments me-2 text-primary"></i>
						Discussions (${fn:length(commentList)}) <i
							class="fa-solid fa-chevron-down ms-2 small opacity-70"></i>
					</button>
				</div>

				<div class="collapse" id="discussionSection">
					<div class="card shadow border-0 p-4 bg-white mb-5"
						style="border-radius: 12px;">

						<c:if test="${not empty sessionScope.user}">
							<div class="card border-0 bg-light p-3 mb-4"
								style="border-radius: 10px;">
								<form action="add-comment" method="post">
									<input type="hidden" name="sheetId" value="${sheet.id}">
									<div class="mb-3">
										<label class="form-label fw-semibold text-secondary small">Leave
											a comment</label>
										<textarea name="content"
											class="form-control border-0 shadow-sm" rows="3"
											placeholder="မရှင်းလင်းသည်များကို ဤနေရာတွင် မေးမြန်းနိုင်ပါသည်..."
											style="border-radius: 8px;" required></textarea>
									</div>
									<div class="text-end">
										<button type="submit"
											class="btn btn-sm text-white px-4 shadow-sm"
											style="background-color: #ff8c00; border-radius: 20px;">
											<i class="fa-regular fa-paper-plane me-1"></i>Post Comment
										</button>
									</div>
								</form>
							</div>
						</c:if>
						<c:if test="${empty sessionScope.user}">
							<div
								class="alert alert-warning text-center border-0 small shadow-sm mb-4"
								style="border-radius: 10px;">
								Please write comment or ask..<a href="login.jsp"
									class="fw-bold text-decoration-none">Please Login</a>
							</div>
						</c:if>

						<div class="comment-list">
							<c:forEach var="comment" items="${commentList}">

								<c:if test="${empty comment.parentCommentId}">
									<div
										class="parent-comment-card border border-light shadow-sm p-3 mb-3 bg-white"
										style="border-radius: 10px;">

										<div
											class="d-flex justify-content-between align-items-center mb-2">
											<span class="fw-bold text-dark text-capitalize"
												style="font-size: 0.95rem;"> <i
												class="fa-solid fa-user-circle me-1 text-secondary"></i>
												${comment.username} <c:if
													test="${comment.userRole eq 'Admin'}">
													<span class="badge bg-danger ms-1"
														style="font-size: 0.65rem; padding: 3px 6px;">Admin</span>
												</c:if>
											</span> <small class="text-muted" style="font-size: 0.8rem;">${comment.createdAt}</small>
										</div>
										<p class="mb-2 text-secondary px-1"
											style="font-size: 0.95rem; white-space: pre-wrap;">${comment.content}</p>

										<div
											class="d-flex justify-content-end align-items-center gap-3 admin-actions">
											<c:if test="${sessionScope.user.role eq 'Admin'}">
												<button
													class="btn btn-sm btn-link text-decoration-none p-0 text-primary small"
													type="button" data-bs-toggle="collapse"
													data-bs-target="#replyForm-${comment.id}">
													<i class="fa-solid fa-reply me-1"></i>Reply
												</button>

												<c:if test="${comment.userRole ne 'Admin'}">
													<button
														class="btn btn-sm btn-link text-decoration-none p-0 text-danger small"
														type="button" data-bs-toggle="modal"
														data-bs-target="#deleteCommentModal"
														onclick="setDeleteData('${comment.id}', '${sheet.id}')">
														<i class="fa-solid fa-trash-can me-1"></i>Delete Comment
													</button>
												</c:if>
											</c:if>
										</div>

										<%-- Admin Reply Form --%>
										<c:if test="${sessionScope.user.role eq 'Admin'}">
											<div class="collapse mt-2" id="replyForm-${comment.id}">
												<div class="card card-body border-0 bg-light p-3"
													style="border-radius: 8px;">
												<form action="add-comment" method="post">
														<input type="hidden" name="sheetId" value="${sheet.id}">
														<input type="hidden" name="parentCommentId"
															value="${comment.id}">
														<div class="mb-2">
															<textarea name="content"
																class="form-control form-control-sm border-0 shadow-sm"
																rows="2" placeholder="Write reasons..." required></textarea>
														</div>
														<div class="text-end">
															<button type="submit"
																class="btn btn-sm btn-primary px-3 rounded-pill">Submit
																Reply</button>
														</div>
													</form>
												</div>
											</div>
										</c:if>
									</div>

									<%--Replies --%>
									<c:forEach var="reply" items="${commentList}">
										<c:if test="${reply.parentCommentId eq comment.id}">
											<div
												class="card border-0 border-start border-3 border-warning p-3 mb-3 ms-5 bg-light shadow-sm"
												style="border-radius: 0 10px 10px 0;">
												<div
													class="d-flex justify-content-between align-items-center mb-1">
													<span class="fw-bold text-dark text-capitalize"
														style="font-size: 0.9rem;"> <i
														class="fa-solid fa-reply fa-flip-horizontal me-1 text-warning"></i>
														${reply.username} <c:if
															test="${reply.userRole eq 'Admin'}">
															<span class="badge bg-danger ms-1"
																style="font-size: 0.66rem; padding: 2px 5px;">Admin</span>
														</c:if>
													</span> <small class="text-muted" style="font-size: 0.75rem;">${reply.createdAt}</small>
												</div>
												<p class="mb-0 text-secondary px-1"
													style="font-size: 0.9rem; white-space: pre-wrap;">${reply.content}</p>
											</div>
										</c:if>
									</c:forEach>

								</c:if>
							</c:forEach>

							<c:if test="${empty commentList}">
								<div class="text-center py-4 text-muted">
									<i
										class="fa-regular fa-comment-dots fa-2x mb-2 text-secondary opacity-50"></i>
									<p class="small mb-0">No comments yet. Be the first to ask!</p>
								</div>
							</c:if>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 🛠️ TAKE A NOTE (MODAL POP-UP BOX) --%>
	<div class="modal fade" id="saveNoteModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow"
				style="border-radius: 12px;">
				<div class="modal-header bg-success text-white">
					<h5 class="modal-title fw-bold">
						<i class="fa-solid fa-pen-to-square me-2"></i>Create Personal Note
					</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Close"
						style="box-shadow: none;"></button>
				</div>
				<form action="personal-notes" method="post">
					<div class="modal-body p-4">
						<div class="mb-3">
							<label class="form-label fw-semibold text-secondary">Note
								Title</label> <input type="text" name="title" class="form-control"
								value="My Notes on ${sheet.title}" required
								style="border-radius: 6px;">
						</div>
						<div class="mb-3">
							<label class="form-label fw-semibold text-secondary">Note
								Content (Write personal notes..)</label>
							<textarea name="content" class="form-control" rows="6"
								placeholder="Write note on this cheat as you like..." required
								style="border-radius: 6px;"></textarea>
						</div>
					</div>
					<div class="modal-footer bg-light border-0">
						<button type="button"
							class="btn btn-sm btn-secondary rounded-pill px-3"
							data-bs-dismiss="modal">Close</button>
						<button type="submit"
							class="btn btn-sm btn-success rounded-pill px-4">Save
							Note</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="deleteCommentModal" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow"
				style="border-radius: 12px;">
				<div class="modal-header bg-danger text-white">
					<h5 class="modal-title fw-bold">
						<i class="fa-solid fa-trash-can me-2"></i>Delete Comment
					</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Close"
						style="box-shadow: none;"></button>
				</div>
				<form action="delete-comment" method="post">
					<div class="modal-body p-4 text-center">
						<input type="hidden" name="commentId" id="modalDeleteCommentId">
						<input type="hidden" name="sheetId" id="modalDeleteSheetId">

						<i class="fa-solid fa-triangle-exclamation text-danger fa-3x mb-3"></i>
						<p class="mb-0 fw-semibold text-secondary" style="font-size: 1.05rem;">
							Are you sure you want to delete this comment?
						</p>
						<small class="text-muted">This action cannot be undone.</small>
					</div>
					<div class="modal-footer bg-light border-0 justify-content-center">
						<button type="button"
							class="btn btn-sm btn-secondary rounded-pill px-4"
							data-bs-dismiss="modal">Cancel</button>
						<button type="submit"
							class="btn btn-sm btn-danger rounded-pill px-4">Delete</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		function copyCode() {
			var content = document.getElementById("codeContent").innerText;
			navigator.clipboard.writeText(content).then(function() {
				alert("Code copied to clipboard!");
			}, function(err) {
				console.error('Could not copy text: ', err);
			});
		}

		// ✅ ပြောင်းလဲမှု - Delete လုပ်မည့် ID များ လှမ်းထည့်ပေးသည့် ဂျာဗားစခရစ် Function
		function setDeleteData(commentId, sheetId) {
			document.getElementById('modalDeleteCommentId').value = commentId;
			document.getElementById('modalDeleteSheetId').value = sheetId;
		}
	</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>