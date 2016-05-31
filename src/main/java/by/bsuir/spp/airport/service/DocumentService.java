package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.entity.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Seagull on 30.05.2016.
 */
public class DocumentService extends BaseService {
    private static final String FONT_FILE = "font/arial.ttf";
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String SCHEDULE_DOCUMENT = DocumentType.SCHEDULE.getPath()+DocumentFormat.XLSX.getFormat();
    private static final String AIRLINES_DOCUMENT = DocumentType.AIRLINES.getPath()+DocumentFormat.XLSX.getFormat();
    private static final String TICKET_DOCUMENT = DocumentType.TICKET.getPath()+DocumentFormat.XLSX.getFormat();
    private static final String AIRLINE_FLIGHTS_DOCUMENT = DocumentType.AIRLINE_FLIGHTS.getPath()+DocumentFormat.XLSX.getFormat();
    private static final String AIRLINE_PILOTS_DOCUMENT = DocumentType.AIRLINE_PILOTS.getPath()+DocumentFormat.XLSX.getFormat();
    private static CellStyle defaultStyle;
    private static CellStyle headerStyle;
    private static CellStyle dateStyle;
    private static DocumentService instance = new DocumentService();

    private DocumentService(){
    }

    public static DocumentService getInstance() {
        return instance;
    }

    private int iteratorSize(Iterator iterator) {
        int size = 0;
        for (; iterator.hasNext();) {
            iterator.next();
            size++;
        }

        return size;
    }

    private void createCellStyle(Workbook workbook) {
        defaultStyle = workbook.createCellStyle();
        defaultStyle.setBorderBottom(CellStyle.BORDER_THIN);
        defaultStyle.setBorderLeft(CellStyle.BORDER_THIN);
        defaultStyle.setBorderTop(CellStyle.BORDER_THIN);
        defaultStyle.setBorderRight(CellStyle.BORDER_THIN);
        defaultStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        defaultStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        dateStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        dateStyle.setDataFormat(format.getFormat("dd.MM.yyyy"));
        dateStyle.setBorderBottom(CellStyle.BORDER_THIN);
        dateStyle.setBorderLeft(CellStyle.BORDER_THIN);
        dateStyle.setBorderTop(CellStyle.BORDER_THIN);
        dateStyle.setBorderRight(CellStyle.BORDER_THIN);
        dateStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        dateStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    }

    private String saveGeneratedDocument(DocumentType type, DocumentFormat format) throws ServiceException {
        String result = null;
        switch (format) {
            case XLSX:
                result = type.getPath() + format.getFormat();
                break;
            case CSV:
                saveCsv(type.getPath());
                result = type.getPath() + format.getFormat();
                break;
            case PDF:
                savePdf(type.getPath());
                result = type.getPath() + format.getFormat();
                break;
        }
        return result;
    }

    private void saveCsv(String fileName) throws ServiceException {
        File outFile = new File(fileName + DocumentFormat.CSV.getFormat());
        File inputFile = new File(fileName + DocumentFormat.XLSX.getFormat());
        StringBuilder data = new StringBuilder();

        try (
                FileOutputStream fos = new FileOutputStream(outFile);
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(inputFile));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "Windows-1251")
        ) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell;
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue()).append(";");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue()).append(";");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue()).append(";");
                            break;
                        default:
                            data.append(cell).append(";");
                    }
                }
                data.append("\r\n");
            }
            outputStreamWriter.write(data.toString());
            outputStreamWriter.flush();
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private void savePdf(String fileName) throws ServiceException {
        File outFile = new File(fileName + DocumentFormat.PDF.getFormat());
        File inputFile = new File(fileName + DocumentFormat.XLSX.getFormat());

        try (
                FileInputStream fileInputStream = new FileInputStream(inputFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                FileOutputStream fos = new FileOutputStream(outFile)
        ) {
            BaseFont baseFont = BaseFont.createFont(FONT_FILE, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, fos);
            pdfDocument.open();
            PdfPTable table = null;

            PdfPCell tableCell;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                while (cellIterator.hasNext()) {
                    if (table == null) {
                        table = new PdfPTable(iteratorSize(cellIterator));
                        cellIterator = row.iterator();
                    }
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            tableCell = new PdfPCell( new Phrase(String.valueOf(cell.getBooleanCellValue())));
                            table.addCell(tableCell);
                        case Cell.CELL_TYPE_STRING:
                            table.addCell(new Paragraph(cell.getStringCellValue(), font));
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (cell.getNumericCellValue() > 42000) {
                                SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
                                String formattedDate = sf.format(cell.getDateCellValue());
                                tableCell = new PdfPCell(new Phrase(formattedDate));
                                table.addCell(tableCell);
                            } else {
                                table.addCell(String.valueOf(cell.getNumericCellValue()));
                            }
                            break;
                        default:
                            tableCell = new PdfPCell(new Phrase(" "));
                            table.addCell(tableCell);
                    }
                }
            }
            pdfDocument.add(table);
            pdfDocument.close();
        } catch (IOException | DocumentException ex) {
            throw new ServiceException(ex);
        }
    }

    private void createTicket(Ticket ticket) throws ServiceException{
        try (
                FileOutputStream outputStream = new FileOutputStream(TICKET_DOCUMENT);
                XSSFWorkbook workbook = new XSSFWorkbook()
        ) {
            int rowCount = 0;
            createCellStyle(workbook);
            Sheet sheet = workbook.createSheet("Ticket");
            String[] headers = {"Номер билета",
                    "Место",
                    "ФИО клиента",
                    "Номер рейса",
                    "Пункт назначения",
                    "Время отправления",
                    "Дата отправления",
                    "Время прибытия",
                    "Дата прибытия",
                    "Номер самолета",
                    "Цена"};
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[i]);
            }

            fillTicket(ticket, sheet, rowCount, headers.length);
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private void fillTicket(Ticket ticket, Sheet sheet, int rowCount, int tableSize) {
        Row row = sheet.createRow(rowCount++);
        for (int i = 0; i < tableSize; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(defaultStyle);
            StringBuilder cellValue = new StringBuilder();
            switch (i) {
                case 0:
                    cell.setCellValue(ticket.getId().toString());
                    break;
                case 1:
                    cell.setCellValue(ticket.getSeat().getPlace());
                    break;
                case 2:
                    cellValue.append(ticket.getClient().getLastName())
                            .append(" ")
                            .append(ticket.getClient().getFirstName())
                            .append(" ")
                            .append(ticket.getClient().getPatronymic());
                    cell.setCellValue(cellValue.toString());
                    break;
                case 3:
                    cell.setCellValue(ticket.getFlight().getFlightNumber());
                    break;
                case 4:
                    cell.setCellValue(ticket.getFlight().getDestination());
                    break;
                case 5:
                    cell.setCellValue(ticket.getFlight().getDepartureTime().toString());
                    break;
                case 6:
                    cell.setCellStyle(dateStyle);
                    cell.setCellValue(ticket.getFlight().getDepartureDate());
                    break;
                case 7:
                    cell.setCellValue(ticket.getFlight().getArrivalTime().toString());
                    break;
                case 8:
                    cell.setCellStyle(dateStyle);
                    cell.setCellValue(ticket.getFlight().getArrivalDate());
                    break;
                case 9:
                    cell.setCellValue(ticket.getFlight().getPlane().getPrivateNumber());
                    break;
                case 10:
                    cell.setCellValue(ticket.getPrice());
                    break;
                }
            sheet.autoSizeColumn(i);
        }
    }

    private void createAirlineFlights(ArrayList<Flight> flights) throws ServiceException {
        try (
                FileOutputStream outputStream = new FileOutputStream(AIRLINE_FLIGHTS_DOCUMENT);
                XSSFWorkbook workbook = new XSSFWorkbook()
        ) {
            int rowCount = 0;
            createCellStyle(workbook);
            Sheet sheet = workbook.createSheet("Flights");
            String[] headers = {"Номер рейса",
                    "Пункт назначения",
                    "Время отправления",
                    "Дата отправления",
                    "Время прибытия",
                    "Дата прибытия",
                    "Номер самолета",
                    "Пилот"};
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[i]);
            }

            fillFlights(flights, sheet, rowCount, headers.length);
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private void fillFlights(ArrayList<Flight> flights, Sheet sheet, int rowCount, int tableSize) {
        for(Flight flight: flights) {
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < tableSize; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(defaultStyle);
                StringBuilder cellValue = new StringBuilder();
                switch (i) {
                    case 0:
                        cell.setCellValue(flight.getFlightNumber());
                        break;
                    case 1:
                        cell.setCellValue(flight.getDestination());
                        break;
                    case 2:
                        cell.setCellValue(flight.getDepartureTime().toString());
                        break;
                    case 3:
                        cell.setCellStyle(dateStyle);
                        cell.setCellValue(flight.getDepartureDate());
                        break;
                    case 4:
                        cell.setCellValue(flight.getArrivalTime().toString());
                        break;
                    case 5:
                        cell.setCellStyle(dateStyle);
                        cell.setCellValue(flight.getArrivalDate());
                        break;
                    case 6:
                        cell.setCellValue(flight.getPlane().getPrivateNumber());
                        break;
                    case 7:
                        cellValue.append(flight.getPilot().getLastName())
                                .append(" ")
                                .append(flight.getPilot().getFirstName())
                                .append(" ")
                                .append(flight.getPilot().getPatronymic());
                        cell.setCellValue(cellValue.toString());
                }
                sheet.autoSizeColumn(i);
            }
        }
    }


    private void createSchedule(ArrayList<Flight> flights) throws ServiceException {
        try (
                FileOutputStream outputStream = new FileOutputStream(SCHEDULE_DOCUMENT);
                XSSFWorkbook workbook = new XSSFWorkbook()
        ) {
            int rowCount = 0;
            createCellStyle(workbook);
            Sheet sheet = workbook.createSheet("Schedule");
            String[] headers = {"Номер рейса",
                    "Пункт назначения",
                    "Время отправления",
                    "Дата отправления",
                    "Время прибытия",
                    "Дата прибытия",
                    "Номер самолета"};
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[i]);
            }

            fillFlights(flights, sheet, rowCount, headers.length);
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }


    private void createAirlines(ArrayList<Airline> airlines) throws ServiceException {
        try (
                FileOutputStream outputStream = new FileOutputStream(AIRLINES_DOCUMENT);
                XSSFWorkbook workbook = new XSSFWorkbook()
        ) {
            int rowCount = 0;
            createCellStyle(workbook);
            Sheet sheet = workbook.createSheet("Airlines");
            String[] headers = {"Имя",
                    "Логин",
                    "Пароль"};
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[i]);
            }
            fillAirlines(airlines, sheet, rowCount, headers.length);
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private void fillAirlines(ArrayList<Airline> airlines, Sheet sheet, int rowCount, int tableSize) {
        for(Airline airline: airlines) {
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < tableSize; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(defaultStyle);
                StringBuilder cellValue = new StringBuilder();
                switch (i) {
                    case 0:
                        cell.setCellValue(airline.getName());
                        break;
                    case 1:
                        cell.setCellValue(airline.getCredential().getLogin());
                        break;
                    case 2:
                        cell.setCellValue(airline.getCredential().getPassword());
                        break;
                }
                sheet.autoSizeColumn(i);
            }
        }
    }

    private void createAirlinePilots(ArrayList<Pilot> pilots) throws ServiceException {
        try (
                FileOutputStream outputStream = new FileOutputStream(AIRLINE_PILOTS_DOCUMENT);
                XSSFWorkbook workbook = new XSSFWorkbook()
        ) {
            int rowCount = 0;
            createCellStyle(workbook);
            Sheet sheet = workbook.createSheet("Pilots");
            String[] headers = {"ID",
                    "ФИО",
                    "ИИН",
                    "Стаж"};
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[i]);
            }

            fillPilots(pilots, sheet, rowCount, headers.length);
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private void fillPilots(ArrayList<Pilot> pilots, Sheet sheet, int rowCount, int tableSize) {
        for(Pilot pilot: pilots) {
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < tableSize; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(defaultStyle);
                StringBuilder cellValue = new StringBuilder();
                switch (i) {
                    case 0:
                        cell.setCellValue(pilot.getId().toString());
                        break;
                    case 1:
                        cellValue.append(pilot.getLastName())
                                .append(" ")
                                .append(pilot.getFirstName())
                                .append(" ")
                                .append(pilot.getPatronymic());
                        cell.setCellValue(cellValue.toString());
                        break;
                    case 2:
                        cell.setCellValue(pilot.getIin());
                        break;
                    case 3:
                        cell.setCellValue(pilot.getExperience().toString());
                        break;
                }
                    sheet.autoSizeColumn(i);
            }
        }
    }

    public String getAirlinePilots(ArrayList<Pilot> pilots, DocumentFormat format) throws ServiceException {
        createAirlinePilots(pilots);
        return saveGeneratedDocument(DocumentType.AIRLINE_PILOTS,format);
    }

    public String getTicket(Ticket ticket, DocumentFormat format) throws ServiceException {
        createTicket(ticket);
        return saveGeneratedDocument(DocumentType.TICKET,format);
    }

    public String getAirlineFlights(ArrayList<Flight> flights, DocumentFormat format) throws ServiceException {
        createAirlineFlights(flights);
        return saveGeneratedDocument(DocumentType.AIRLINE_FLIGHTS,format);
    }

    public String getPilotSchedule(ArrayList<Flight> flights, DocumentFormat format) throws ServiceException {
        createSchedule(flights);
        return saveGeneratedDocument(DocumentType.SCHEDULE,format);
    }

    public String getAirlines(ArrayList<Airline> airlines, DocumentFormat format) throws ServiceException {
        createAirlines(airlines);
        return saveGeneratedDocument(DocumentType.AIRLINES, format);
    }
}
