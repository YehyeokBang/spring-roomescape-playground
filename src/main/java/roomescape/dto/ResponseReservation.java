package roomescape.dto;

public record ResponseReservation(
        Long id,
        String name,
        String date,
        Long timeId
) {
    public static ResponseReservation of(Long id, String name, String date, Long timeId) {
        return new ResponseReservation(id, name, date, timeId);
    }
}
