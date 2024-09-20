package ch.elca.backendninja.entity;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creation_datetime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "first_surname", nullable = false)
    private String first_surname;

    @Column(name = "second_surname", nullable = true)
    private String second_surname;

    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Column(name = "time_of_breakfast", nullable = true)
    private Time time_of_breakfast;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_position_id", nullable = true)
    private JobPosition job_position;

    public User() {}

    public User(int id, String username, String password, LocalDateTime creation_datetime, Gender gender, String first_name, String first_surname, String second_surname, Date birthdate, Time time_of_breakfast, JobPosition job_position) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.creation_datetime = creation_datetime;
        this.gender = gender;
        this.first_name = first_name;
        this.first_surname = first_surname;
        this.second_surname = second_surname;
        this.birthdate = birthdate;
        this.time_of_breakfast = time_of_breakfast;
        this.job_position = job_position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreation_datetime() {
        return creation_datetime;
    }

    public void setCreation_datetime(LocalDateTime creation_datetime) {
        this.creation_datetime = creation_datetime;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_surname() {
        return first_surname;
    }

    public void setFirst_surname(String first_surname) {
        this.first_surname = first_surname;
    }

    public String getSecond_surname() {
        return second_surname;
    }

    public void setSecond_surname(String second_surname) {
        this.second_surname = second_surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Time getTime_of_breakfast() {
        return time_of_breakfast;
    }

    public void setTime_of_breakfast(Time time_of_breakfast) {
        this.time_of_breakfast = time_of_breakfast;
    }

    public JobPosition getJob_position() {
        return job_position;
    }

    public void setJob_position(JobPosition job_position) {
        this.job_position = job_position;
    }
}
