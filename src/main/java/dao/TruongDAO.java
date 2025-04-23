package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Truong;

@Repository
public class TruongDAO {

    @Autowired
    private DataSource dataSource;

    public List<Truong> getAll() throws Exception {
        List<Truong> truongList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TRUONG");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Truong truong = new Truong();
                truong.setMaTruong(rs.getString("MaTruong"));
                truong.setTenTruong(rs.getString("TenTruong"));
                truong.setDiaChi(rs.getString("DiaChi"));
                truong.setSoDT(rs.getString("SoDT"));
                truongList.add(truong);
            }
        }
        return truongList;
    }
}