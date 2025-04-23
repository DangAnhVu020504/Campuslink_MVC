package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.NganhDAO;
import dao.SinhVienDAO;
import dao.TruongDAO;
import model.Nganh;
import model.SinhVien;
import model.TotNghiep;
import model.Truong;
import service.StudentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StudentFormController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TruongDAO truongDAO;

    @Autowired
    private NganhDAO nganhDAO;

    @Autowired
    private SinhVienDAO sinhVienDAO; 

    @InitBinder("totNghiep")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("ngayTN");
    }

    @GetMapping("/")
    public String redirectToStudentForm(Model model) {
        return "redirect:/studentForm";
    }

    @GetMapping("/studentForm")
    public String showStudentForm(Model model) throws Exception {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("totNghiep", new TotNghiep());
        List<Truong> truongList = truongDAO.getAll();
        List<Nganh> nganhList = nganhDAO.getAll();
        System.out.println("truongList: " + truongList);
        System.out.println("nganhList: " + nganhList);
        model.addAttribute("truongList", truongList);
        model.addAttribute("nganhList", nganhList);
        return "studentForm";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("sinhVien") SinhVien sinhVien,
                              @ModelAttribute("totNghiep") TotNghiep totNghiep,
                              @RequestParam("ngayTN") String ngayTNStr,
                              HttpServletRequest request,
                              Model model) throws Exception {
        try {

            System.out.println("Request parameters:");
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + String.join(", ", entry.getValue()));
            }


            System.out.println("sinhVien: " + sinhVien);
            System.out.println("totNghiep: " + totNghiep);
            System.out.println("ngayTNStr: " + ngayTNStr);

            String soCMND = sinhVien.getSoCMND() != null ? sinhVien.getSoCMND().trim() : null;
            String maTruong = totNghiep.getMaTruong() != null ? totNghiep.getMaTruong().trim() : null;
            String maNganh = totNghiep.getMaNganh() != null ? totNghiep.getMaNganh().trim() : null;

            if (soCMND == null || soCMND.isEmpty() ||
                maTruong == null || maTruong.isEmpty() || maTruong.equals("Chọn Trường") ||
                maNganh == null || maNganh.isEmpty() || maNganh.equals("Chọn Ngành")) {
                System.out.println("Validation failed: Missing required fields.");
                model.addAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc: Số CMND, Mã Trường, Mã Ngành.");
                model.addAttribute("sinhVien", sinhVien);
                model.addAttribute("totNghiep", totNghiep);
                model.addAttribute("ngayTNStr", ngayTNStr);
                model.addAttribute("truongList", truongDAO.getAll());
                model.addAttribute("nganhList", nganhDAO.getAll());
                return "studentForm";
            }

            if (sinhVienDAO.existsBySoCMND(soCMND)) {
                System.out.println("Validation failed: SoCMND already exists.");
                model.addAttribute("error", "Số CMND đã tồn tại trong hệ thống.");
                model.addAttribute("sinhVien", sinhVien);
                model.addAttribute("totNghiep", totNghiep);
                model.addAttribute("ngayTNStr", ngayTNStr);
                model.addAttribute("truongList", truongDAO.getAll());
                model.addAttribute("nganhList", nganhDAO.getAll());
                return "studentForm";
            }

            if (ngayTNStr == null || ngayTNStr.trim().isEmpty()) {
                System.out.println("Validation failed: NgayTN is empty.");
                model.addAttribute("error", "Ngày tốt nghiệp không được để trống.");
                model.addAttribute("sinhVien", sinhVien);
                model.addAttribute("totNghiep", totNghiep);
                model.addAttribute("ngayTNStr", ngayTNStr);
                model.addAttribute("truongList", truongDAO.getAll());
                model.addAttribute("nganhList", nganhDAO.getAll());
                return "studentForm";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date ngayTN;
            try {
                ngayTN = sdf.parse(ngayTNStr);
                totNghiep.setNgayTN(ngayTN);
                if (ngayTN.after(new Date())) {
                    System.out.println("Validation failed: NgayTN is in the future.");
                    model.addAttribute("error", "Ngày tốt nghiệp không được là ngày trong tương lai.");
                    model.addAttribute("sinhVien", sinhVien);
                    model.addAttribute("totNghiep", totNghiep);
                    model.addAttribute("ngayTNStr", ngayTNStr);
                    model.addAttribute("truongList", truongDAO.getAll());
                    model.addAttribute("nganhList", nganhDAO.getAll());
                    return "studentForm";
                }
            } catch (Exception e) {
                System.out.println("Validation failed: Invalid NgayTN format - " + e.getMessage());
                model.addAttribute("error", "Ngày tốt nghiệp không hợp lệ. Vui lòng nhập theo định dạng YYYY-MM-DD (ví dụ: 2023-12-31).");
                model.addAttribute("sinhVien", sinhVien);
                model.addAttribute("totNghiep", totNghiep);
                model.addAttribute("ngayTNStr", ngayTNStr);
                model.addAttribute("truongList", truongDAO.getAll());
                model.addAttribute("nganhList", nganhDAO.getAll());
                return "studentForm";
            }

            totNghiep.setSoCMND(sinhVien.getSoCMND());
            System.out.println("Saving student data...");
            studentService.saveStudent(sinhVien, totNghiep);
            System.out.println("Student data saved successfully.");
            model.addAttribute("success", "Lưu thông tin sinh viên thành công!");
            model.addAttribute("sinhVien", new SinhVien());
            model.addAttribute("totNghiep", new TotNghiep());
        } catch (Exception e) {
            System.out.println("Error saving student data: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Lưu thông tin không thành công: " + e.getMessage());
            model.addAttribute("sinhVien", sinhVien);
            model.addAttribute("totNghiep", totNghiep);
            model.addAttribute("ngayTNStr", ngayTNStr);
            model.addAttribute("truongList", truongDAO.getAll());
            model.addAttribute("nganhList", nganhDAO.getAll());
            return "studentForm";
        }
        model.addAttribute("truongList", truongDAO.getAll());
        model.addAttribute("nganhList", nganhDAO.getAll());
        return "studentForm";
    }
}