import java.io.File;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class ExcelDownloadTest {
	public static void main(String[] args) {
		//config 에서 일어서 처리
		String template_path = "d:\\";
		String temple_filename = "test.xlsx";
		
		File f = new File(template_path+File.separatorChar+temple_filename);
		try {
			Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(f);
			
			XSSFSheet sheet = (XSSFSheet)wb.getSheet("Sheet1");
		    
            XSSFRow row = sheet.getRow(2);
            
            XSSFCell cell0 = row.getCell(0);
            XSSFCell cell = row.getCell(1);
            XSSFCell cell2 = row.getCell(2);
            cell2 = row.createCell(2);
            cell2.setCellValue("2");
            XSSFCell cell3 = row.getCell(3);
            cell2.setCellValue("테스트 데이터");
            XSSFCell cell4 = row.getCell(100);
            
            XSSFRow row1 = sheet.getRow(200);
            
            
            System.out.println("ddd");
            //기존소스에서 다운로드 하는거 처리
            /*
             * 
             * 
             */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
