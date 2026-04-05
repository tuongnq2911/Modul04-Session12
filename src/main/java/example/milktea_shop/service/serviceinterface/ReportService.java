package example.milktea_shop.service.serviceinterface;

import example.milktea_shop.dto.response.report.RevenueReportResponse;
import example.milktea_shop.enums.ReportType;

public interface ReportService {

    RevenueReportResponse getRevenueReport(ReportType type);
}
