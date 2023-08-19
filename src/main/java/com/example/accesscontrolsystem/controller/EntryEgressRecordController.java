package com.example.accesscontrolsystem.controller;

import com.example.accesscontrolsystem.model.AccessCheckResultModel;
import com.example.accesscontrolsystem.model.EntryEgressRecordModel;
import com.example.accesscontrolsystem.service.AccessCheckResultService;
import com.example.accesscontrolsystem.service.EntryEgressRecordService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EntryEgressRecordController {
    private final EntryEgressRecordService entryEgressRecordService;

    @GetMapping("/acsRecord")
    public String getResult(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<EntryEgressRecordModel> acrList = entryEgressRecordService.getResults();

        entryEgressRecordService.sortResults(acrList, sortBy);

        model.addAttribute("acrList", acrList);
        model.addAttribute("acrCount", entryEgressRecordService.getACRCount(acrList));
        return "result/result-control";
    }

    @GetMapping("/acsRecord/deleteACR/{id}")
    public String deleteResult(@PathVariable("id") Long id) {
        entryEgressRecordService.removeResult(id);
        return "redirect:/acsRecord";
    }

    @GetMapping("/acsRecord/deleteTenOldest")
    public String deleteTenOldest() {
        entryEgressRecordService.removeOldestTen();
        return "redirect:/acsRecord";
    }

    @GetMapping("/acsRecord/deleteAllRecords")
    public String deleteAll(){
        entryEgressRecordService.removeAll();
        return "redirect:/acsRecord";
    }

    @GetMapping("acsRecord/result/exportPDF")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        entryEgressRecordService.exportToPDF(response);
    }

    @GetMapping("/acsRecord/result/exportCSV")
    public String exportToCSV() throws  IOException {
        entryEgressRecordService.exportToCSV();
        return "redirect:/acsRecord";
    }
}
