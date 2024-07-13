package com.rabatownik.report;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rabatownik.order.Order;
import org.apache.pdfbox.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Component
@Profile("report")
public class ReportGenerator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${report.path}")
    private String reportPath;

    public void generatePdfReport(List<Order> orders) {
        logger.info("Rozpoczynam generowanie raportu...");
        try {
            Files.createDirectories(Paths.get(reportPath));
            String fileName = "Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
            String filePath = reportPath + fileName;
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            ClassPathResource imageResource = new ClassPathResource("images/report_image.jpg");
            Image image = Image.getInstance(IOUtils.toByteArray(imageResource.getInputStream()));
            image.scaleToFit(PageSize.A4.getWidth() * 3 / 4, PageSize.A4.getHeight() / 4);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            var title = new Paragraph("Buldog daje okejke, na ten raport!", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            Stream.of("Kod Zamówienia", "Cena Przed Rabatem", "Cena Po Rabacie")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle, fontBody));
                        table.addCell(header);
                    });

            orders.forEach(order -> {
                table.addCell(new Phrase(order.getOrderCode(), fontBody));
                table.addCell(new Phrase(order.getPriceBeforeDiscount().amount().toString(), fontBody));
                table.addCell(new Phrase(order.getPriceAfterDiscount().amount().toString(), fontBody));
            });

            document.add(table);
            document.close();

            logger.info("Koniec generowania raportu. Sprawdź czy go masz w: {}", filePath);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}