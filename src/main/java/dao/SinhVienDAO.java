package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.SinhVien;

@Repository
public class SinhVienDAO {

    @Autowired
    private DataSource dataSource;

    private String normalizeString(String str) {
        if (str == null) return null;
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noDiacritics = pattern.matcher(normalized).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
        return noDiacritics.toLowerCase();
    }

    public void save(SinhVien sinhVien) throws Exception {
        String insertSinhVienSQL = "INSERT INTO SINHVIEN (SoCMND, HoTen, Email, SoDT, DiaChi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSinhVienSQL)) {
            pstmt.setString(1, sinhVien.getSoCMND());
            pstmt.setString(2, sinhVien.getHoTen());
            pstmt.setString(3, sinhVien.getEmail());
            pstmt.setString(4, sinhVien.getSoDT());
            pstmt.setString(5, sinhVien.getDiaChi());
            pstmt.executeUpdate();
        }
    }

    public List<SinhVien> search(String keyword) throws Exception {
        List<SinhVien> students = new ArrayList<>();
        String sql = """
            SELECT * FROM SINHVIEN 
            WHERE HoTen LIKE ? 
               OR Email LIKE ? 
               OR SoDT LIKE ? 
               OR DiaChi LIKE ? 
               OR SoCMND LIKE ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String keywordNormalized = normalizeString(keyword); 
            String searchPatternNormalized = "%" + keywordNormalized + "%";
            pstmt.setString(1, searchPatternNormalized);
            pstmt.setString(2, searchPatternNormalized); 
            pstmt.setString(3, searchPatternNormalized);
            pstmt.setString(4, searchPatternNormalized);
            pstmt.setString(5, searchPatternNormalized);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String hoTen = rs.getString("HoTen");
                    String email = rs.getString("Email");
                    String soDT = rs.getString("SoDT");
                    String diaChi = rs.getString("DiaChi");
                    String soCMND = rs.getString("SoCMND");

                   
                    String hoTenNormalized = normalizeString(hoTen);
                    String emailNormalized = normalizeString(email);
                    String soDTNormalized = normalizeString(soDT);
                    String diaChiNormalized = normalizeString(diaChi);
                    String soCMNDNormalized = normalizeString(soCMND);

                    if (hoTenNormalized.contains(keywordNormalized) ||
                        emailNormalized.contains(keywordNormalized) ||
                        soDTNormalized.contains(keywordNormalized) ||
                        diaChiNormalized.contains(keywordNormalized) ||
                        soCMNDNormalized.contains(keywordNormalized)) {
                        SinhVien sv = new SinhVien();
                        sv.setSoCMND(soCMND);
                        sv.setHoTen(hoTen);
                        sv.setEmail(email);
                        sv.setSoDT(soDT);
                        sv.setDiaChi(diaChi);
                        students.add(sv);
                    }
                }
            }
        }
        return students;
    }

    public boolean existsBySoCMND(String soCMND) throws Exception {
        String sql = "SELECT COUNT(*) FROM SINHVIEN WHERE SoCMND = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, soCMND);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}