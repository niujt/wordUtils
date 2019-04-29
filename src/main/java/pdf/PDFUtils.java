package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class PDFUtils {
        public static void main(String[] args) {
            test1_1();
        }
    public static void test1_1(){
        BaseFont bf;
        Font font = null;
        try {
            bf = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);//创建字体
            font = new Font(bf,12);//使用字体
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("D:/测试3.pdf"));
            document.open();
            document.add(new Paragraph("hello word 你好 世界",font));//引用字体
            PdfPTable pdfPTable=new PdfPTable(4);
            createCell("xxx检查单",4,pdfPTable,font,false);
            createCell("名称:         ",1,pdfPTable,font,false);
            createCell("型号:         ",1,pdfPTable,font,false);
            createCell("序号:         ",2,pdfPTable,font,false);
            createCell("地点:         ",1,pdfPTable,font,false);
            createCell("日期:         ",1,pdfPTable,font,false);
            createCell("单号:         ",2,pdfPTable,font,false);
            createCell("检查类型:         ",1,pdfPTable,font,false);
            createCell("",1,pdfPTable,font,false);
            createCell("aaa",1,pdfPTable,font,false);
            createCell("没了",2,pdfPTable,font,false);
            document.add(pdfPTable);
            document.close();
        } catch (Exception e) {
            System.out.println("file create exception");
        }
    }
    public static void createCell(String text,int colspan,PdfPTable pdfPTable,Font font,boolean flag){
            if(!flag){
                PdfPCell cell=new PdfPCell(new Paragraph(text,font));
                cell.setColspan(colspan);
                pdfPTable.addCell(cell);
            }
            else {
                System.out.println(111);
                PdfPCell cell=new PdfPCell(getCheck());
                pdfPTable.addCell(cell);
            }


    }
    public static PdfPTable getCheck(){
            PdfPCell checkCell =null;
            PdfPTable checkTable=new PdfPTable(1);
            checkTable.setWidthPercentage(2);
            checkCell=new PdfPCell();
            checkCell.setMinimumHeight(5f);
            checkCell.setBorder(Rectangle.BOX);
            checkCell.setBorderWidth(1);
            checkCell.setFixedHeight(2f);
            checkTable.addCell(checkCell);
            return checkTable;
    }
}
