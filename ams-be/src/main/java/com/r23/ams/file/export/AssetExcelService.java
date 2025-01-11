package com.r23.ams.file.export;

import com.r23.ams.models.entities.Asset;
import com.r23.ams.repositories.AssetRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class AssetExcelService {
    private final AssetRepository assetRepository;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Asset> listExportAssets;

    public AssetExcelService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public void exportAllAsset(HttpServletResponse response) throws IOException {
        workbook = new XSSFWorkbook();
        listExportAssets = assetRepository.findAll();
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("All Assets");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Asset ID", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Category", style);
        createCell(row, 3, "Imei", style);
        createCell(row, 4, "Serial number", style);
        createCell(row, 5, "Inventory number", style);
        createCell(row, 6, "Status", style);
        createCell(row, 7, "Owner", style);
        createCell(row, 8, "User", style);
        createCell(row, 9, "Project", style);
        createCell(row, 10, "Supplier", style);
        createCell(row, 11, "Comment", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Asset asset : listExportAssets) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, asset.getId(), style);
            createCell(row, columnCount++, asset.getName(), style);
            createCell(row, columnCount++, asset.getAssetCategory().getName(), style);
            createCell(row, columnCount++, asset.getImei(), style);
            createCell(row, columnCount++, asset.getSerialNumber(), style);
            createCell(row, columnCount++, asset.getInventoryNumber(), style);
            createCell(row, columnCount++, asset.getAssetStatus().getName(), style);
            createCell(row, columnCount++, asset.getOwnership(), style);
            createCell(row, columnCount++, asset.getAppUser().getEmail(), style);
            createCell(row, columnCount++, asset.getProject().getName(), style);
            createCell(row, columnCount++, asset.getAssetSupplier().getName(), style);
            createCell(row, columnCount++, asset.getComments(), style);
        }
    }

}
