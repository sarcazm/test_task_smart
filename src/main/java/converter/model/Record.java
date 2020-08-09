package converter.model;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Record")
@Data
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "qty")
    private Integer qty;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "from_valute_id")
    private Valute fromValute;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "to_valute_id")
    private Valute toValute;

    public Record() {
    }

    public Record(Integer qty, Double sum, LocalDate date, User user, Valute fromValute, Valute toValute) {
        this.qty = qty;
        this.sum = sum;
        this.date = date;
        this.user = user;
        this.fromValute = fromValute;
        this.toValute = toValute;
    }
}
