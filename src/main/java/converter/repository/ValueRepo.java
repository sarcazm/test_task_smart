package converter.repository;


import converter.model.Value;
import converter.model.Valute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;


public interface ValueRepo extends JpaRepository<Value, Long> {
    @Query("SELECT MAX(v.date) FROM Value v")
    LocalDate findMaxDate();
    @Query("SELECT v FROM Value v WHERE v.date = ?1 and v.valute = ?2")
    Value findByDateAndValute(LocalDate date, Valute valute);

}
