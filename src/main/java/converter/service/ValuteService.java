package converter.service;

import converter.model.Valute;

import java.util.List;

public interface ValuteService {
    Valute findByCharCode(String charCode);
    Valute add(Valute valute);
    List<Valute> findAll();
}
