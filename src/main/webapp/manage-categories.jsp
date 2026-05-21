<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Manage Categories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body { background-color: #f8f9fa; }
        .card { border: none; border-radius: 12px; }
        .table th { font-weight: 600; background-color: #f1f3f5; }
        .cat-icon-preview {
            width: 42px; height: 42px; display: flex; align-items: center;
            justify-content: center; border-radius: 50%; background: #eef2ff;
            color: #4f46e5; font-size: 1.1rem; transition: all 0.2s ease;
        }
        .table tr:hover .cat-icon-preview { background: #4f46e5; color: #ffffff; transform: scale(1.05); }
        .btn-action { border-radius: 20px; padding: 5px 15px; font-weight: 500; transition: all 0.2s ease; }
        .btn-action:hover { transform: translateY(-1px); }
        .alert { border-radius: 10px; border: none; }
    </style>
</head>
<body>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            
            <%-- Session Messages --%>
            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="alert alert-danger alert-dismissible fade show shadow-sm mb-4" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i> ${sessionScope.errorMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="errorMsg" scope="session" />
            </c:if>
            
            <c:if test="${not empty sessionScope.succMsg}">
                <div class="alert alert-success alert-dismissible fade show shadow-sm mb-4" role="alert">
                    <i class="fas fa-check-circle me-2"></i> ${sessionScope.succMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="succMsg" scope="session" />
            </c:if>
            
            <div class="card shadow-sm">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center border-bottom">
                    <h5 class="mb-0 text-dark fw-bold">
                        <i class="fa-solid fa-layer-group text-primary me-2"></i>Category Management
                    </h5>
                    
                    <div class="d-flex align-items-center gap-2">
                        <a href="home" class="btn btn-sm btn-secondary rounded-pill px-3 shadow-sm fw-bold">
                            <i class="fa-solid fa-arrow-left me-1"></i> Back to Home
                        </a>
                        <a href="add-category" class="btn btn-sm btn-primary rounded-pill px-3 shadow-sm fw-bold">
                            <i class="fas fa-plus me-1"></i> Add New Category
                        </a>
                    </div>
                </div>
                
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead>
                                <tr>
                                    <th class="ps-4 py-3" style="width: 100px;"># No</th>
                                    <th>Category Details</th>
                                    <th class="text-center" style="width: 150px;">Display Icon</th>
                                    <th class="text-end pe-4" style="width: 150px;">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cat" items="${categoryList}" varStatus="status">
                                    <tr>
                                        <td class="ps-4 text-muted fw-bold"># ${status.count}</td>
                                        <td>
                                            <div class="fw-bold text-dark fs-6">${cat.name}</div>
                                            <small class="text-muted">
                                                <i class="fa-solid fa-box-open me-1"></i>Total items: ${cat.totalItems}
                                            </small>
                                        </td>
                                        <td>
                                            <div class="cat-icon-preview mx-auto shadow-sm">
                                                <i class="${cat.icon}"></i>
                                            </div>
                                        </td>
                                        <td class="text-end pe-4">
                                            <%-- 💡 ဖျက်ပြီးရင် လက်ရှိ စာမျက်နှာ (manage-categories) ဆီ ပြန်လာဖို့ Servlet ဘက်မှာ လုပ်ရပါမယ် --%>
                                            <a href="delete-category?id=${cat.id}"
                                               class="btn btn-sm btn-outline-danger btn-action shadow-sm"
                                               onclick="return confirm('Are you sure delete this category?')">
                                                <i class="fa-solid fa-trash-can me-1"></i> Delete
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                <c:if test="${empty categoryList}">
                                    <tr>
                                        <td colspan="4" class="text-center py-5 text-muted">
                                            <i class="fa-solid fa-folder-open fa-3x mb-3 d-block opacity-25"></i>
                                            No categories found.
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div> 
            
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>