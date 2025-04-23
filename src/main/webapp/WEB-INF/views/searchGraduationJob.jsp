<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tìm Kiếm Thông Tin Tốt Nghiệp và Công Việc</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <h2 class="text-center mb-4">🎓 Tìm Kiếm Thông Tin Tốt Nghiệp & Công Việc</h2>

    <!-- Navigation Buttons -->
    <div class="d-flex justify-content-center mb-4">
        <a href="studentForm" class="btn btn-success me-2">Nhập Thông Tin Sinh Viên</a>
        <a href="searchBasic" class="btn btn-primary">Tìm Kiếm Cơ Bản</a>
    </div>

    <!-- Search Form -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <form method="post" action="searchGraduationJob">
                <div class="row align-items-center">
                    <div class="col-md-4">
                        <label for="keyword" class="form-label">Từ khóa (CMND hoặc Họ Tên):</label>
                    </div>
                    <div class="col-md-5">
                        <input type="text" id="keyword" name="keyword" class="form-control" placeholder="Nhập từ khóa..." value="${keyword}">
                    </div>
                    <div class="col-md-3 text-end">
                        <button type="submit" class="btn btn-outline-secondary">Tìm kiếm</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Results Table -->
    <c:if test="${not empty results}">
        <h5 class="mb-3">📋 Kết quả tìm kiếm:</h5>
        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle text-center">
                <thead class="table-light">
                    <tr>
                        <th>Số CMND</th>
                        <th>Họ Tên</th>
                        <th>Mã Ngành (TN)</th>
                        <th>Mã Trường (TN)</th>
                        <th>Mã Ngành (Công Ty)</th>
                        <th>Tên Công Ty</th>
                        <th>Ngày Vào Công Ty</th>
                        <th>Thời Gian Làm Việc</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="result" items="${results}">
                        <tr>
                            <td>${result[0].soCMND}</td>
                            <td>${result[0].hoTen}</td>
                            <td>${result[1].maNganh}</td>
                            <td>${result[1].maTruong}</td>
                            <td>${result[2].maNganh}</td>
                            <td>${result[2].tenCongTy}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty result[2].ngayVaoCongTy}">
                                        <fmt:formatDate value="${result[2].ngayVaoCongTy}" pattern="dd-MM-yyyy"/>
                                    </c:when>
                                    <c:otherwise>N/A</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${result[2].thoiGianLamViec}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
