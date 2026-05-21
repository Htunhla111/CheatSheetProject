<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - User Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            border: none;
            border-radius: 12px;
        }
        .table th {
            font-weight: 600;
            background-color: #f1f3f5;
        }
        .btn-action {
            border-radius: 20px;
            padding: 5px 15px;
            font-weight: 500;
            transition: all 0.2s ease;
        }
        .btn-action:hover {
            transform: translateY(-1px);
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
    </style>
</head>
<body>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            
            <%-- Session Error Message --%>
            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="alert alert-danger alert-dismissible fade show shadow-sm mb-4" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i> ${sessionScope.errorMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="errorMsg" scope="session" />
            </c:if>
            
            <%-- Session Success Message --%>
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
                        <i class="fas fa-users-cog text-primary me-2"></i> User Management
                    </h5>
                    
                    <%-- 💡 အသစ်ထည့်ထားသော Back Button (Home သို့ ပြန်ရန်) --%>
                    <div>
                        <a href="home" class="btn btn-sm btn-secondary rounded-pill px-3 me-2 shadow-sm fw-bold">
                            <i class="fas fa-arrow-left me-1"></i> Back to Home
                        </a>
                       
                    </div>
                </div>
                
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead>
                                <tr>
                                    <th class="ps-4">User Details</th>
                                    <th>Role</th>
                                    <th class="text-end pe-4">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%-- Loop ပတ်ပြီး Database ထဲက User စာရင်းအမှန်ကိုပဲ ပြမည့်အပိုင်း --%>
                                <c:forEach var="u" items="${userList}">
                                    <tr>
                                        <td class="ps-4">
                                            <div class="d-flex align-items-center">
                                                <div class="avatar-icon bg-light text-secondary rounded-circle d-flex align-items-center justify-content-center me-3" style="width: 40px; height: 40px;">
                                                    <i class="fas fa-user"></i>
                                                </div>
                                                <div>
                                                    <div class="fw-bold text-dark">${u.username}</div>
                                                    <small class="text-muted">ID: #${u.id}</small>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <span class="badge ${u.role == 'Admin' ? 'bg-primary-subtle text-primary' : 'bg-light text-secondary'} px-3 py-2 rounded-pill">
                                                <i class="fas ${u.role == 'Admin' ? 'fa-user-shield' : 'fa-user'} me-1"></i> ${u.role}
                                            </span>
                                        </td>
                                        <td class="text-end pe-4">
                                            <a href="DeleteUser?id=${u.id}" 
                                               class="btn btn-sm btn-outline-danger btn-action shadow-sm" 
                                               onclick="return confirm('Are you sure delete this user?')">
                                                <i class="fas fa-trash-alt me-1"></i> Delete
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                
                                <%-- 💡 အပိုဖြစ်နေတဲ့ ကုဒ်အဟောင်းကြီးကို လုံးဝ ဖျက်ထုတ်ပေးလိုက်ပါပြီ --%>
                                
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