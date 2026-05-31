package pl.wsb.fitnesstracker.user.internal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDetailsDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Kontroler REST odpowiedzialny za udostępnianie endpointów HTTP
 * służących do zarządzania użytkownikami w systemie FitnessTracker.
 *
 * <p>Klasa obsługuje operacje CRUD oraz wyszukiwanie użytkowników
 * wymagane w ramach zadania LAB04.</p>
 */
@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Tworzy kontroler użytkowników z wymaganymi zależnościami.
     *
     * @param userService serwis obsługujący logikę użytkowników
     * @param userMapper mapper przekształcający encję User na DTO
     */
    UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Zwraca listę wszystkich użytkowników zapisanych w systemie.
     *
     * <p>Endpoint zwraca szczegółowe dane użytkowników i jest wykorzystywany
     * przez testy integracyjne sprawdzające podstawowe działanie API.</p>
     *
     * @return lista użytkowników w formie DTO ze szczegółowymi danymi
     */
    @GetMapping
    List<UserDetailsDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDetailsDto)
                .toList();
    }

    /**
     * Zwraca podstawowe informacje o wszystkich użytkownikach.
     *
     * <p>Endpoint realizuje wymaganie listowania tylko podstawowych danych:
     * ID, imienia oraz nazwiska użytkownika.</p>
     *
     * @return lista użytkowników w uproszczonej formie DTO
     */
    @GetMapping("/simple")
    List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Zwraca szczegółowe informacje o użytkowniku na podstawie jego ID.
     *
     * @param id identyfikator użytkownika
     * @return szczegółowe dane wybranego użytkownika
     * @throws IllegalArgumentException gdy użytkownik o podanym ID nie istnieje
     */
    @GetMapping("/{id}")
    UserDetailsDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDetailsDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Wyszukuje użytkowników po fragmencie adresu e-mail,
     * bez rozróżniania wielkości liter.
     *
     * <p>Endpoint zwraca jedynie ID oraz adres e-mail użytkownika,
     * zgodnie z wymaganiem funkcjonalnym.</p>
     *
     * @param email fragment adresu e-mail używany do wyszukiwania
     * @return lista użytkowników pasujących do podanego fragmentu e-maila
     */
    @GetMapping("/email")
    List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.findUsersByEmailFragment(email)
                .stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    /**
     * Zwraca użytkowników, których data urodzenia jest wcześniejsza
     * niż podana data.
     *
     * <p>Użytkownicy urodzeni przed wskazaną datą są traktowani
     * jako starsi względem podanego kryterium.</p>
     *
     * @param time data używana jako punkt odniesienia do porównania wieku
     * @return lista użytkowników urodzonych przed podaną datą
     */
    @GetMapping("/older/{time}")
    List<UserDetailsDto> getUsersOlderThan(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time
    ) {
        return userService.findUsersOlderThan(time)
                .stream()
                .map(userMapper::toDetailsDto)
                .toList();
    }

    /**
     * Tworzy nowego użytkownika na podstawie danych przesłanych w treści żądania.
     *
     * @param user dane użytkownika przekazane w formacie JSON
     * @return utworzony użytkownik w formie DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDetailsDto createUser(@RequestBody User user) {
        return userMapper.toDetailsDto(userService.createUser(user));
    }

    /**
     * Aktualizuje dane istniejącego użytkownika.
     *
     * @param userId identyfikator użytkownika, który ma zostać zaktualizowany
     * @param user nowe dane użytkownika przekazane w treści żądania
     * @return zaktualizowany użytkownik w formie DTO
     */
    @PutMapping("/{userId}")
    UserDetailsDto updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userMapper.toDetailsDto(userService.updateUser(userId, user));
    }

    /**
     * Usuwa użytkownika o wskazanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika do usunięcia
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}