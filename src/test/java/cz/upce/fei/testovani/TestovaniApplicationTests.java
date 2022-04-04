package cz.upce.fei.testovani;

import cz.upce.fei.testovani.shared.GenericResponse;
import cz.upce.fei.testovani.user.User;
import cz.upce.fei.testovani.user.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//klienta dostat do kontextu - web environment
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestovaniApplicationTests {


    public static final String API_1_0_USERS = "/api/1.0/users";
    //klienta pro realizaci requestu
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    //database vycisteni, add junit
    //
    @Before
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    void postUser_whenUserIsValid_receiveOK() {
        User user = createUser();

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    //ctrl d ulozeni do database
    @Test
    void postUser_whenUserIsValid_userSavedToDatabase() {
        User user = createUser();

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertThat(userRepository.count()).isEqualTo(1);

    }
    @Test
    void postUser_whenUserIsValid_receiveSuccessMessage() {
        User user = createUser();

        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
        assertThat(response.getBody().getMessage()).isNotNull();
    }
    @Test
    void postUser_whenUserIsValid_passwordIsHashedInDatabase() {
        User user = createUser();

        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
        List<User> users = userRepository.findAll();
        User inDB = users.get(0);

        assertThat(inDB.getPassword()).isNotEqualToIgnoringCase(user.getPassword());
    }    @Test
    void postUser_whenUserHasNullUsername_receiveBadRequest() {
        User user = createUser();
        user.setUsername(null);

        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void postUser_whenUserHasNullDisplayName_receiveBadRequest() {
        User user = createUser();
        user.setDisplayname(null);

        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }    @Test
    void postUser_whenUserHasNullPassword_receiveBadRequest() {
        User user = createUser();
        user.setPassword(null);

        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

 //TODO pomoci dalsich constraint z odkazu https://beanvalidation.org/2.0/spec/ osetrete dalsi
    //vhodne hodnoty - napr min a max delku uzivatelskeho jmena, slozeni hesla apod
    //co se tyka uvazovanych delek jmena i heso, tak uvazujte s limitem v DB

    @Test
    void postUser_whenUserNotUseMinRequirementsForPassword_receiveBadRequest() {
        User user = createUser();
        user.setPassword("Heslo");

        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void postUser_whenUsernameHas40length_receiveBadRequest() {
        User user = createUser();
        user.setUsername("ReallyLongNameReallyLongNameReallyLongNameReallyLongNameReallyLongNameReallyLongNameReallyLongNameReallyLongName");
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


//extract method
    private User createUser() {
        User user = new User();
        user.setUsername ("test-user");
        user.setDisplayname ("test-user");
        user.setPassword("P4assword");
        return user;
    }
}
