package converter.service;

import converter.model.Record;
import converter.model.User;

import java.util.List;

public interface RecordService {
    void save(Record record);
    List<Record> findAll();
    List<Record> findAllByUser(User user);
}
