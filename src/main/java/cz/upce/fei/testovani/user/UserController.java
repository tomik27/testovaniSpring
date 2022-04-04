package cz.upce.fei.testovani.user;

import cz.upce.fei.testovani.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    GenericResponse createUser(@RequestBody User user) throws UserNotValidException {
        if(user.getUsername()==null) {
            throw new UserNotValidException();
        }
        userService.save(user);
        return new GenericResponse("user saved");
    }
}
