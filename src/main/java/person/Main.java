package person;

import com.github.javafaker.Faker;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Main {

    public static  void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try(Handle handle = jdbi.open()) {
            PersonDao dao = handle.attach(PersonDao.class);

            dao.dbTable();

            int i=0;
            var faker = new Faker();
            int numberOfPeople = Integer.parseInt(args[0]);
            while(i < numberOfPeople){

                Date date = faker.date().birthday(1,100);
                LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
                var person = Person.builder()
                        .id(i)
                        .name(faker.name().fullName())
                        .birthdate(localDate)
                        .gender(faker.options().option(Person.Gender.MALE, Person.Gender.FEMALE))
                        .email(faker.internet().emailAddress())
                        .phone(faker.phoneNumber().cellPhone() )
                        .profession(faker.company().profession())
                        .married(faker.bool().bool())
                        .build();

                dao.addPerson(person);

                System.out.println(dao.getPerson(i));

                i++;
            }

            System.out.println("\nThe list of persons ID in ascending order:");
            System.out.println(dao.orderById());

            System.out.println("\nRemoving the Person with ID 2");
            dao.removePerson(2);

            System.out.println("\nThe list of persons ID in ascending order after ID 2 was removed:");
            System.out.println(dao.orderById());

            Person newPerson = createPerson();
            dao.addPerson(newPerson);
            System.out.println(dao.orderById());

        }
    }

    public static Person createPerson(){
        var faker = new Faker();
        Date date = faker.date().birthday(1,100);
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        var person = Person.builder()
                .id(4)
                .name(faker.name().fullName())
                .birthdate(localDate)
                .gender(faker.options().option(Person.Gender.MALE, Person.Gender.FEMALE))
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().cellPhone() )
                .profession(faker.company().profession())
                .married(faker.bool().bool())
                .build();

        return person;
    }

}