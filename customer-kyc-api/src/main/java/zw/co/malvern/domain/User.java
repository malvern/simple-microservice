package zw.co.malvern.domain;



import lombok.Data;
import zw.co.malvern.utils.enums.Title;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Title title;
    private String name;
    private String surname;
    private String dateOfBirth;
    private String jobTitle;
    private String creationDate;
    private String updateDate;
}
