<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>My Saved Notes</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
	<%@ include file="navbar.jsp"%>

	<div class="container mt-5">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2 class="fw-bold text-dark m-0">
				<i class="fas fa-bookmark text-primary me-2"></i> My Favorite Notes
			</h2>
			<button onclick="history.back()"
				class="btn btn-outline-secondary rounded-pill px-4 shadow-sm fw-bold">
				<i class="fas fa-arrow-left me-2"></i> Go Back
			</button>
		</div>
		<hr>

		<c:if test="${not empty sessionScope.errorMsg}">
			<div class="alert alert-danger">${sessionScope.errorMsg}</div>
			<c:remove var="errorMsg" scope="session" />
		</c:if>

		<c:if test="${not empty sessionScope.succMsg}">
			<div class="alert alert-success">${sessionScope.succMsg}</div>
			<c:remove var="succMsg" scope="session" />
		</c:if>

		<div class="row">
			<c:forEach var="note" items="${noteList}">
				<div class="col-md-4 mb-4">
					<div class="card shadow-sm h-100 border-0 rounded-3">
						<div class="card-body d-flex flex-column">
							<h5 class="card-title fw-bold text-dark mb-1">${note.sheetTitle}</h5>
							<p class="text-muted small mb-3">Saved on: ${note.savedAt}</p>

							<p
								class="card-text text-secondary bg-light p-3 rounded-3 flex-grow-1 text-wrap"
								style="min-height: 80px;">${note.personalRemark}</p>

							<div class="d-flex justify-content-between align-items-center mt-3 pt-2 border-top">
	<a href="detail?id=${note.cheatsheetId}" class="btn btn-sm btn-primary rounded-pill px-3"> 
		<i class="fa-solid fa-eye me-1"></i> View
	</a>

	<div class="btn-group">
		<button type="button" class="btn btn-sm btn-outline-warning rounded-pill px-3 me-2"
			onclick="openEditModal('${note.id}', '${note.personalRemark}')">
			<i class="fas fa-edit me-1"></i> Edit
		</button>

		<a href="delete-note?noteId=${note.id}"
			class="btn btn-sm btn-outline-danger fw-bold rounded-pill px-3"
			onclick="return confirm('Are you sure you want to delete this note?');">
			<i class="fas fa-trash me-1"></i> Cancel
		</a>
	</div>
</div>
						</div>
					</div>
				</div>
			</c:forEach>

			<c:if test="${empty noteList}">
				<div class="col-12">
					<div class="alert alert-info py-4 text-center">
						<i class="fa-solid fa-folder-open display-6 mb-2 d-block"></i> You
						haven't saved any notes yet.
					</div>
				</div>
			</c:if>
		</div>
	</div>

	<div class="modal fade" id="editNoteModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow-lg rounded-3">
				<div class="modal-header border-0 bg-light">
					<h5 class="modal-title fw-bold">
						<i class="fas fa-edit text-warning me-2"></i>Edit Personal Note
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<form action="my-notes" method="post">
					<div class="modal-body py-4">
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="id" id="modalNoteId">

						<div class="mb-3">
							<label class="form-label text-muted small fw-semibold">Your
								Remark</label>
							<textarea class="form-control rounded-3" name="personalRemark"
								id="modalRemark" rows="5" required
								placeholder="Modify your note here..."></textarea>
						</div>
					</div>
					<div class="modal-footer border-0 bg-light">
						<button type="button" class="btn btn-secondary rounded-pill px-4"
							data-bs-dismiss="modal">Cancel</button>
						<button type="submit" class="btn btn-primary rounded-pill px-4">Save
							Changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		// Edit ခလုတ်နှိပ်လိုက်တာနဲ့ သက်ဆိုင်ရာ ဒေတာတွေကို Pop-up Form ထဲ ထည့်ပေးပြီး ဖွင့်ပေးမည့် Function
		function openEditModal(id, remark) {
			document.getElementById('modalNoteId').value = id;
			document.getElementById('modalRemark').value = remark;
			var myModal = new bootstrap.Modal(document
					.getElementById('editNoteModal'));
			myModal.show();
		}
	</script>
</body>
</html>