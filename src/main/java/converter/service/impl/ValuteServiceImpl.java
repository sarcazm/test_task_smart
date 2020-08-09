package converter.service.impl;

import converter.model.Valute;
import converter.repository.ValuteRepo;
import converter.service.ValuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValuteServiceImpl implements ValuteService {

    private final ValuteRepo valuteRepo;
    @Autowired
    public ValuteServiceImpl(ValuteRepo valuteRepo) {
        this.valuteRepo = valuteRepo;
    }

    @Override
    public Valute findByCharCode(String charCode) {
        return valuteRepo.findByCharCode(charCode);
    }

    @Override
    public Valute add(Valute valute) {
        Valute resultSave = valuteRepo.save(valute);
        return resultSave;
    }

    @Override
    public List<Valute> findAll() {
        return valuteRepo.findAll();
    }
}
