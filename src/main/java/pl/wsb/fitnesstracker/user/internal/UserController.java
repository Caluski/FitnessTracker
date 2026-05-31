package pl.wsb.fitnesstracker.user.internal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;

    UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/simple")
    List<User> getAllSimpleUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @GetMapping("/email")
    List<User> getUserByEmail(@RequestParam String email) {
        return userService.findUsersByEmailFragment(email);
    }

    @GetMapping("/older/{time}")
    List<User> getUsersOlderThan(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time
    ) {
        return userService.findUsersOlderThan(time);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}