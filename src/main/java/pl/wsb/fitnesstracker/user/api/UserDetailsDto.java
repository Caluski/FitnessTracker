package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;

/**
 * Obiekt DTO reprezentujący szczegółowe dane użytkownika.
 *
 * <p>Wykorzystywany przy pobieraniu pełnych informacji o użytkowniku
 * oraz przy zwracaniu listy użytkowników z pełniejszym zakresem danych.</p>
 *
 * @param id identyfikator użytkownika
 * @param firstName imię użytkownika
 * @param lastName nazwisko użytkownika
 * @param birthdate data urodzenia użytkownika
 * @param email adres e-mail użytkownika
 */
public record UserDetailsDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthdate,
        String email
) {
}