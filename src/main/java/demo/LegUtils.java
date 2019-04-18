package demo;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LegUtils {
    public static Map getListByParamter(Leg leg){
            Map map=new LinkedHashMap();
            try{
                Class legclazz=leg.getClass();
                Field[] fields=legclazz.getDeclaredFields();
                for(Field field:fields){
                    field.setAccessible(true);
                    Method method=legclazz.getDeclaredMethod("get"+up2First(field.getName()));
                    Object o=method.invoke(leg);
                    map.put("${leg."+field.getName()+"}",o);
                }
            }
            catch (Exception e){
                e.printStackTrace();
        }
        return map;
    }

    public static List<Leg> getlegList(List<Leg> legs){
        for(int i=0;i<20;i++){
            Leg leg=new Leg();
            leg.setDirection("康贝嗯"+i);
            leg.setExpirationDate(LocalDate.of(2018,12,3));
            leg.setHistoryValue(new BigDecimal(12));
            leg.setId(1);
            leg.setInitialValue(new BigDecimal(12));
            leg.setInstrumentName("asdasda"+i);
            leg.setOptionType("看跌");
            leg.setProductType("欧式");
            leg.setRemainValue(new BigDecimal(300));
            leg.setSettleAmount(new BigDecimal(12));
            leg.setSettlementDate(LocalDate.of(2018,2,2));
            leg.setStrike(new BigDecimal(12));
            leg.setTradeId("21212"+i);
            leg.setUnderlyerInstrumentId("600572.SH");
            leg.setUnderlyerPrice(new BigDecimal(12));
            legs.add(leg);
        }
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

 }
