import demo.Leg;
import demo.LegUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class WordUtils {
    public static void main(String[] args) throws IOException {
        String filepathString ="D:\\场外衍生品结算通知书_190220.docx";
        String destpathString ="D:\\2ttt.docx";
        try (FileInputStream fileInputStream = new FileInputStream(filepathString);
             OutputStream os = new FileOutputStream(destpathString)
        ) {
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            replaceDocument(xwpfDocument, "中正有限公司");
            xwpfDocument.write(os);
        }
    }


    private static void replaceDocument(XWPFDocument document, String partyName) {
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
        sourceRow.getTableCells().stream().filter(sourceCell->sourceCell!=null).forEach(sourceCell-> {
            XWPFTableCell targetCell = targetRow.addNewTableCell();
            //列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
            if (sourceCell.getParagraphs().get(0).getRuns() != null && sourceCell.getParagraphs().get(0).getRuns().size() > 0) {
                XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                cellR.setBold(sourceCell.getParagraphs().get(0).getRuns().get(0).isBold());
            }
        });
        return targetRow;
    }

    private static int get$Line(XWPFTable table) {
        return table.getRows().stream()
                .filter(xwpfTableRow -> xwpfTableRow.getTableCells().stream()
                        .anyMatch(cell -> cell.getText().contains("${leg.id}")))
                .findAny()
                .map(v -> table.getRows().indexOf(v))
                .orElseThrow(() -> new RuntimeException("can not find the line"));
    }

    private static void replaceTable(XWPFTable table, String partyName) {
        int $_line = get$Line(table);
        table.getRows().forEach(
                xwpfTableRow ->
                        xwpfTableRow.getTableCells().forEach(cell -> {
                            String content = cell.getText();
                            if (content.contains("${partyName}")) {
                                String cont = content.replace("${partyName}", partyName);
                                cell.removeParagraph(0);
                                cell.setText(cont);
                            }
                        })
        );
        List<Leg> legs = LegUtils.getlegList(new ArrayList<>());
        replase$2Value(legs,table,$_line);


    }

    private static void replase$2Value(List<Leg> legs,XWPFTable table,int $_line){
        final int $_line_final = $_line;
        legs.forEach(leg -> {
            XWPFTableRow originRow = table.getRow($_line_final);
            XWPFTableRow row1 = copy(table, originRow, $_line_final + 1);
            Map map2 = LegUtils.getListByParamter(leg);
            originRow.getTableCells().stream()
                    .filter(originCell -> map2.get(originCell.getText()) != null)
                    .forEach(originCell -> {
                        int i = originRow.getTableCells().indexOf(originCell);
                        XWPFTableCell newCell = row1.getCell(i);
                        String context = originCell.getText();
                        Object value = map2.get(context);
                        newCell.setText(value.toString());
                    });
        });
        table.removeRow($_line);
    }

}