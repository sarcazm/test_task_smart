package converter.service.impl;

import converter.model.Value;
import converter.model.Valute;
import converter.repository.ValueRepo;
import converter.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValueServiceImpl implements ValueService {

    private final ValueRepo valueRepo;

    @Autowired
    public ValueServiceImpl(ValueRepo valueRepo) {
        this.valueRepo = valueRepo;
    }

    @Override
    public LocalDate findMaxDate() {
        return valueRepo.findMaxDate();
    }

    @Override
    public Value add(Value value) {
        return valueRepo.save(value);
    }

    @Override
    public Value findByDateAndValute(LocalDate date, Valute valute) {
        return valueRepo.findByDateAndValute(date, valute);
    }
}
