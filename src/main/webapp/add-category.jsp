<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Category - CheatSheet Central</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<style>
    body {
        background-color: #f8f9fa; /* Light grey background */
    }
    .card {
        border: none;
        border-radius: 15px;
    }
    .btn-save {
        background-color: #007bff;
        border: none;
        padding: 10px 25px;
        font-weight: bold;
    }
    .btn-save:hover {
        background-color: #0056b3;
    }
    .form-label {
        font-weight: 600;
        color: #495057;
    }
    .input-group-text {
        background-color: #e9ecef;
        border-right: none;
    }
    .form-control {
        border-left: none;
    }
    .form-control:focus {
        box-shadow: none;
        border-color: #ced4da;
    }
</style>
</head>
<body>

<%-- <jsp:include page="navbar.jsp" /> --%>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-lg p-4">
                <div class="text-center mb-4">
                    <i class="fa-solid fa-folder-plus fa-3x text-primary mb-2"></i>
                    <h3 class="fw-bold">Create New Category</h3>
                    <p class="text-muted">Organize your cheat sheets by adding a new section</p>
                </div>
                
                <form action="add-category" method="post" class="p-4 bg-white shadow rounded">
    <h3 class="mb-4 text-primary fw-bold">Add New Category</h3>

    <div class="mb-3">
        <label class="form-label fw-bold">Category Name</label>
        <input type="text" name="categoryName" class="form-control" placeholder="e.g. Basics, Advanced" required>
    </div>

    <div class="mb-4">
        <div class="d-flex justify-content-between align-items-center mb-2">
            <label class="form-label fw-bold mb-0">Category Icon Code</label>
            <a href="https://fontawesome.com/search?o=r&m=free" target="_blank" class="btn btn-sm btn-link text-decoration-none p-0">
                <i class="fas fa-external-link-alt me-1"></i>Find Icons
            </a>
        </div>
        
        <input type="text" name="iconClass" class="form-control" placeholder="e.g. fab fa-linux, fas fa-code" required>
        <div class="form-text text-muted">FontAwesome က ကူးလာတဲ့ class code ကို ဒီထဲ ထည့်ပေးပါ။</div>
    </div>

    <div class="d-flex justify-content-end gap-2">
        <a href="home" class="btn btn-outline-secondary">Cancel</a>
        <button type="submit" class="btn btn-primary px-4">Save Category</button>
    </div>
</form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>