<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Icon</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { background-color: #f4f7f6; padding-top: 50px; }
        .card { border-radius: 12px; border: none; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
        .btn-primary { background-color: #0d6efd; border: none; }
    </style>
</head>
<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card p-4">
                <div class="text-center mb-4">
                    <i class="fas fa-icons fa-3x text-primary mb-2"></i>
                    <h3 class="fw-bold">Add FontAwesome Icon</h3>
                    <p class="text-muted small">Database ထဲသို့ Icon အသစ်ထည့်ရန်</p>
                </div>

                <form action="add-icon" method="post">
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold">Icon Name (Display)</label>
                        <input type="text" name="displayName" class="form-control" 
                               placeholder="e.g., Angular Logo" required>
                    </div>
                    
                    <div class="mb-4">
                        <label class="form-label fw-bold">FontAwesome Class</label>
                        <input type="text" name="iconClass" class="form-control" 
                               placeholder="e.g., fab fa-angular" required>
                        <div class="form-text mt-2">
                            Icons ရှာရန်: <a href="https://fontawesome.com/icons" target="_blank" class="text-decoration-none">fontawesome.com</a>
                        </div>
                    </div>
                    
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <i class="fas fa-save me-2"></i> Save Icon
                        </button>
                        <a href="manage-categories" class="btn btn-light">Back to List</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>