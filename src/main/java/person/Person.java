package person;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data


public class Person {

    public enum Gender {
        FEMALE, MALE;

    }

    private int id;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private String email;
    private String phone;
    private String profession;
    private boolean married;

}