import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;

public class WordUtils {

    private static final String PATH = "C:\\Docs\\";

    private static Properties map;

    public static void main(String[] args) throws IOException {
        String filepathString = PATH + "场外衍生品结算通知书_190220.docx";
        String destpathString = PATH + "/2ttt.doc";

        try (InputStream is = WordUtils.class.getClassLoader().getResourceAsStream("keywords.properties")) {
            Properties properties = new Properties();
            InputStreamReader inputStreamReader = new InputStreamReader(is, "GBK");
            properties.load(inputStreamReader);
            map = properties;
        }

        try (FileInputStream fileInputStream = new FileInputStream(filepathString);
             OutputStream os = new FileOutputStream(destpathString);
        ) {
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);

            Map<String, String> map = preHandle(WordUtils.map);
            replaceDocument(xwpfDocument, map);

            xwpfDocument.write(os);
        }

    }

    private static Map<String, String> preHandle(Properties props) {
        Map<String, String> map = new HashMap<>();
        props.forEach((key, value) -> map.put("${" + key + "}", (String) value));
        return map;
    }

    private static void replaceDocument(XWPFDocument document, Map<String, String> map) {
        Iterator<XWPFParagraph> paragraphsIterator = document.getParagraphsIterator();
        while (paragraphsIterator.hasNext()) {
            XWPFParagraph paragraph = paragraphsIterator.next();
            replaceParagraph(paragraph, map);
        }

        Iterator<XWPFTable> tablesIterator = document.getTablesIterator();
        while (tablesIterator.hasNext()) {
            XWPFTable table = tablesIterator.next();
            replaceTable(table, map);
        }
    }

    private static void replaceParagraph(XWPFParagraph paragraph, Map<String, String> map) {
        Set<String> set = map.keySet();
        for (String key : set) {
            List<XWPFRun> run = paragraph.getRuns();
            for (XWPFRun xwpfRun : run) {
                String text = xwpfRun.getText(xwpfRun.getTextPosition());
                System.out.println(text);
                if (StringUtils.contains(text,"{")) {
                    /**
                     * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                     * 就可以把原变量替换掉
                     */
                    xwpfRun.setText(map.get(key), 0);
                }
            }
        }
    }

    private static void replaceTable(XWPFTable table, Map<String, String> map) {
        int count = table.getNumberOfRows();//获得表格总行数
        for (int i = 0; i < count; i++) { //遍历表格的每一行
            XWPFTableRow row = table.getRow(i);//获得表格的行
            List<XWPFTableCell> cells = row.getTableCells();//在行元素中，获得表格的单元格
            for (XWPFTableCell cell : cells) {   //遍历单元格
                for (Map.Entry<String, String> e : map.entrySet()) {
                    String content = cell.getText();
                    String keyWord = e.getKey();
                    if (content.contains(keyWord)) {
                        String cont = content.replace(keyWord, e.getValue());
                        cell.removeParagraph(0);//所以这里就要求每一个单元格只能有唯一的变量。
                        cell.setText(cont);
                    }
                }
            }
        }
    }

}