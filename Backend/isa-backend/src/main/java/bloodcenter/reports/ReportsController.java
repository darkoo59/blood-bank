package bloodcenter.reports;

import bloodcenter.IsaBackendApplication;
import bloodcenter.address.AddressService;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;

@RestController
@RequestMapping("api/blood-use-report")
public class ReportsController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletContext context;
    @Autowired
    private ReportsService reportsService;

    public ReportsController(ReportsService service) {
        this.reportsService = service;
    }

    @PostMapping
    public void receiveReport(@RequestParam(value = "file") MultipartFile pdfFile) throws IOException {
        reportsService.saveReport(pdfFile);
    }
    
}
