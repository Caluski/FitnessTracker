package pl.wsb.fitnesstracker.user.api;

/**
 * Uproszczony obiekt DTO reprezentujący podstawowe dane użytkownika.
 *
 * <p>Wykorzystywany przy listowaniu użytkowników, gdy potrzebne są
 * jedynie podstawowe informacje: ID, imię oraz nazwisko.</p>
 *
 * @param id identyfikator użytkownika
 * @param firstName imię użytkownika
 * @param lastName nazwisko użytkownika
 */
public record UserSimpleDto(Long id, String firstName, String lastName) {
}