package com.example.accesscontrolsystem.controller;

import com.example.accesscontrolsystem.model.AccessCheckResultModel;
import com.example.accesscontrolsystem.model.ResultPDFExporter;
import com.example.accesscontrolsystem.service.AccessCheckResultService;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccessCheckResultController {
    private final AccessCheckResultService accessCheckResultService;

    @GetMapping("/result")
    public String getResult(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<AccessCheckResultModel> acrList = accessCheckResultService.getResults();
        accessCheckResultService.sortResults(acrList, sortBy);

        model.addAttribute("acrList", acrList);
        model.addAttribute("acrCount", accessCheckResultService.getACRCount(acrList));
        return "result/result";
    }

    @GetMapping("/deleteACR/{id}")
    public String deleteResult(@PathVariable("id") Long id) {
        accessCheckResultService.removeResult(id);
        return "redirect:/result";
    }

    @GetMapping("/deleteTenOldest")
    public String deleteTenOldest() {
        accessCheckResultService.removeOldestTen();
        return "redirect:/result";
    }

    @GetMapping("/deleteAllRecords")
    public String deleteAll(){
        accessCheckResultService.removeAll();
        return "redirect:/result";
    }

    @GetMapping("result/exportPDF")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        accessCheckResultService.exportToPDF(response);
    }

    @GetMapping("result/exportCSV")
    public String exportToCSV() throws  IOException {
        accessCheckResultService.exportToCSV();
        return "redirect:/result";
    }
}
