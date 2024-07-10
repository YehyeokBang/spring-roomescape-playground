package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dao.TimeDAO;
import roomescape.dto.RequestTime;
import roomescape.model.Time;

import java.util.List;

@Controller
public class TimeManageController {
    private final TimeDAO timeDAO;

    public TimeManageController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> addTime(@RequestBody RequestTime requestTime) {
        Time newTime = timeDAO.saveTime(requestTime.time());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/times/" + newTime.getId())
                .body(newTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(timeDAO.getTimes());
    }

    @DeleteMapping("times/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeDAO.deleteTime(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}
