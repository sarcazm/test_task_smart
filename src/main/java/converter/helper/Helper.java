package converter.helper;

import converter.dom.DomValute;
import converter.model.Record;
import converter.model.User;
import converter.model.Value;
import converter.model.Valute;
import converter.service.ValueService;
import converter.service.ValuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;

@Component
public class Helper {

    private final ValuteService valuteService;
    private final ValueService valueService;
    private final DomValute domValute;

    @Autowired
    public Helper(ValuteService valuteService, ValueService valueService, DomValute domValute) {
        this.valuteService = valuteService;
        this.valueService = valueService;
        this.domValute = domValute;
    }

    public void recordValuteAndValue(Map<Valute, Value> map){
        if (valuteService.findByCharCode("RUB") == null){
            Valute valute = new Valute("000", "RUB", 1, "Российский рубль");
            Value value = new Value(1.0, LocalDate.now());
            value.setValute(valute);
            valueService.add(value);
            valuteService.add(valute);
        }
        boolean isDate = false;
        for (Map.Entry<Valute, Value> entry : map.entrySet()) {
            Valute valute = valuteService.findByCharCode(entry.getKey().getCharCode());
            Value value = entry.getValue();
            if (valute == null){
                valute = valuteService.add(entry.getKey());
            }
            if (isDate || !valueService.findMaxDate().isEqual(value.getDate())){
                isDate = true;
                value.setValute(valute);
                valueService.add(value);
            }

        }
    }

    public Record convert(String from, String to, int qty){
        Valute fromValute = valuteService.findByCharCode(from);
        Valute toValute = valuteService.findByCharCode(to);

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        Double sum;
        LocalDate date = domValute.getDateFromXml();

        Value fromValue = valueService.findByDateAndValute(date, fromValute);
        Value toValue = valueService.findByDateAndValute(date, toValute);
        if (fromValute.getCharCode().equals("RUB")){
            //значит рубль и задаем значение по умолчанию
            fromValue = new Value(1.0, LocalDate.now());
        }
        if (toValute.getCharCode().equals("RUB")){
            sum = fromValue.getValue() * qty / fromValute.getNominal();
        }else {
            sum = (qty * (fromValue.getValue() / fromValute.getNominal()) / (toValue.getValue() / toValute.getNominal()));
        }

        BigDecimal result = new BigDecimal(sum);
        result = result.setScale(3, RoundingMode.HALF_UP);
        return new Record(qty, result.doubleValue(), LocalDate.now(), new User(), fromValute, toValute);
    }
}
