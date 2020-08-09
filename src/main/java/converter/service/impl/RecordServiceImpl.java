package converter.service.impl;

import converter.model.Record;
import converter.model.User;
import converter.model.Valute;
import converter.repository.RecordRepo;
import converter.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepo recordRepo;

    @Autowired
    public RecordServiceImpl(RecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }


    @Override
    public void save(Record record) {
        recordRepo.save(record);
    }

    @Override
    public List<Record> findAll() {
        return recordRepo.findAll();
    }

    @Override
    public List<Record> findAllByUser(User user) {
        return recordRepo.findAllByUser(user);
    }

    @Override
    public List<Record> findByParams(LocalDate date, Valute fromValute, Valute toValute) {
        return recordRepo.findByDateAndFromValuteAndToValute(date, fromValute, toValute);
    }
}
