package converter.repository;

import converter.model.Record;

import converter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepo extends JpaRepository<Record, Long> {
    List<Record> findAllByUser(User user);
}
