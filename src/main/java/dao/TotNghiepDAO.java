package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.TotNghiep;

@Repository
public class TotNghiepDAO {

    @Autowired
    private DataSource dataSource;

    public void save(TotNghiep totNghiep) throws Exception {
        String insertTotNghiepSQL = "INSERT INTO TOT_NGHIEP (SoCMND, MaTruong, MaNganh, HeTN, NgayTN, LoaiTN) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertTotNghiepSQL)) {
            pstmt.setString(1, totNghiep.getSoCMND());
            pstmt.setString(2, totNghiep.getMaTruong());
            pstmt.setString(3, totNghiep.getMaNganh());
            pstmt.setString(4, totNghiep.getHeTN());
            pstmt.setDate(5, new java.sql.Date(totNghiep.getNgayTN().getTime()));
            pstmt.setString(6, totNghiep.getLoaiTN());
            pstmt.executeUpdate();
        }
    }
}