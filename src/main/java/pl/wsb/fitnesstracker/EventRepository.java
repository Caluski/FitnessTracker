package pl.wsb.fitnesstracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.wsb.fitnesstracker.event.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.startDate > :now ORDER BY e.startDate")
    List<Event> findUpcoming(@Param("now") LocalDate now);
}