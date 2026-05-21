<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login | CheatSheet Central</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    
    <style>
        body {
            /* လှပတဲ့ Gradient Background */
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .login-card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
        }
        .card-header {
            background: #fff;
            border-bottom: none;
            padding-top: 30px;
        }
        .btn-login {
            background: #764ba2;
            border: none;
            padding: 12px;
            font-weight: 600;
            transition: 0.3s;
        }
        .btn-login:hover {
            background: #5a3782;
            transform: translateY(-2px);
        }
        .input-group-text {
            background-color: transparent;
            border-right: none;
        }
        .form-control {
            border-left: none;
            padding: 12px;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #dee2e6;
        }
        .input-group:focus-within .input-group-text {
            border-color: #764ba2;
            color: #764ba2;
        }
        .input-group:focus-within .form-control {
            border-color: #764ba2;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5 col-lg-4">
                
                <div class="card login-card shadow-lg">
                    <div class="card-header text-center">
                        
                        <div class="mb-3">
                            <h3 class="fw-bold m-0" style="letter-spacing: 0.5px; font-size: 1.6rem;">
                                <span style="color: #444;">CheatSheet</span> 
                                <span style="color: #ffc107;">Central</span>
                            </h3>
                        </div>

                        <div class="mb-3">
                            <i class="bi bi-person-circle" style="font-size: 3rem; color: #764ba2;"></i>
                        </div>
                        
                        <h4 class="fw-bold" style="color: #444;">Welcome Back</h4>
                        <p class="text-muted">Enter credentials to access admin panel</p>
                    </div>
                    
                    <div class="card-body p-4 pt-2">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger d-flex align-items-center p-2" role="alert" style="font-size: 0.9rem;">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                <div>${error}</div>
                            </div>
                        </c:if>

                        <form action="login" method="post">
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Username</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person"></i></span>
                                    <input type="text" name="username" class="form-control" placeholder="Admin username" required>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label small fw-bold">Password</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                    <input type="password" name="password" class="form-control" placeholder="••••••••" required>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary btn-login w-100 shadow-sm mb-3">
                                LOGIN NOW
                            </button>
                            
                            <div class="text-center">
                                <a href="home" class="text-decoration-none small text-muted">
                                    <i class="bi bi-arrow-left"></i> Back to Home
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                
                <p class="text-center text-white-50 mt-4 small">
                    &copy; 2026 CheatSheet Central Project
                </p>
                
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>