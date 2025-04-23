package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Nganh;

@Repository
public class NganhDAO {

    @Autowired
    private DataSource dataSource;

    public List<Nganh> getAll() throws Exception {
        List<Nganh> nganhList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM NGANH");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Nganh nganh = new Nganh();
                nganh.setMaNganh(rs.getString("MaNganh"));
                nganh.setTenNganh(rs.getString("TenNganh"));
                nganh.setLoaiNganh(rs.getString("LoaiNganh"));
                nganhList.add(nganh);
            }
        }
        return nganhList;
    }
}