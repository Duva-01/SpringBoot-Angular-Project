package ch.elca.backendninja.model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public class UserModel {

    private int id;
    private String username;
    private String password; 

    private LocalDateTime creation_datetime;
    private GenderModel gender;
    private String first_name;
    private String first_surname;
    private String second_surname;

    private Date birthdate;
    private Time time_of_breakfast;

    private JobPositionModel job_position;

    public UserModel() {}

    public UserModel(int id, String username, String password, LocalDateTime creation_datetime, GenderModel gender, String first_name, String first_surname, String second_surname, Date birthdate, Time time_of_breakfast, JobPositionModel job_position) {
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

    public GenderModel getGender() {
        return gender;
    }

    public void setGender(GenderModel gender) {
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

    public JobPositionModel getJob_position() {
        return job_position;
    }

    public void setJob_position(JobPositionModel job_position) {
        this.job_position = job_position;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", creation_datetime=" + creation_datetime +
                ", gender=" + gender +
                ", first_name='" + first_name + '\'' +
                ", first_surname='" + first_surname + '\'' +
                ", second_surname='" + second_surname + '\'' +
                ", birthdate=" + birthdate +
                ", time_of_breakfast=" + time_of_breakfast +
                ", job_position=" + job_position +
                '}';
    }
}
