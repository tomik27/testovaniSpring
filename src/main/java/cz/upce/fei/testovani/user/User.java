package cz.upce.fei.testovani.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//data nemusim pouzivat getter setter
@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(length = 30)
    private String username;
    private String displayname;
    /*
^                 # start-of-string
[a-zA-Z@#$%^&+=]  # first digit letter or special character
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[A-Z])       # an upper case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once
.{8,}             # anything, at least eight places though
[a-zA-Z0-9]       # last digit letter or number
$                 # end-of-string
     */
    //zkontrolovat pomoci regexp
    @Pattern(regexp="^[a-zA-Z@#$%^&+=](?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}[a-zA-Z0-9]$")
    private String password;



}
