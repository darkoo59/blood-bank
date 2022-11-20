package bloodcenter.reports;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReportsService {

    public void saveReport(MultipartFile pdfFile) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        File file = new File("src/main/resources/reports/bloodReport-" +dtf.format(now) +".pdf");
        try (OutputStream os = new FileOutputStream((file))) {
            os.write(pdfFile.getBytes());
        }
    }
}
