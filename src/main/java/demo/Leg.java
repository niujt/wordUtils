package demo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Leg {
    private Integer id;
    private String instrumentName;
    private String  underlyerInstrumentId;
    private String direction;
    private String productType;
    private String optionType;
    private BigDecimal strike;
    private LocalDate expirationDate;
    private LocalDate settlementDate;
    private BigDecimal initialValue;
    private BigDecimal historyValue;
    private BigDecimal underlyerPrice;
    private BigDecimal remainValue;
    private BigDecimal settleAmount;
    private String tradeId;

    @Override
    public String toString() {
        return "demo.Leg{" +
                "id=" + id +
                ", instrumentName='" + instrumentName + '\'' +
                ", underlyerInstrumentId='" + underlyerInstrumentId + '\'' +
                ", direction='" + direction + '\'' +
                ", productType='" + productType + '\'' +
                ", optionType='" + optionType + '\'' +
                ", strike='" + strike + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", settlementDate=" + settlementDate +
                ", initialValue=" + initialValue +
                ", historyValue=" + historyValue +
                ", underlyerPrice='" + underlyerPrice + '\'' +
                ", remainValue=" + remainValue +
                ", settleAmount=" + settleAmount +
                ", tradeId='" + tradeId + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getUnderlyerInstrumentId() {
        return underlyerInstrumentId;
    }

    public void setUnderlyerInstrumentId(String underlyerInstrumentId) {
        this.underlyerInstrumentId = underlyerInstrumentId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public BigDecimal getStrike() {
        return strike;
    }

    public void setStrike(BigDecimal strike) {
        this.strike = strike;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getHistoryValue() {
        return historyValue;
    }

    public void setHistoryValue(BigDecimal historyValue) {
        this.historyValue = historyValue;
    }

    public BigDecimal getUnderlyerPrice() {
        return underlyerPrice;
    }

    public void setUnderlyerPrice(BigDecimal underlyerPrice) {
        this.underlyerPrice = underlyerPrice;
    }

    public BigDecimal getRemainValue() {
        return remainValue;
    }

    public void setRemainValue(BigDecimal remainValue) {
        this.remainValue = remainValue;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
