<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nhập Thông Tin Sinh Viên</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        .nav-bar {
            text-align: center;
            margin-bottom: 30px;
        }

        .nav-button {
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            margin: 0 10px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .nav-button:hover {
            background-color: #2980b9;
        }

        .form-container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .form-section {
            margin-bottom: 25px;
        }

        .form-section h3 {
            color: #34495e;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .form-group {
            display: flex;
            margin-bottom: 15px;
            align-items: center;
        }

        label {
            width: 180px;
            font-weight: bold;
            color: #555;
        }

        input[type="text"],
        input[type="email"],
        input[type="date"],
        select {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
            background: #fdfdfd;
        }

        input[type="submit"] {
            background-color: #27ae60;
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #1e8449;
        }

        .error {
            color: red;
            margin-bottom: 15px;
        }

        .success {
            color: green;
            margin-bottom: 15px;
        }
    </style>

    <script>
        function validateForm() {
            const soCMND = document.getElementById("soCMND").value;
            const maTruong = document.getElementById("maTruong").value;
            const maNganh = document.getElementById("maNganh").value;
            const ngayTN = document.getElementById("ngayTN").value;

            if (!soCMND || soCMND.trim() === "") {
                alert("Số CMND không được để trống.");
                return false;
            }

            if (!maTruong || maTruong === "") {
                alert("Vui lòng chọn Trường.");
                return false;
            }

            if (!maNganh || maNganh === "") {
                alert("Vui lòng chọn Ngành.");
                return false;
            }

            const datePattern = /^\d{4}-\d{2}-\d{2}$/;
            if (!ngayTN || !datePattern.test(ngayTN)) {
                alert("Ngày tốt nghiệp phải có định dạng YYYY-MM-DD.");
                return false;
            }

            const date = new Date(ngayTN);
            const today = new Date();
            if (isNaN(date.getTime()) || date > today) {
                alert("Ngày tốt nghiệp không hợp lệ.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
    <h2>Nhập Thông Tin Sinh Viên</h2>
    <div class="nav-bar">
        <button class="nav-button" onclick="window.location.href='searchBasic'">Tìm Kiếm Cơ Bản</button>
        <button class="nav-button" onclick="window.location.href='searchGraduationJob'">Tìm Kiếm Tốt Nghiệp & Công Việc</button>
    </div>

    <div class="form-container">
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty success}">
            <p class="success">${success}</p>
        </c:if>

        <form action="saveStudent" method="post" onsubmit="return validateForm()">
            <div class="form-section">
                <h3>Thông Tin Cá Nhân</h3>
                <div class="form-group">
                    <label for="soCMND">Số CMND (*):</label>
                    <input type="text" id="soCMND" name="soCMND" value="${sinhVien.soCMND}" required />
                </div>
                <div class="form-group">
                    <label for="hoTen">Họ Tên:</label>
                    <input type="text" id="hoTen" name="hoTen" value="${sinhVien.hoTen}" />
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${sinhVien.email}" />
                </div>
                <div class="form-group">
                    <label for="soDT">Số Điện Thoại:</label>
                    <input type="text" id="soDT" name="soDT" value="${sinhVien.soDT}" />
                </div>
                <div class="form-group">
                    <label for="diaChi">Địa Chỉ:</label>
                    <input type="text" id="diaChi" name="diaChi" value="${sinhVien.diaChi}" />
                </div>
            </div>

            <div class="form-section">
                <h3>Thông Tin Tốt Nghiệp</h3>
                <div class="form-group">
                    <label for="maTruong">Trường (*):</label>
                    <select id="maTruong" name="maTruong" required>
                        <option value="">Chọn Trường</option>
                        <c:forEach var="truong" items="${truongList}">
                            <option value="${truong.maTruong}" ${truong.maTruong == totNghiep.maTruong ? 'selected' : ''}>${truong.tenTruong}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="maNganh">Ngành (*):</label>
                    <select id="maNganh" name="maNganh" required>
                        <option value="">Chọn Ngành</option>
                        <c:forEach var="nganh" items="${nganhList}">
                            <option value="${nganh.maNganh}" ${nganh.maNganh == totNghiep.maNganh ? 'selected' : ''}>${nganh.tenNganh}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="heTN">Hệ Tốt Nghiệp:</label>
                    <input type="text" id="heTN" name="heTN" value="${totNghiep.heTN}" />
                </div>
                <div class="form-group">
                    <label for="ngayTN">Ngày Tốt Nghiệp (*):</label>
                    <input type="date" id="ngayTN" name="ngayTN" value="${ngayTNStr}" required />
                </div>
                <div class="form-group">
                    <label for="loaiTN">Loại Tốt Nghiệp:</label>
                    <input type="text" id="loaiTN" name="loaiTN" value="${totNghiep.loaiTN}" />
                </div>
            </div>

            <div class="form-group" style="justify-content: center;">
                <input type="submit" value="Lưu Thông Tin" />
            </div>
        </form>
    </div>
</body>
</html>
