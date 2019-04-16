import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordUtils {

    //        // 返回Docx中需要替换的特殊字符，没有重复项
//        // 推荐传入正则表达式参数"\\$\\{[^{}]+\\}"
//        public ArrayList<String> getReplaceElementsInWord(String filePath,
//                                                          String regex) {
//            String[] p = filePath.split("\\.");
//            if (p.length > 0) {// 判断文件有无扩展名
//                // 比较文件扩展名
//                if (p[p.length - 1].equalsIgnoreCase("doc")) {
//                    ArrayList<String> al = new ArrayList();
//                    File file = new File(filePath);
//                    HWPFDocument document = null;
//                    try {
//                        InputStream is = new FileInputStream(file);
//                        document = new HWPFDocument(is);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Range range = document.getRange();
//                    String rangeText = range.text();
//                    CharSequence cs = rangeText.subSequence(0, rangeText.length());
//                    Pattern pattern = Pattern.compile(regex);
//                    Matcher matcher = pattern.matcher(cs);
//                    int startPosition = 0;
//                    while (matcher.find(startPosition)) {
//                        if (!al.contains(matcher.group())) {
//                            al.add(matcher.group());
//                        }
//                        startPosition = matcher.end();
//                    }
//                    return al;
//                } else if (p[p.length - 1].equalsIgnoreCase("docx")) {
//                    ArrayList<String> al = new ArrayList();
//                    XWPFDocument document = null;
//                    try {
//                        document = new XWPFDocument(
//                                POIXMLDocument.openPackage(filePath));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    // 遍历段落
//                    Iterator<XWPFParagraph> itPara = document
//                            .getParagraphsIterator();
//                    while (itPara.hasNext()) {
//                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
//                        String paragraphString = paragraph.getText();
//                        CharSequence cs = paragraphString.subSequence(0,
//                                paragraphString.length());
//                        Pattern pattern = Pattern.compile(regex);
//                        Matcher matcher = pattern.matcher(cs);
//                        int startPosition = 0;
//                        while (matcher.find(startPosition)) {
//                            if (!al.contains(matcher.group())) {
//                                al.add(matcher.group());
//                            }
//                            startPosition = matcher.end();
//                        }
//                    }
//                    // 遍历表
//                    Iterator<XWPFTable> itTable = document.getTablesIterator();
//                    while (itTable.hasNext()) {
//                        XWPFTable table = (XWPFTable) itTable.next();
//                        int rcount = table.getNumberOfRows();
//                        for (int i = 0; i < rcount; i++) {
//                            XWPFTableRow row = table.getRow(i);
//                            List<XWPFTableCell> cells = row.getTableCells();
//                            for (XWPFTableCell cell : cells) {
//                                String cellText = "";
//                                cellText = cell.getText();
//                                CharSequence cs = cellText.subSequence(0,
//                                        cellText.length());
//                                Pattern pattern = Pattern.compile(regex);
//                                Matcher matcher = pattern.matcher(cs);
//                                int startPosition = 0;
//                                while (matcher.find(startPosition)) {
//                                    if (!al.contains(matcher.group())) {
//                                        al.add(matcher.group());
//                                    }
//                                    startPosition = matcher.end();
//                                }
//                            }
//                        }
//                    }
//                    return al;
//                } else {
//                    return null;
//                }
//            } else {
//                return null;
//            }
//        }
//
//        // 替换word中需要替换的特殊字符
//        public static boolean replaceAndGenerateWord(String srcPath,
//                                                     String destPath, Map<String, Object> map) {
//            String[] sp = srcPath.split("\\.");
//            String[] dp = destPath.split("\\.");
//            if ((sp.length > 0) && (dp.length > 0)) {// 判断文件有无扩展名
//                // 比较文件扩展名
//                if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
//                    try {
//                        XWPFDocument document = new XWPFDocument(
//                                POIXMLDocument.openPackage(srcPath));
//                        // 替换段落中的指定文字
//                        Iterator<XWPFParagraph> itPara = document
//                                .getParagraphsIterator();
//                        while (itPara.hasNext()) {
//                            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
//                            List<XWPFRun> runs = paragraph.getRuns();
//                            for (int i = 0; i < runs.size(); i++) {
//                                String oneparaString = runs.get(i).getText(
//                                        runs.get(i).getTextPosition());
//                                oneparaString = oneparaString.replace("${partyName}", (String)map.get("${partyName}"));
//                                runs.get(i).setText(oneparaString, 0);
//                            }
//                        }
//
//                        // 替换表格中的指定文字
//                        Iterator<XWPFTable> itTable = document.getTablesIterator();
//                        while (itTable.hasNext()) {
//                            XWPFTable table = (XWPFTable) itTable.next();
//                            int rcount = table.getNumberOfRows();
//                            System.out.println(rcount);
//                            for (int i = 0; i < 2; i++) {
//                                XWPFTableRow row = table.getRow(i);
//                                List<XWPFTableCell> cells = row.getTableCells();
//                                for (XWPFTableCell cell : cells) {
//                                    String cellTextString = cell.getText();
//                                    Iterator it=map.entrySet().iterator();
//                                    Object key;
//                                    Object value;
//                                    while(it.hasNext()){
//                                        Map.Entry<String,Object> entry = (Map.Entry<String,Object>)it.next();
//                                        key=entry.getKey();
//                                        value=entry.getValue();
//                                        if(!((String) key).contains("partyName")){
//                                            if (cellTextString.contains((String)key)){
//                                                cellTextString = cellTextString.replace((String)key,((((List<String>)value).get(i))));
//                                            }
//                                        }
//
//
//                                    }
//
//                                    cell.removeParagraph(0);
//                                    cell.setText(cellTextString);
//                                }
//                            }
//                        }
//                        FileOutputStream outStream = null;
//                        outStream = new FileOutputStream(destPath);
//                        document.write(outStream);
//                        outStream.close();
//                        return true;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//
//                } else
//                    // doc只能生成doc，如果生成docx会出错
//                    if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
//                            && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
//                        HWPFDocument document = null;
//                        try {
//                            document = new HWPFDocument(new FileInputStream(srcPath));
//                            Range range = document.getRange();
//                            range.replaceText("${partyName}",(String)map.get("${partyName}"));
//                            FileOutputStream outStream = null;
//                            outStream = new FileOutputStream(destPath);
//                            document.write(outStream);
//                            outStream.close();
//                            return true;
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                            return false;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return false;
//                        }
//                    } else {
//                        return false;
//                    }
//            } else {
//                return false;
//            }
//        }
    public static String find$(String paramter) {
        String regix = "\\$\\{[^{}]+\\}";
        if (paramter.matches(regix)) {
            return paramter;
        } else {
            return null;
        }


    }

    public static List<XWPFTableCell> getCells(String path) {
        List<XWPFTableCell> cells = null;
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(path));
            //获取所有的表格
            List<XWPFTable> xwpfTables = document.getTables();
            for (XWPFTable xwpfTable : xwpfTables) {
                List<XWPFTableRow> rows = xwpfTable.getRows();
                for (int i = 0; i < rows.size(); i++) {
                    cells = rows.get(i).getTableCells();
                    for (XWPFTableCell c : cells) {
                        System.out.print(c.getText() + ",");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cells;
    }

    public static String readWord(String path) {
        try {
            List<String> keyWord = new ArrayList<>();
            int index = 0;
            List<XWPFTableCell> cells = getCells(path);
            for (XWPFTableCell cell : cells) {
                System.out.println(cell.getText());
                if (find$(cell.getText()) != null && cell.getText().startsWith("$")) {
                    System.out.println(cell.getText());
                    keyWord.add(cell.getText());
                }
            }
            index = getIndex(keyWord);
            System.out.println(index);
//                for(XWPFTableCell cell:cells){
//                    if(find$(cell.getText())!=null&&cell.getText().startsWith("$")){
//                        cell.getText()=
//                    }
//                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static int getIndex(List<String> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    index++;
                }
            }
        }
        return index;
    }

    public static Map getMap(List<Leg> legs) {
        Map map = new HashMap();
        Class clazz = Leg.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put("${leg." + field.getName() + "}", LegUtils.getListByParamter(legs, field.getName()));
        }
        return map;

    }

    public static void main(String[] args) {
        String filepathString = "D:/场外衍生品结算通知书_190220.docx";
        String destpathString = "D:/2ttt.doc";
        List<Leg> legs = new ArrayList<>();
        legs = LegUtils.getlegList(legs);
        Map map = getMap(legs);
        map.put("${partyName}", "大苏打");
        //System.out.println(map);
        //System.out.println(find$("$asdasda}"));
        readWord("D:/场外衍生品结算通知书_190220.docx");
    }
}