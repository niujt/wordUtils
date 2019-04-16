import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LegUtils {
    public static List getListByParamter(List<Leg> legs,String paramterName){
        List list=new ArrayList();
        for(Leg leg:legs){
            try{
                Class legclazz=leg.getClass();
                Field field=legclazz.getDeclaredField(paramterName);
                field.setAccessible(true);
                Method method=legclazz.getDeclaredMethod("get"+up2First(paramterName));
                Object o=method.invoke(leg);
                list.add(o);
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

 }
