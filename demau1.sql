
-- Tạo lại cơ sở dữ liệu với utf8mb4
CREATE DATABASE demau1 CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE demau1;

-- Bảng SINHVIEN (bỏ cột HoTenKhongDau và DiaChiKhongDau)
CREATE TABLE SINHVIEN (
    SoCMND VARCHAR(12) PRIMARY KEY,
    HoTen VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    Email VARCHAR(100),
    SoDT VARCHAR(15),
    DiaChi VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Bảng TRUONG
CREATE TABLE TRUONG ( 
    MaTruong VARCHAR(10) PRIMARY KEY,
    TenTruong VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    DiaChi VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    SoDT VARCHAR(15)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO TRUONG (MaTruong, TenTruong, DiaChi, SoDT) VALUES
('DHQGTPHCM', 'Đại học Quốc gia TP.HCM', 'TP. Hồ Chí Minh', '028-1234567'),
('DHBKDN', 'Đại học Bách khoa Đà Nẵng', 'Đà Nẵng', '0236-9876543'),
('DHTN', 'Đại học Thái Nguyên', 'Thái Nguyên', '0208-4567890'),
('DHYHN', 'Đại học Y Hà Nội', 'Hà Nội', '024-1122334'),
('QNU', 'Đại học Quy Nhơn', 'Bình Định', '0256-3831213');

-- Bảng NGANH
CREATE TABLE NGANH (
    MaNganh VARCHAR(10) PRIMARY KEY,
    TenNganh VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    LoaiNganh VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO NGANH (MaNganh, TenNganh, LoaiNganh) VALUES
('CNTT', 'Công nghệ thông tin', 'Kỹ thuật'),
('QTKD', 'Quản trị kinh doanh', 'Kinh tế'),
('SPTO', 'Sư phạm Toán học', 'Sư phạm'),
('YDAK', 'Y đa khoa', 'Y tế'),
('KTNL', 'Kỹ thuật năng lượng', 'Kỹ thuật');

-- Bảng TOT_NGHIEP
CREATE TABLE TOT_NGHIEP (
    SoCMND VARCHAR(12),
    MaTruong VARCHAR(10),
    MaNganh VARCHAR(10),
    HeTN VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    NgayTN DATE,
    LoaiTN VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    PRIMARY KEY (SoCMND, MaTruong, MaNganh),
    FOREIGN KEY (SoCMND) REFERENCES SINHVIEN(SoCMND),
    FOREIGN KEY (MaTruong) REFERENCES TRUONG(MaTruong),
    FOREIGN KEY (MaNganh) REFERENCES NGANH(MaNganh)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Bảng CONG_VIEC
CREATE TABLE CONG_VIEC (
    SoCMND VARCHAR(12),
    NgayVaoCongTy DATE,
    MaNganh VARCHAR(10),
    TenCongViec VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    TenCongTy VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    DiaChiCongTy VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    ThoiGianLamViec INT,
    PRIMARY KEY (SoCMND, NgayVaoCongTy),
    FOREIGN KEY (SoCMND) REFERENCES SINHVIEN(SoCMND),
    FOREIGN KEY (MaNganh) REFERENCES NGANH(MaNganh)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Thêm dữ liệu mẫu
INSERT INTO SINHVIEN (SoCMND, HoTen, Email, SoDT, DiaChi) VALUES
('052204011243', 'Nguyễn Văn A', 'anguyen@gmail.com', '09325386123', 'Bình Định'),
('052204011245', 'Nguyễn Văn B', 'bnguyen@gmail.com', '09325386124', 'Bình Định');

INSERT INTO TOT_NGHIEP (SoCMND, MaTruong, MaNganh, HeTN, NgayTN, LoaiTN) VALUES
('052204011243', 'DHQGTPHCM', 'CNTT', 'Chính Quy', '2020-02-12', 'Tốt'),
('052204011245', 'DHQGTPHCM', 'CNTT', 'Chính Quy', '2020-02-12', 'Tốt');

INSERT INTO CONG_VIEC (SoCMND, NgayVaoCongTy, MaNganh, TenCongViec, TenCongTy, DiaChiCongTy, ThoiGianLamViec) VALUES
('052204011243', '2020-03-01', 'CNTT', 'Lập trình viên', 'FPT Software', 'TP. Hồ Chí Minh', 24),
('052204011245', '2020-03-01', 'CNTT', 'Kỹ sư phần mềm', 'VNG', 'TP. Hồ Chí Minh', 24);
