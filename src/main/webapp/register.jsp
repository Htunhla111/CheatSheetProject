<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account | CheatSheet Central</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .card {
            border-radius: 15px;
            overflow: hidden;
        }
        .register-header {
            background: #ffffff;
            padding: 20px;
            text-align: center;
        }
        .btn-register {
            background: linear-gradient(to right, #667eea, #764ba2);
            border: none;
            padding: 12px;
            font-weight: 600;
            transition: 0.3s;
        }
        .btn-register:hover {
            opacity: 0.9;
            transform: translateY(-2px);
        }
        .form-control {
            border-radius: 10px;
            padding: 12px;
            background-color: #f8f9fa;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #764ba2;
        }
        .input-group-text {
            background: transparent;
            border-radius: 10px 0 0 10px;
            border-right: none;
        }
        .form-control {
            border-radius: 0 10px 10px 0;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5 col-lg-4">
                <div class="card shadow-lg border-0">
                    <div class="card-body p-5">
                        
                        <div class="text-center mb-4">
                            <div class="mb-3">
                                <h3 class="fw-bold m-0" style="letter-spacing: 0.5px; font-size: 1.6rem; font-family: 'Segoe UI', sans-serif;">
                                    <span style="color: #444;">CheatSheet</span> 
                                    <span style="color: #ffc107;">Central</span>
                                </h3>
                            </div>

                            <i class="bi bi-person-plus-fill" style="font-size: 3rem; color: #764ba2;"></i>
                            <h2 class="fw-bold mt-2">Join Us</h2>
                            <p class="text-muted small">Create an account to save your favorite cheatsheets</p>
                        </div>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger d-flex align-items-center rounded-3 p-2" role="alert" style="font-size: 0.9rem;">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                <div>${error}</div>
                            </div>
                        </c:if>

                        <c:if test="${not empty success}">
                            <div class="alert alert-success d-flex align-items-center rounded-3 p-2" role="alert" style="font-size: 0.9rem;">
                                <i class="bi bi-check-circle-fill me-2"></i>
                                <div>${success}</div>
                            </div>
                        </c:if>

                        <form action="register" method="post">
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Username</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person"></i></span>
                                    <input type="text" name="username" class="form-control" placeholder="Choose a username" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label small fw-bold">Email Address</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                    <input type="email" name="email" class="form-control" placeholder="Enter your email" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label small fw-bold">Password</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                    <input type="password" name="password" class="form-control" placeholder="Create a password" required>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label small fw-bold">Confirm Password</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-shield-check"></i></span>
                                    <input type="password" name="confirmPassword" class="form-control" placeholder="Repeat your password" required>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary btn-register w-100 text-white shadow-sm">
                                SIGN UP
                            </button>
                            
                            <div class="text-center mt-4">
                                <span class="text-muted small">Already a member?</span> 
                                <a href="login" class="text-decoration-none small fw-bold" style="color: #764ba2;"> Login here</a>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="text-center mt-3">
                    <a href="home" class="text-white-50 text-decoration-none small"><i class="bi bi-arrow-left"></i> Back to Home</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>