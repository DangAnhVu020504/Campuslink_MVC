<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tìm Kiếm Sinh Viên</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <h2 class="mb-4 text-center">🔍 Tìm Kiếm Sinh Viên</h2>

        <div class="d-flex justify-content-center mb-4">
            <a href="studentForm" class="btn btn-success me-2">Nhập Thông Tin Sinh Viên</a>
            <a href="searchGraduationJob" class="btn btn-primary">Tìm Kiếm Tốt Nghiệp & Công Việc</a>
        </div>

        <div class="card shadow-sm">
            <div class="card-body">
                <form method="post" action="searchBasic">
                    <div class="mb-3 row">
                        <label for="keyword" class="col-sm-3 col-form-label">Từ khóa (CMND hoặc Họ Tên):</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${keyword}" placeholder="Nhập từ khóa...">
                        </div>
                        <div class="col-sm-3">
                            <button type="submit" class="btn btn-outline-secondary">Tìm kiếm</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${not empty results}">
            <div class="mt-5">
                <h5 class="mb-3">Kết quả tìm kiếm:</h5>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle text-center">
                        <thead class="table-light">
                            <tr>
                                <th>Số CMND</th>
                                <th>Họ Tên</th>
                                <th>Email</th>
                                <th>Số ĐT</th>
                                <th>Địa Chỉ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${results}">
                                <tr>
                                    <td>${student.soCMND}</td>
                                    <td>${student.hoTen}</td>
                                    <td>${student.email}</td>
                                    <td>${student.soDT}</td>
                                    <td>${student.diaChi}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </div>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
