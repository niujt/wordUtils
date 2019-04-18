import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LegUtils {
    public static String writeInProperties(List<Leg> legs,String propertiespath,String partyName){
        String message="";
        FileWriter fileWriter=null;
        BufferedWriter bw=null;
        try{
            fileWriter=new FileWriter(new File(propertiespath));
            bw=new BufferedWriter(fileWriter);
            List<Map> list=getListByParamter(legs);
            bw.write("partyName="+partyName+"\n");
            for (Map map:list) {
                for (Object key : map.keySet()) {
                    bw.write(low2First(Leg.class.getSimpleName())+"."+key+"="+map.get(key)+"\n");
                    message="success";
                }
                }
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
            try{
                bw.close();
                fileWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return message;
    }
    public static List<Map> getListByParamter(List<Leg> legs){
        List list=new ArrayList();
        for(Leg leg:legs){
            Map map=new LinkedHashMap();
            try{
                Class legclazz=leg.getClass();
                Field[] fields=legclazz.getDeclaredFields();
                for(Field field:fields){
                    field.setAccessible(true);
                    Method method=legclazz.getDeclaredMethod("get"+up2First(field.getName()));
                    Object o=method.invoke(leg);
                    map.put(field.getName(),o);
                }



                list.add(map);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<Leg> getlegList(List<Leg> legs){
        Leg leg=new Leg();
        leg.setDirection("康贝嗯");
        leg.setExpirationDate(LocalDate.of(2018,12,3));
        leg.setHistoryValue(new BigDecimal(12));
        leg.setId(1);
        leg.setInitialValue(new BigDecimal(12));
        leg.setInstrumentName("asdasda");
        leg.setOptionType("看跌");
        leg.setProductType("欧式");
        leg.setRemainValue(new BigDecimal(300));
        leg.setSettleAmount(new BigDecimal(12));
        leg.setSettlementDate(LocalDate.of(2018,2,2));
        leg.setStrike(new BigDecimal(12));
        leg.setTradeId("21212");
        leg.setUnderlyerInstrumentId("600572.SH");
        leg.setUnderlyerPrice(new BigDecimal(12));
        Leg leg2=new Leg();
        leg2.setDirection("dasda");
        leg2.setExpirationDate(LocalDate.of(2018,11,28));
        leg2.setHistoryValue(new BigDecimal(13));
        leg2.setId(2);
        leg2.setInitialValue(new BigDecimal(13));
        leg2.setInstrumentName("czczxcz");
        leg2.setOptionType("看涨");
        leg2.setProductType("美式");
        leg2.setRemainValue(new BigDecimal(400));
        leg2.setSettleAmount(new BigDecimal(16));
        leg2.setSettlementDate(LocalDate.of(2017,11,5));
        leg2.setStrike(new BigDecimal(21));
        leg2.setTradeId("21212");
        leg2.setUnderlyerInstrumentId("600212.SH");
        leg2.setUnderlyerPrice(new BigDecimal(17));
        legs.add(leg);
        legs.add(leg2);
        return legs;
    }

    public static String up2First(String parameter){
            char[] chars=parameter.toCharArray();
            chars[0]-=32;
            return String.valueOf(chars);
    }
    public static String low2First(String parameter){
        char[] chars=parameter.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        List<Leg> legs=new ArrayList<>();
        legs=getlegList(legs);
        System.out.println(writeInProperties(legs,"D:\\wordutils\\src\\main\\resources\\keywords.txt","中正资本有限公司"));
    }

 }
