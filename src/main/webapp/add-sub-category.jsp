<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Add New Technology</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card shadow border-0">
					<div class="card-body p-5">
						<h3 class="mb-4 fw-bold text-center">Add New Technology</h3>

						<form action="add-sub-category" method="post">
							<!-- Category ID ကို hidden အနေနဲ့ သိမ်းထားမယ် -->
							<%
							String cId = request.getParameter("catId");
							%>
							<input type="hidden" name="catId" value="<%=cId%>">
							<div class="mb-3">
								<label class="form-label">Technology Name</label> <input
									type="text" name="name" class="form-control"
									placeholder="e.g. Node.js" required>
							</div>

							<div class="mb-4">
								<label class="form-label">FontAwesome Icon Class</label> <input
									type="text" name="icon" class="form-control"
									placeholder="e.g. fa-brands fa-node-js" required>
								<div class="form-text mt-2">
									For logos <a href="https://fontawesome.com/icons"
										target="_blank">FontAwesome</a> class use.
								</div>
							</div>

							<div class="d-grid gap-2">
								<button type="submit" class="btn btn-primary btn-lg">Save
									Technology</button>
								<a href="javascript:history.back()"
									class="btn btn-link text-muted">Cancel</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>