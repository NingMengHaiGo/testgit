package com.zhao.deep.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.zhao.deep.bean.Order;
/*import com.zhao.crud.bean.Department;*/
import com.zhao.deep.dao.OrderMapper;

public class ExportExcelUtils {
//	@Autowired
	private OrderMapper orderMapper;
	
	/*public ExportExcelUtils(String title,String[] rowName,List<Object[]>  dataList,HttpServletResponse  response){  
        this.title=title;  
        this.rowName=rowName;  
        this.dataList=dataList;  
        this.response = response;  
    } */
	
	public ExportExcelUtils(OrderMapper orderMapper) {
		super();
		this.orderMapper = orderMapper;
	}
	
	
	public void buildExcelDocument(HttpServletResponse response) throws Exception {
		String title = "Order���";
		String [] rowName = {"���","������","�û�ID","�ջ���ַID","������","��������","�˷�",
				"����״̬","֧��ʱ��","����ʱ��","�������ʱ��","�رս���ʱ��","����ʱ��","����ʱ��"};
		List<Order> dataList = orderMapper.selectByExample(null);
		 HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet =workbook.createSheet(title); // �������  
        // ������������  
        HSSFRow rowm  =sheet.createRow(0);  // ��  
        HSSFCell cellTiltle =rowm.createCell(0);  // ��Ԫ��  
          
        // sheet��ʽ����  
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); // ͷ��ʽ  
        HSSFCellStyle style = this.getStyle(workbook);  // ��Ԫ����ʽ  
        /**  
         * ����˵��  
         * ��0��ʼ   ��һ�� ��һ�� ���ǴӽǱ�0��ʼ  
         * �� �� ����    (0,0,0,5)  �ϲ���һ�� ��һ��  ����һ�� ������  
         * ��ʼ�У���ʼ�У������У�������  
         *   
         * new Region()  �������ʹ��ʱ��  
         */  
        // �ϲ���һ�е�������  
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length-1)));  
        cellTiltle.setCellStyle(columnTopStyle);  
        cellTiltle.setCellValue(title);   
          
        int columnNum = rowName.length;  // ����еĳ���  
        HSSFRow rowRowName = sheet.createRow(1);  // �ڵڶ��д�����  
        HSSFCellStyle cells =workbook.createCellStyle();  
        cells.setBottomBorderColor(HSSFColor.BLACK.index);    
        rowRowName.setRowStyle(cells);  
          
        // ѭ�� �������Ž�ȥ  
        for (int i = 0; i < columnNum; i++) {  
            HSSFCell  cellRowName = rowRowName.createCell((int)i);  
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // ��Ԫ������  
            HSSFRichTextString text = new HSSFRichTextString(rowName[i]);  // �õ��е�ֵ  
            cellRowName.setCellValue(text); // �����е�ֵ  
            cellRowName.setCellStyle(columnTopStyle); // ��ʽ  
        }  
        //�������������  
        for (int j = 0; j < dataList.size(); j++) {  
             HSSFCell  cell = null;   //���õ�Ԫ�����������   
             Order order =  dataList.get(j);
             HSSFRow row = sheet.createRow(j+2);
             cell = row.createCell(0,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getId());                       //���õ�Ԫ���ֵ 
            // cell = row.createCell(1,HSSFCell.CELL_TYPE_STRING);
            // cell.setCellValue(15);
             cell = row.createCell(1,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getOrderNo());
             cell = row.createCell(2,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getUserId());
             cell = row.createCell(3,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getShippingId());
             cell = row.createCell(4,HSSFCell.CELL_TYPE_STRING);
             
             cell.setCellValue(order.getPayment().doubleValue());
             cell = row.createCell(5,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getPaymentType());
             cell = row.createCell(6,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getPostage());
             cell = row.createCell(7,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getStatus());
             cell = row.createCell(8,HSSFCell.CELL_TYPE_STRING);
             
             SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             String PaymentTime = sdf.format(order.getPaymentTime());
             cell.setCellValue(PaymentTime);
             
             cell = row.createCell(9,HSSFCell.CELL_TYPE_STRING);
             String SendTime = sdf.format(order.getSendTime());
             cell.setCellValue(SendTime);
             
             cell = row.createCell(10,HSSFCell.CELL_TYPE_STRING);
             String EndTime = sdf.format(order.getEndTime());
             cell.setCellValue(EndTime);
             
             cell = row.createCell(11,HSSFCell.CELL_TYPE_STRING);
             String CloseTime = sdf.format(order.getCloseTime());
             cell.setCellValue(CloseTime);
             
             cell = row.createCell(12,HSSFCell.CELL_TYPE_STRING);
             String CreateTime = sdf.format(order.getCreateTime());
             cell.setCellValue(CreateTime);
             
             cell = row.createCell(13,HSSFCell.CELL_TYPE_STRING);
             String UpdateTime = sdf.format(order.getUpdateTime());
             cell.setCellValue(UpdateTime);
             /*
             cell = row.createCell(2,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(employee.getGender());
             cell = row.createCell(3,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(employee.getEmail());
             */
        }  
 
        //  ���п����ŵ������г��Զ���Ӧ  
         sheet.setColumnWidth(4, 100 * 256);
         sheet.autoSizeColumn((short)0); //������һ�п��  
         sheet.autoSizeColumn((short)1); //�����ڶ��п��  
         sheet.autoSizeColumn((short)2); //���������п��  
         sheet.autoSizeColumn((short)3); //���������п��  
         sheet.autoSizeColumn((short)4); //���������п��  
         sheet.autoSizeColumn((short)5); //���������п��  
         sheet.autoSizeColumn((short)6); //���������п��  
         sheet.autoSizeColumn((short)7); //���������п��  
         sheet.autoSizeColumn((short)8); //���������п��  
         sheet.autoSizeColumn((short)9); //���������п��  
         sheet.autoSizeColumn((short)10); //���������п��  
         sheet.autoSizeColumn((short)11); //���������п��  
         sheet.autoSizeColumn((short)12); //���������п��  
         sheet.autoSizeColumn((short)13); //���������п��  
         if(workbook !=null){    
                try    
                {    
                    // excel ���ļ���  
                    String fileName = title + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";    
                    String fileName11 = URLEncoder.encode(fileName,"UTF-8");  
                    String headStr = "attachment; filename=\"" + fileName11 + "\"";    
                    response.setContentType("APPLICATION/OCTET-STREAM");    
                    response.setHeader("Content-Disposition", headStr);    
                    OutputStream out = response.getOutputStream();    
                    workbook.write(out);  
                    out.flush();  
                    out.close();  
                }    
                catch (IOException e)    
                {    
                    e.printStackTrace();    
                }   
                  
            }    
    
        }  
   

	public void getdata(){
		 List<Order> dataList = orderMapper.selectByExample(null);
		 List<String[]> datastring = new ArrayList<>();
		 for(int i=0;i<dataList.size();i++){
			 
		 }
	}
    
    
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {    
        
        // ��������    
        HSSFFont font = workbook.createFont();    
        //���������С    
        font.setFontHeightInPoints((short)11);    
        //����Ӵ�    
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    
        //������������     
        font.setFontName("Courier New");    
        //������ʽ;     
        HSSFCellStyle style = workbook.createCellStyle();    
        //���õױ߿�;     
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
        //���õױ߿���ɫ;      
        style.setBottomBorderColor(HSSFColor.BLACK.index);    
        //������߿�;       
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
        //������߿���ɫ;     
        style.setLeftBorderColor(HSSFColor.BLACK.index);    
        //�����ұ߿�;     
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    
        //�����ұ߿���ɫ;     
        style.setRightBorderColor(HSSFColor.BLACK.index);    
        //���ö��߿�;     
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);    
        //���ö��߿���ɫ;      
        style.setTopBorderColor(HSSFColor.BLACK.index);    
        //����ʽ��Ӧ�����õ�����;      
        style.setFont(font);    
        //�����Զ�����;     
        style.setWrapText(false);    
        //����ˮƽ�������ʽΪ���ж���;      
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        //���ô�ֱ�������ʽΪ���ж���;     
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    
            
        return style;    
            
  }    
      
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {    
        // ��������    
        HSSFFont font = workbook.createFont();    
        //���������С    
        //font.setFontHeightInPoints((short)10);    
        //����Ӵ�    
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    
        //������������     
        font.setFontName("Courier New");    
        //������ʽ;     
        HSSFCellStyle style = workbook.createCellStyle();    
        //���õױ߿�;     
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
        //���õױ߿���ɫ;      
        style.setBottomBorderColor(HSSFColor.BLACK.index);    
        //������߿�;       
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
        //������߿���ɫ;     
        style.setLeftBorderColor(HSSFColor.BLACK.index);    
        //�����ұ߿�;     
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    
        //�����ұ߿���ɫ;     
        style.setRightBorderColor(HSSFColor.BLACK.index);    
        //���ö��߿�;     
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);    
        //���ö��߿���ɫ;      
        style.setTopBorderColor(HSSFColor.BLACK.index);    
        //����ʽ��Ӧ�����õ�����;      
        style.setFont(font);    
        //�����Զ�����;     
        style.setWrapText(false);    
        //����ˮƽ�������ʽΪ���ж���;      
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        //���ô�ֱ�������ʽΪ���ж���;     
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    
           
        return style;    
  }  
		
	

}