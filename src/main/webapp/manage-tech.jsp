<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Technology</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow p-4">
                    <h3 class="mb-4 text-primary">
                        <c:choose>
                            <c:when test="${not empty techId}">Edit Technology</c:when>
                            <c:otherwise>Add New Technology</c:otherwise>
                        </c:choose>
                    </h3>

                    <form action="manage-tech" method="post">
                        <input type="hidden" name="id" value="${techId}">

                        <div class="mb-3">
                            <label class="form-label fw-bold">Technology Name</label>
                            <input type="text" name="techName" class="form-control" 
                                   value="${techName}" placeholder="e.g. Python, Ruby" required>
                        </div>

                        <div class="d-flex justify-content-end gap-2">
                            <a href="home" class="btn btn-secondary">Cancel</a>
                            <button type="submit" class="btn btn-success">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>