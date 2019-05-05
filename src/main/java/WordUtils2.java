import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordUtils2 {
    public static void main(String[] args) {
        //获取word文档解析对象
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(POIXMLDocument.openPackage("D:\\test.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置文本中表格内容样式
//---------------------------------------------------------
        List<XWPFTable> xwpfTableList = document.getTables();
        xwpfTableList.forEach(xwpfTable ->{
                        xwpfTable.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE,1,1,"000000");
                        xwpfTable.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE,1,1,"000000");
                        xwpfTable.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE,1,1,"000000");
                        xwpfTable.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE,1,1,"000000");
                        xwpfTable.setRightBorder(XWPFTable.XWPFBorderType.SINGLE,1,1,"000000");
                        xwpfTable.setTopBorder(XWPFTable.XWPFBorderType.SINGLE,1,1,"000000");
        }
//            xwpfTable.getRows().forEach(xwpfTableRow -> {
//                xwpfTableRow.getTableCells().forEach(xwpfTableCell ->
//                    System.out.println(xwpfTableCell.getText())
//                );
//            })
        );
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:\\text2.docx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            document.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
