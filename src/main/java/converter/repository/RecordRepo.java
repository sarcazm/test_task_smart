package converter.repository;

import converter.model.Record;

import converter.model.User;
import converter.model.Valute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepo extends JpaRepository<Record, Long> {
    List<Record> findAllByUser(User user);

    List<Record> findByDateAndFromValuteAndToValute(LocalDate date, Valute fromValute, Valute toValute);
}
