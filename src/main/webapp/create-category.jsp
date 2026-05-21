<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Category</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .card { border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .form-label { font-weight: bold; }
    </style>
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card p-4">
                <div class="text-center mb-4">
                    <div class="bg-primary text-white rounded-circle d-inline-block p-3 mb-2">
                        <i class="fas fa-folder-plus fa-2x"></i>
                    </div>
                    <h2 class="fw-bold">Create New Category</h2>
                    <p class="text-muted">Organize your cheat sheets by adding a new section</p>
                </div>

                <!-- Form Action ကို မင်းရဲ့ SaveCategoryServlet URL နဲ့ ကိုက်အောင် ပြင်ပါ -->
                <form action="save-category" method="post">
                    
                    <!-- Category Name -->
                    <div class="mb-4">
                        <label for="categoryName" class="form-label">Category Name</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-tag"></i></span>
                            <input type="text" name="categoryName" id="categoryName" 
                                   class="form-control" placeholder="e.g., Programming, Angular" required>
                        </div>
                    </div>

                    <!-- Icon Selection Dropdown -->
                    <div class="mb-4">
                        <label for="iconClass" class="form-label">Select Icon</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-icons"></i></span>
                            <select name="iconClass" id="iconClass" class="form-select" required>
                                <option value="" disabled selected>Choose an icon...</option>
                                
                                <!-- Servlet က ပို့လိုက်တဲ့ icons list ကို loop ပတ်ပြမယ် -->
                                <c:forEach var="icon" items="${icons}">
                                    <option value="${icon.iconClass}">
                                        ${icon.displayName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <small class="text-muted mt-2 d-block">
                            Icons help users identify categories quickly.
                        </small>
                    </div>

                    <!-- Action Buttons -->
                    <div class="d-flex gap-2">
                        <a href="manage-categories" class="btn btn-outline-secondary w-100">Cancel</a>
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="fas fa-save me-2"></i> Save Category
                        </button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>