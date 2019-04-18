import demo.Leg;
import demo.LegUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;

public class WordUtils {

    private static final String PATH = "D:\\";
    public static void main(String[] args) throws IOException {
        String filepathString = PATH + "场外衍生品结算通知书_190220.docx";
        String destpathString = PATH + "/2ttt.docx";
        try (FileInputStream fileInputStream = new FileInputStream(filepathString);
             OutputStream os = new FileOutputStream(destpathString);
        ) {
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            replaceDocument(xwpfDocument, "中正有限公司");
            xwpfDocument.write(os);
        }

    }


    private static void replaceDocument(XWPFDocument document,String partyName) {
        Iterator<XWPFTable> tablesIterator = document.getTablesIterator();
        while (tablesIterator.hasNext()) {
            XWPFTable table = tablesIterator.next();
            replaceTable(table, partyName);
        }
    }


    public static XWPFTableRow copy(XWPFTable table, XWPFTableRow sourceRow, int rowIndex) {
        //在表格指定位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(rowIndex);
        //复制行属性
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> cellList = sourceRow.getTableCells();
        if (null == cellList) {
            return targetRow;
        }
        //复制列及其属性和内容
        XWPFTableCell targetCell = null;
        for (XWPFTableCell sourceCell : cellList) {
            targetCell = targetRow.addNewTableCell();
            //列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            //段落属性
            if (sourceCell.getParagraphs() != null && sourceCell.getParagraphs().size() > 0) {
                targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
                if (sourceCell.getParagraphs().get(0).getRuns() != null && sourceCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    //cellR.setText(sourceCell.getText());
                    cellR.setBold(sourceCell.getParagraphs().get(0).getRuns().get(0).isBold());
                } else {
                    //targetCell.setText(sourceCell.getText());
                }
            } else {
                //targetCell.setText(sourceCell.getText());
            }
        }

        return targetRow;
    }

    private static Map<Integer, List<XWPFTableCell>> getCells(XWPFTable table) {
        Map<Integer, List<XWPFTableCell>> map = new LinkedHashMap<>();
        int count = table.getNumberOfRows();//获得表格总行数
        for (int i = 0; i < count; i++) { //遍历表格的每一行
            XWPFTableRow row = table.getRow(i);//获得表格的行
            List<XWPFTableCell> cells = row.getTableCells();//在行元素中，获得表格的单元格
            map.put(i, cells);
        }
        return map;
    }

    private static void replaceTable(XWPFTable table,String partyName) {
        int count = table.getNumberOfRows();//获得表格总行数
        int line = 0;
        int $_line = 0;
        for (int i = 0; i < count; i++) { //遍历表格的每一行
            List<XWPFTableCell> cells = getCells(table).get(i);
            for (XWPFTableCell cell : cells) {   //遍历单元格
                String content = cell.getText();
                if (content.contains("${header}")) {
                    line = i;
                }
                if (content.contains("${leg.id}")) {
                    $_line = i;
                }
            }
        }
        List<Leg> lesg = new ArrayList<>();
        lesg = LegUtils.getlegList(lesg);
        for (Leg leg : lesg) {
            XWPFTableRow originRow = table.getRow($_line);
            XWPFTableRow row1 = copy(table, originRow, $_line + 1);
            Map map2 = LegUtils.getListByParamter(leg);
            for (int i = 0; i < originRow.getTableCells().size(); i++) {
                XWPFTableCell originCell = originRow.getCell(i);
                XWPFTableCell newCell = row1.getCell(i);
                String context = originCell.getText();
                Object value = map2.get(context);
                if (value == null) {
                    continue;
                }
                newCell.setText(value.toString());
                //cell.removeParagraph(0);
            }
        }
        table.removeRow($_line);
        Map map3 = LegUtils.getListByParamter(new Leg());
        for (int i = 0; i < count - 1; i++) { //遍历表格的每一行
            List<XWPFTableCell> cells = getCells(table).get(i);
            for (XWPFTableCell cell : cells) {
                String content = cell.getText();
//                if (content.contains("${header}")) {
//                    // System.out.println(content + "=====================第" + i + "行");
//                    String cont = content.replace("${header}", "");
//                    cell.setText(cont);
//                } else
                if (content.contains("${partyName}")) {
                    String cont = content.replace("${partyName}",partyName );
                    cell.removeParagraph(0);
                    cell.setText(cont);
                }
//                for (Object key : map3.keySet()) {
//                        if(content.contains((String)key)){
//                            System.out.println(content);
////                            content=content.replace((String)key,(String)map3.get(key));
//                      // cell.removeParagraph(0);//所以这里就要求每一个单元格只能有唯一的变量。
//                       cell.setText(content);
//                   }
//            }
            }
        }
    }

}