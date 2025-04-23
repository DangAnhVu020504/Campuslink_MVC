package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.SinhVienDAO;
import dao.TotNghiepDAO;
import model.SinhVien;
import model.TotNghiep;

@Service
public class StudentService {

    @Autowired
    private SinhVienDAO sinhVienDAO;

    @Autowired
    private TotNghiepDAO totNghiepDAO;

    @Transactional
    public void saveStudent(SinhVien sinhVien, TotNghiep totNghiep) throws Exception {
        sinhVienDAO.save(sinhVien);
        totNghiepDAO.save(totNghiep);
    }
}