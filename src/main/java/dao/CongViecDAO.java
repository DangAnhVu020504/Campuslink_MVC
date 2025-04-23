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

import model.CongViec;
import model.SinhVien;
import model.TotNghiep;

@Repository
public class CongViecDAO {

    @Autowired
    private DataSource dataSource;

    private String normalizeString(String str) {
        if (str == null) return null;
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noDiacritics = pattern.matcher(normalized).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
        return noDiacritics.toLowerCase();
    }

    public List<Object[]> searchGraduationAndJob(String keyword) throws Exception {
        List<Object[]> results = new ArrayList<>();
        String sql = """
            SELECT sv.*, tn.*, cv.*
            FROM SINHVIEN sv
            LEFT JOIN TOT_NGHIEP tn ON sv.SoCMND = tn.SoCMND
            LEFT JOIN CONG_VIEC cv ON sv.SoCMND = cv.SoCMND
            WHERE sv.HoTen LIKE ? 
               OR sv.DiaChi LIKE ? 
               OR sv.SoCMND = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String keywordNormalized = normalizeString(keyword);
            String searchPattern = "%" + keyword + "%";
            String searchPatternNormalized = "%" + keywordNormalized + "%";
            pstmt.setString(1, searchPatternNormalized);
            pstmt.setString(2, searchPatternNormalized);
            pstmt.setString(3, keyword);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String hoTen = rs.getString("sv.HoTen");
                    String diaChi = rs.getString("sv.DiaChi");
                    String soCMND = rs.getString("sv.SoCMND");


                    String hoTenNormalized = normalizeString(hoTen);
                    String diaChiNormalized = normalizeString(diaChi);
                    String soCMNDNormalized = soCMND != null ? soCMND.toLowerCase() : null;
                    String keywordLowerCase = keyword.toLowerCase();


                    if (hoTenNormalized.contains(keywordNormalized) || hoTen.contains(keyword) ||
                        diaChiNormalized.contains(keywordNormalized) || diaChi.contains(keyword) ||
                        (soCMNDNormalized != null && soCMNDNormalized.equals(keywordLowerCase)) ||
                        (soCMND != null && soCMND.equals(keyword))) {
                        Object[] result = new Object[3];
                        SinhVien sv = new SinhVien();
                        sv.setSoCMND(soCMND);
                        sv.setHoTen(hoTen);
                        sv.setEmail(rs.getString("sv.Email"));
                        sv.setSoDT(rs.getString("sv.SoDT"));
                        sv.setDiaChi(diaChi);

                        TotNghiep tn = new TotNghiep();
                        tn.setSoCMND(rs.getString("tn.SoCMND"));
                        tn.setMaTruong(rs.getString("tn.MaTruong"));
                        tn.setMaNganh(rs.getString("tn.MaNganh"));
                        tn.setHeTN(rs.getString("tn.HeTN"));
                        tn.setNgayTN(rs.getDate("tn.NgayTN"));
                        tn.setLoaiTN(rs.getString("tn.LoaiTN"));

                        CongViec cv = new CongViec();
                        cv.setSoCMND(rs.getString("cv.SoCMND"));
                        cv.setNgayVaoCongTy(rs.getDate("cv.NgayVaoCongTy"));
                        cv.setMaNganh(rs.getString("cv.MaNganh"));
                        cv.setTenCongViec(rs.getString("cv.TenCongViec"));
                        cv.setTenCongTy(rs.getString("cv.TenCongTy"));
                        cv.setDiaChiCongTy(rs.getString("cv.DiaChiCongTy"));
                        cv.setThoiGianLamViec(rs.getInt("cv.ThoiGianLamViec"));

                        result[0] = sv;
                        result[1] = tn;
                        result[2] = cv;
                        results.add(result);
                    }
                }
            }
        }
        return results;
    }
}