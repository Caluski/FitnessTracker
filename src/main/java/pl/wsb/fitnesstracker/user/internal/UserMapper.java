package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDetailsDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

/**
 * Komponent odpowiedzialny za mapowanie encji User na obiekty DTO.
 *
 * <p>Dzięki tej klasie kontroler nie zwraca bezpośrednio encji bazodanowej
 * w miejscach, gdzie wymagany jest ograniczony zakres danych.</p>
 */
@Component
class UserMapper {

    /**
     * Mapuje encję User na DTO ze szczegółowymi danymi użytkownika.
     *
     * @param user encja użytkownika
     * @return DTO zawierające szczegółowe dane użytkownika
     */
    UserDetailsDto toDetailsDto(User user) {
        return new UserDetailsDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
    }

    /**
     * Mapuje encję User na uproszczony obiekt DTO.
     *
     * @param user encja użytkownika
     * @return DTO zawierające ID, imię oraz nazwisko użytkownika
     */
    UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    /**
     * Mapuje encję User na DTO wykorzystywane przy wyszukiwaniu po e-mailu.
     *
     * @param user encja użytkownika
     * @return DTO zawierające ID oraz e-mail użytkownika
     */
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(
                user.getId(),
                user.getEmail()
        );
    }
}