package converter.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Value")
@Data
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "valute_id")
    private Valute valute;

    public Value() {
    }

    public Value(Double value, LocalDate date) {
        this.value = value;
        this.date = date;
    }
}
