package converter.repository;


import converter.model.Value;
import converter.model.Valute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValuteRepo extends JpaRepository<Valute, Long>{
    @Query("SELECT v.charCode FROM Valute v")
    List<String> allCharCode();

    List<Valute> findAll();

    Valute findByCharCode(String charCode);



}
