package person;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Person.class)
public interface PersonDao{


    @SqlUpdate("""
            CREATE TABLE person_table (
                         id INTEGER PRIMARY KEY  ,
                         name VARCHAR NOT NULL,
                         birthDate DATE ,
                         gender ENUM('MALE','FEMALE') ,
                         email VARCHAR ,
                         phone VARCHAR ,
                         profession VARCHAR ,
                         married VARCHAR 
            )
            """
    )
    void dbTable();

    @SqlUpdate("INSERT INTO person_table VALUES (:id, :name, :birthdate, :gender, :email, :phone, :profession, :married) ")
    void addPerson(@BindBean Person person);

    @SqlQuery("Select * From person_table Where id= :id")
    Optional<Person> getPerson(@Bind("id") int id);

    @SqlQuery("SELECT * FROM person_table ORDER BY id")
    List<Person> orderById();

    @SqlUpdate("DELETE FROM person_table WHERE id= :id")
    void removePerson(@Bind("id") int id);

}