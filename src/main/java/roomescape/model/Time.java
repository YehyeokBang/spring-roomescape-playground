package roomescape.model;

public class Time {
    private static final String TIME_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
    private Long id;
    private String time;

    public Time(Long id, String time) {
        checkTime(time);
        this.id = id;
        this.time = time;
    }

    private void checkTime(String time) {
        if (time == null || !time.matches(TIME_REGEX)) {
            throw new IllegalArgumentException("time format is not correct");
        }
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
