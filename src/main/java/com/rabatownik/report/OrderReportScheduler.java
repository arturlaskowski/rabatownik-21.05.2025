package com.rabatownik.report;

import com.rabatownik.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("report")
class OrderReportScheduler {

    private final OrderRepository orderRepository;
    private final ReportGenerator reportGenerator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    OrderReportScheduler(OrderRepository orderRepository, ReportGenerator reportGenerator) {
        this.orderRepository = orderRepository;
        this.reportGenerator = reportGenerator;
    }

    @Scheduled(fixedDelayString = "${scheduling.report.generation.delay:60000}", initialDelayString = "${scheduling.report.generation.initialDelay:60000}")
    public void generateReports() {
        var orders = orderRepository.findAll();
        reportGenerator.generatePdfReport(orders);

        logger.info("Usuwam wszystkie zamówiena...");
        orderRepository.deleteAll();
    }
}