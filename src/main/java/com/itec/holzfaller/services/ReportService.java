package com.itec.holzfaller.services;

import com.itec.holzfaller.common.DateUtils;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.UserRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by rbu on 4/8/17.
 */
public class ReportService {

    UserService userService = new UserService();

    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    private static Font subParFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.NORMAL);

    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);

    public void createPDFReportFor(String username) {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.dir") + "/" + username + "report.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        addTitlePage(document, username);
        addContent(document, username);
        document.close();
    }

    private void addTitlePage(Document document, String username) {
        Paragraph preface = new Paragraph();
        preface.add(new Paragraph(" "));
        preface.add(new Paragraph("Report for: " + username, titleFont));
        preface.add(new Paragraph(" "));
        preface.add(new Paragraph(
                "Report generated by: " + LoggedUserService.loggedUser.getUsername() + ", on  " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallFont));
        try {
            document.add(preface);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.newPage();
    }

    private void addContent(Document document, String username) {
        User user = userService.findByUsername(username);
        Paragraph usernamePar = new Paragraph("Username: " + user.getUsername(), titleFont);
        Paragraph emailPar = new Paragraph("Email: " + user.getEmail(), titleFont);
        Paragraph journiesPar = new Paragraph("Journies", subParFont);

        Paragraph tableParagraph = new Paragraph("", smallFont);
        createTable(tableParagraph, user.getJourneys());
        journiesPar.add(tableParagraph);

        try {
            document.add(usernamePar);
            document.add(emailPar);
            document.add(journiesPar);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.newPage();
    }

    private void createTable(Paragraph paragraph, List<Journey> journeys) {
        PdfPTable table = new PdfPTable(4); //number of columns

        PdfPCell locationHeader = new PdfPCell(new Phrase("Location"));
        locationHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(locationHeader);

        PdfPCell fromHeader = new PdfPCell(new Phrase("From"));
        fromHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(fromHeader);

        PdfPCell toHeader = new PdfPCell(new Phrase("To"));
        toHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(toHeader);

        PdfPCell costHeader = new PdfPCell(new Phrase("Cost"));
        costHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(costHeader);

        for(Journey journey : journeys){
            table.addCell(journey.getLocation().getName());
            table.addCell(DateUtils.dateToString(journey.getStartDate()));
            table.addCell(DateUtils.dateToString(journey.getEndDate()));
            table.addCell(journey.getCost() + "");
        }
        paragraph.add(table);
    }

    public String getStringReport(String username){
        User user = userService.findByUsername(username);
        String result = String.format("Username: %s \nEmail: %s", user.getUsername(), user.getEmail());
        result = result.concat("\n");
        result = result.concat(String.format("%s\t\t%s\t%s\t\t%s","Location", "Start Date", "End Date", "Cost"));

        for(Journey journey : user.getJourneys()){
            String line = String.format("%s\t\t%s\t%s\t\t%s", journey.getLocation().toString(), DateUtils.dateToString(journey.getStartDate()), DateUtils.dateToString(journey.getEndDate()), journey.getCost() + "");
            result = result.concat("\n");
            result = result.concat(line);
        }

        return result;
    }

}