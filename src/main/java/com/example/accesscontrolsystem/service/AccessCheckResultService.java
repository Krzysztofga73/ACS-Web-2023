package com.example.accesscontrolsystem.service;

import com.example.accesscontrolsystem.model.AccessCheckResultModel;
import com.example.accesscontrolsystem.model.ResultPDFExporter;
import com.example.accesscontrolsystem.repository.AccessCheckResultRepository;
import com.lowagie.text.DocumentException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccessCheckResultService {
    private final AccessCheckResultRepository accessCheckResultRepository;

    @Value("${csv.output.path}") // ścieżka do pliku CSV po auomatycznym usuwaniu rekordów
    String path;
    @Value("${csv.output.path2}")  // ścieżka do pliku CSV po eksporcie
    String fullBackupPath;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    private String getFormattedDate() {
        return dateTimeFormatter.format(LocalDateTime.now());  // utowrzenie stringa z datą teraźniejszą do późniejszego dodania do nazwy pliku po eksporcie
    }

    public void addAccessCheckResult(AccessCheckResultModel acr) {
        accessCheckResultRepository.save(acr);
    }

    public List<AccessCheckResultModel> getResults() {
        return accessCheckResultRepository.findAll();
    }

    public AccessCheckResultModel getResultById(Long id) {
        return accessCheckResultRepository.findById(id).orElse(null);
    }

    public void saveEditResult(AccessCheckResultModel acr) {
        accessCheckResultRepository.save(acr);
    }

    public void removeResult(Long id) {
        accessCheckResultRepository.deleteById(id);
    }

    public void removeOldestTen() {    // -> metoda usuwa 10 najstrszych rekordów, brak backupu
        getResults().stream()
                .sorted(Comparator.comparing(u -> u.getCreationDate()))
                .limit(10)
                .collect(Collectors.toList())
                .forEach(e -> removeResult(e.getId()));
    }

    public void removeAll() {   // -> usuwa wszystko bez backupu
        accessCheckResultRepository.deleteAll();
    }

    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {   // eksport do pdf w oparciu o instruktarz z internetu
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=results_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<AccessCheckResultModel> list = getResults();

        ResultPDFExporter exporter = new ResultPDFExporter(list);
        exporter.export(response);
    }

    public void exportToCSV() throws IOException {
        String now = getFormattedDate() + ".csv";
        try {
            String fullBackupPathWithDate = fullBackupPath.replace(".csv", now);  // zastąpienie nazwy pliku zdefiniowanej w properties, dodanie aktualnej daty
            saveToCSV(getResults(), fullBackupPathWithDate);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void sortResults(List<AccessCheckResultModel> res, String sortBy) {
        if (sortBy != null) {
            switch (sortBy) {
                case "id":
                    res.sort(Comparator.comparing(AccessCheckResultModel::getId));
                    break;
                case "creationDate":
                    res.sort(Comparator.comparing(u -> u.getCreationDate().toString()));
                    break;
                case "description":
                    res.sort(Comparator.comparing(e -> e.getDescription().toLowerCase()));
                    break;
                default:
                    // Obsłuż nieznany parametr sortowania
                    break;
            }
        }
    }

    @PostConstruct
    public void removeOlderThan() throws IOException {
        String filePath = path;

        Date dateToCompare = new Date(System.currentTimeMillis() - 10 * 60 * 1000);   // wartość czasu ustawiania na stałe
        List<AccessCheckResultModel> acrFiltered = getResults().stream()              // lista z odfiltrowanymi rekordami
                .filter(e -> e.getCreationDate().before(dateToCompare))
                .collect(Collectors.toList());

        if (!acrFiltered.isEmpty()) {
            saveToCSV(acrFiltered, filePath);                                         // tworzenie backupu
            acrFiltered.forEach(e -> removeResult(e.getId()));                        // usuwanie rekordów
        }
    }


    public String getACRCount(List<AccessCheckResultModel> acr) {
        return "Total number of records: " + acr.size();
    }


    private void saveToCSV(List<AccessCheckResultModel> list, String filePath) throws IOException {
        Boolean isBackup = false;
        Boolean isEmpty = false;

        try {

            File newBackup = new File(filePath);
            if (!newBackup.exists()) {
                newBackup.createNewFile();
                isEmpty = true;
                isBackup = true;
            } else {
                isBackup = true;
                isEmpty = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            isBackup = false;
        }
        if (isBackup) {
            try {
                FileWriter writer = new FileWriter(filePath, true);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL.withDelimiter(';'));
                if (isEmpty) {
                    csvPrinter.printRecord("Id", "Description", "CreationDate");
                }
                for (AccessCheckResultModel e : list) {
                    csvPrinter.printRecord(e.getId(), e.getDescription(), e.getCreationDate());
                }
                csvPrinter.flush();
            } catch (IOException e) {
                throw new IOException("Cannot make backup");
            }
        }

    }
}
