package converter.service;

import converter.model.Record;
import converter.model.User;
import converter.model.Valute;

import java.time.LocalDate;
import java.util.List;

public interface RecordService {
    void save(Record record);
    List<Record> findAll();
    List<Record> findAllByUser(User user);
    List<Record> findByParams(LocalDate date, Valute fromValute, Valute toValute);
}
