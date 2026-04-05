package example.milktea_shop.dto.response.report;

import example.milktea_shop.enums.ReportType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RevenueReportResponse {
    private ReportType type;
    private Long totalOrders;
    private BigDecimal grandTotalRevenue;
    private List<RevenueItem> items;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class RevenueItem {
        private String period;
        private Long totalOrders;
        private BigDecimal totalRevenue;
    }

}
