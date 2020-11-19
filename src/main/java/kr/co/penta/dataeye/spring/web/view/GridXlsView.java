package kr.co.penta.dataeye.spring.web.view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import kr.co.penta.dataeye.common.util.CastUtils;

/**
 * @author
 * @function Excel DownLoad
 * @search Spring AbstractExcelView, Spring + Jakarta POI
 */
@Resource(name="gridXlsView")
@Component
public class GridXlsView extends AbstractXlsView {
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    private String fileName;
    private Map<String, Object> gridParam;
    
    protected GridXlsView() {}
    
    public GridXlsView(String fileName, Map<String, Object> gridParam) {
        this.fileName = fileName;
        this.gridParam = gridParam;
    }
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("grid export data");
        CellStyle headerCellStyle = getCellStyle(workbook, IndexedColors.BLACK.getIndex(), IndexedColors.AQUA.getIndex());
        CellStyle dataCellStyle = getCellStyle(workbook, IndexedColors.BLACK.getIndex(), IndexedColors.WHITE.getIndex());
        
        List<Map<String, Object>> colModel = (List<Map<String, Object>>)gridParam.get("colModel");
        // create header row
        Row header = sheet.createRow(0);
        int colIndex = 0;
        List<Map<String, Object>> headerCol = new ArrayList<>();
        
        for(Map<String, Object> colInfo : colModel) {
            String name = CastUtils.toString(colInfo.get("name"));
            boolean hidden = CastUtils.toBoolean(colInfo.get("hidden"));
            if ("rn".equals(name) || "CHK".equals(name) || hidden) {
                continue;
            }
            String label = CastUtils.toString(colInfo.get("label"));
            Integer width = CastUtils.toInteger(colInfo.get("width"));
            width = width*37;
            headerCol.add(colInfo);
            
            sheet.setColumnWidth(colIndex, width);
            header.createCell(colIndex).setCellValue(label);
            header.getCell(colIndex++).setCellStyle(headerCellStyle);
        }
        
        List<Map<String, Object>> data = (List<Map<String, Object>>)gridParam.get("data");
        int rowCount = 1;
        for(Map<String, Object> rowInfo : data) {
            Row row = sheet.createRow(rowCount++);
            colIndex = 0;
            for(Map<String, Object> colInfo : headerCol) {
                String index = CastUtils.toString(colInfo.get("index"));
                if ("".equals(index)) {
                    index = CastUtils.toString(colInfo.get("name"));
                }
                String stype = CastUtils.toString(colInfo.get("stype"));
                if ("text".equals(stype)) {
                    row.createCell(colIndex).setCellValue(CastUtils.toString(rowInfo.get(index)));
                } else {
                    row.createCell(colIndex).setCellValue(CastUtils.toString(rowInfo.get(index)));
                }
                row.getCell(colIndex++).setCellStyle(dataCellStyle);
            }
        }
    }
    
    private CellStyle getCellStyle(Workbook workbook, short borderColor , short foregroundColor) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(borderColor);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(borderColor);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(borderColor);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(borderColor);
        
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(foregroundColor);
        
        return cellStyle;
    }
}