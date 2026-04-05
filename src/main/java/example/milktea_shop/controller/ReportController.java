package example.milktea_shop.controller;

import example.milktea_shop.dto.response.ApiResponse;
import example.milktea_shop.dto.response.ResponseFactory;
import example.milktea_shop.dto.response.report.RevenueReportResponse;
import example.milktea_shop.enums.ReportType;
import example.milktea_shop.service.serviceinterface.ReportService;
import example.milktea_shop.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RevenueReportResponse>> getRevenueReport(@RequestParam ReportType type) {
        RevenueReportResponse response = reportService.getRevenueReport(type);
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.GET_SUCCESS, response));
    }
}
