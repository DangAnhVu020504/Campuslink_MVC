package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.CongViecDAO;

@Controller
public class SearchGraduationJobController {

    @Autowired
    private CongViecDAO congViecDAO;

    @GetMapping("/searchGraduationJob")
    public String showSearchGraduationJobForm(Model model) {
        model.addAttribute("keyword", "");
        return "searchGraduationJob";
    }

    @PostMapping("/searchGraduationJob")
    public String searchGraduationJob(@RequestParam("keyword") String keyword, Model model) throws Exception {
        List<Object[]> results = congViecDAO.searchGraduationAndJob(keyword);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        return "searchGraduationJob";
    }
}