package example.milktea_shop.service.serviceimplenment;

import example.milktea_shop.dto.response.report.RevenueReportResponse;
import example.milktea_shop.entity.Order;
import example.milktea_shop.enums.OrderStatus;
import example.milktea_shop.enums.ReportType;
import example.milktea_shop.repository.OrderRepository;
import example.milktea_shop.service.serviceinterface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;

    @Override
    public RevenueReportResponse getRevenueReport(ReportType type) {
        List<Order> completedOrders = orderRepository.findAllByOrderByCreatedDateDesc()
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .toList();

        Map<String, List<Order>> groupedOrders = switch (type) {
            case DAY -> completedOrders.stream().collect(Collectors.groupingBy(
                    order -> order.getCreatedDate().toLocalDate().toString(),
                    TreeMap::new,
                    Collectors.toList()
            ));
            case MONTH -> completedOrders.stream().collect(Collectors.groupingBy(
                    order -> order.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    TreeMap::new,
                    Collectors.toList()
            ));
            case YEAR -> completedOrders.stream().collect(Collectors.groupingBy(
                    order -> String.valueOf(order.getCreatedDate().getYear()),
                    TreeMap::new,
                    Collectors.toList()
            ));
        };

        List<RevenueReportResponse.RevenueItem> items = groupedOrders.entrySet().stream()
                .map(entry -> RevenueReportResponse.RevenueItem.builder()
                        .period(entry.getKey())
                        .totalOrders((long) entry.getValue().size())
                        .totalRevenue(entry.getValue().stream()
                                .map(Order::getTotalMoney)
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                        .build())
                .toList();

        BigDecimal grandTotalRevenue = items.stream()
                .map(RevenueReportResponse.RevenueItem::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return RevenueReportResponse.builder()
                .type(type)
                .totalOrders((long) completedOrders.size())
                .grandTotalRevenue(grandTotalRevenue)
                .items(items)
                .build();
    }
}
