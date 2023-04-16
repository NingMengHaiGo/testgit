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
		String title = "Order表格";
		String [] rowName = {"编号","订单号","用户ID","收货地址ID","付款金额","付款类型","运费",
				"订单状态","支付时间","发货时间","交易完成时间","关闭交易时间","创建时间","更新时间"};
		List<Order> dataList = orderMapper.selectByExample(null);
		 HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet =workbook.createSheet(title); // 创建表格  
        // 产生表格标题行  
        HSSFRow rowm  =sheet.createRow(0);  // 行  
        HSSFCell cellTiltle =rowm.createCell(0);  // 单元格  
          
        // sheet样式定义  
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); // 头样式  
        HSSFCellStyle style = this.getStyle(workbook);  // 单元格样式  
        /**  
         * 参数说明  
         * 从0开始   第一行 第一列 都是从角标0开始  
         * 行 列 行列    (0,0,0,5)  合并第一行 第一列  到第一行 第六列  
         * 起始行，起始列，结束行，结束列  
         *   
         * new Region()  这个方法使过时的  
         */  
        // 合并第一行的所有列  
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length-1)));  
        cellTiltle.setCellStyle(columnTopStyle);  
        cellTiltle.setCellValue(title);   
          
        int columnNum = rowName.length;  // 表格列的长度  
        HSSFRow rowRowName = sheet.createRow(1);  // 在第二行创建行  
        HSSFCellStyle cells =workbook.createCellStyle();  
        cells.setBottomBorderColor(HSSFColor.BLACK.index);    
        rowRowName.setRowStyle(cells);  
          
        // 循环 将列名放进去  
        for (int i = 0; i < columnNum; i++) {  
            HSSFCell  cellRowName = rowRowName.createCell((int)i);  
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 单元格类型  
            HSSFRichTextString text = new HSSFRichTextString(rowName[i]);  // 得到列的值  
            cellRowName.setCellValue(text); // 设置列的值  
            cellRowName.setCellStyle(columnTopStyle); // 样式  
        }  
        //创建所需的行数  
        for (int j = 0; j < dataList.size(); j++) {  
             HSSFCell  cell = null;   //设置单元格的数据类型   
             Order order =  dataList.get(j);
             HSSFRow row = sheet.createRow(j+2);
             cell = row.createCell(0,HSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(order.getId());                       //设置单元格的值 
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
 
        //  让列宽随着导出的列长自动适应  
         sheet.setColumnWidth(4, 100 * 256);
         sheet.autoSizeColumn((short)0); //调整第一列宽度  
         sheet.autoSizeColumn((short)1); //调整第二列宽度  
         sheet.autoSizeColumn((short)2); //调整第三列宽度  
         sheet.autoSizeColumn((short)3); //调整第四列宽度  
         sheet.autoSizeColumn((short)4); //调整第四列宽度  
         sheet.autoSizeColumn((short)5); //调整第四列宽度  
         sheet.autoSizeColumn((short)6); //调整第四列宽度  
         sheet.autoSizeColumn((short)7); //调整第四列宽度  
         sheet.autoSizeColumn((short)8); //调整第四列宽度  
         sheet.autoSizeColumn((short)9); //调整第四列宽度  
         sheet.autoSizeColumn((short)10); //调整第四列宽度  
         sheet.autoSizeColumn((short)11); //调整第四列宽度  
         sheet.autoSizeColumn((short)12); //调整第四列宽度  
         sheet.autoSizeColumn((short)13); //调整第四列宽度  
         if(workbook !=null){    
                try    
                {    
                    // excel 表文件名  
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
        
        // 设置字体    
        HSSFFont font = workbook.createFont();    
        //设置字体大小    
        font.setFontHeightInPoints((short)11);    
        //字体加粗    
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    
        //设置字体名字     
        font.setFontName("Courier New");    
        //设置样式;     
        HSSFCellStyle style = workbook.createCellStyle();    
        //设置底边框;     
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
        //设置底边框颜色;      
        style.setBottomBorderColor(HSSFColor.BLACK.index);    
        //设置左边框;       
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
        //设置左边框颜色;     
        style.setLeftBorderColor(HSSFColor.BLACK.index);    
        //设置右边框;     
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    
        //设置右边框颜色;     
        style.setRightBorderColor(HSSFColor.BLACK.index);    
        //设置顶边框;     
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);    
        //设置顶边框颜色;      
        style.setTopBorderColor(HSSFColor.BLACK.index);    
        //在样式用应用设置的字体;      
        style.setFont(font);    
        //设置自动换行;     
        style.setWrapText(false);    
        //设置水平对齐的样式为居中对齐;      
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        //设置垂直对齐的样式为居中对齐;     
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    
            
        return style;    
            
  }    
      
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {    
        // 设置字体    
        HSSFFont font = workbook.createFont();    
        //设置字体大小    
        //font.setFontHeightInPoints((short)10);    
        //字体加粗    
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    
        //设置字体名字     
        font.setFontName("Courier New");    
        //设置样式;     
        HSSFCellStyle style = workbook.createCellStyle();    
        //设置底边框;     
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
        //设置底边框颜色;      
        style.setBottomBorderColor(HSSFColor.BLACK.index);    
        //设置左边框;       
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
        //设置左边框颜色;     
        style.setLeftBorderColor(HSSFColor.BLACK.index);    
        //设置右边框;     
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    
        //设置右边框颜色;     
        style.setRightBorderColor(HSSFColor.BLACK.index);    
        //设置顶边框;     
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);    
        //设置顶边框颜色;      
        style.setTopBorderColor(HSSFColor.BLACK.index);    
        //在样式用应用设置的字体;      
        style.setFont(font);    
        //设置自动换行;     
        style.setWrapText(false);    
        //设置水平对齐的样式为居中对齐;      
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        //设置垂直对齐的样式为居中对齐;     
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    
           
        return style;    
  }  
		
	

}