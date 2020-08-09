package converter.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Valute")
@Data
public class Valute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_code", length = 3, nullable = false)
    private String numCode;

    @Column(name = "char_code", length = 3, nullable = false)
    private String charCode;

    @Column(name = "nominal")
    private Integer nominal;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "valute", fetch = FetchType.LAZY)
    private List<Value> values = new ArrayList<>();;

    @OneToMany(mappedBy = "fromValute", fetch = FetchType.LAZY)
    private List<Record> recordsFromValute = new ArrayList<>();

    @OneToMany(mappedBy = "toValute", fetch = FetchType.LAZY)
    private List<Record> recordsToValute = new ArrayList<>();

    public Valute() {

    }

    public Valute(String numCode, String charCode, Integer nominal, String name) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
    }
}
