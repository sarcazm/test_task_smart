package converter.service;

import converter.model.Value;
import converter.model.Valute;

import java.time.LocalDate;

public interface ValueService {
    LocalDate findMaxDate();
    Value add(Value value);
    Value findByDateAndValute(LocalDate date, Valute valute);
}
