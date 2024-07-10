package roomescape.model;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final Long timeId;

    public Reservation(Long id, String name, String date, Long timeId) {
        checkAllFields(id, name, date, timeId);
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    private void checkAllFields(Long id, String name, String date, Long timeId) {
        checkId(id);
        checkName(name);
        checkDate(date);
        checkTimeId(timeId);
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }

    private void checkDate(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date is required");
        }
    }

    private void checkTimeId(Long timeId) {
        if (timeId == null) {
            throw new IllegalArgumentException("Time is required");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
