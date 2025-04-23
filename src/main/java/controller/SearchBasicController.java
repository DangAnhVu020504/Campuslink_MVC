package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.SinhVienDAO;
import model.SinhVien;

@Controller
public class SearchBasicController {

    @Autowired
    private SinhVienDAO sinhVienDAO;

    @GetMapping("/searchBasic")
    public String showSearchBasicForm(Model model) {
        model.addAttribute("keyword", "");
        return "searchBasic";
    }

    @PostMapping("/searchBasic")
    public String searchBasic(@RequestParam("keyword") String keyword, Model model) throws Exception {
        List<SinhVien> results = sinhVienDAO.search(keyword);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        return "searchBasic";
    }
}