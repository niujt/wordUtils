import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;

public class WordUtils {

    private static final String PATH = "D:";

    public static void main(String[] args) throws IOException {
        String filepathString = PATH + "/场外衍生品结算通知书_190220.docx";
        String destpathString = PATH + "/2ttt.doc";
        List<String> list=readFile("D:\\wordutils\\src\\main\\resources\\keywords.txt");
        try (FileInputStream fileInputStream = new FileInputStream(filepathString);
             OutputStream os = new FileOutputStream(destpathString);
        ) {
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);

            List<String> list2= preHandle(list);
            replaceDocument(xwpfDocument, list2);

            xwpfDocument.write(os);
        }

    }
    private static List<String> readFile(String propertiespath){
        FileInputStream fis=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        List<String> list=new ArrayList();
        try{
            fis=new FileInputStream("D:\\wordutils\\src\\main\\resources\\keywords.txt");
            isr=new InputStreamReader(fis, "UTF-8");
            br= new BufferedReader(isr);
            String line="";
            String[] arrs=null;
            while ((line=br.readLine())!=null) {
                list.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                br.close();
                isr.close();
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;


    }

    private static List<String> preHandle(List<String> list) {
        List<String> list2 = new ArrayList<>();
        for (String str:list) {
            String pre=str.split("=")[0];
            String suf=str.split("=")[1];
            pre="${"+pre+"}";
            list2.add(pre+"="+suf);
        }
        return list2;
    }

    private static void replaceDocument(XWPFDocument document, List<String> list) {
        Iterator<XWPFParagraph> paragraphsIterator = document.getParagraphsIterator();
        while (paragraphsIterator.hasNext()) {
            XWPFParagraph paragraph = paragraphsIterator.next();
            replaceParagraph(paragraph, list);
        }

        Iterator<XWPFTable> tablesIterator = document.getTablesIterator();
        while (tablesIterator.hasNext()) {
            XWPFTable table = tablesIterator.next();
            replaceTable(table, list);
        }
    }

    private static void replaceParagraph(XWPFParagraph paragraph, List<String> list) {
        for (String str : list) {
            List<XWPFRun> run = paragraph.getRuns();
            for (XWPFRun xwpfRun : run) {
                String text = xwpfRun.getText(xwpfRun.getTextPosition());
                if (StringUtils.contains(text,"{")) {
                    /**
                     * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                     * 就可以把原变量替换掉
                     */
                    xwpfRun.setText(str.split("=")[1], 0);
                }
            }
        }
    }

    private static void replaceTable(XWPFTable table,List<String> list) {
        int count = table.getNumberOfRows();//获得表格总行数
        String partyName=list.get(0).split("=")[1];
        list.remove(list.get(0));
        int index=0;
        for (int i = 0; i < count; i++) { //遍历表格的每一行
            XWPFTableRow row = table.getRow(i);//获得表格的行
            List<XWPFTableCell> cells = row.getTableCells();//在行元素中，获得表格的单元格
            for (int j=0;j<cells.size();j++) {   //遍历单元格
                String content = cells.get(j).getText();
                if(content.contains("partyName")){
                    String cont =content.replace("${partyName}",partyName);
                    cells.get(j).removeParagraph(0);//所以这里就要求每一个单元格只能有唯一的变量。
                    cells.get(j).setText(cont);
                }
                if(content.trim().startsWith("$")){
                    System.out.println(content+"=====第"+(i+1)+"行，第"+(j+1)+"列");
                    String cont = content.replace(content, list.get(j).split("=")[1]);
                    cells.get(j).removeParagraph(0);//所以这里就要求每一个单元格只能有唯一的变量。
                    cells.get(j).setText(cont);
                }
            }
        }
    }

}