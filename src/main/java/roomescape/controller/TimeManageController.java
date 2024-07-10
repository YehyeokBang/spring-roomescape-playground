package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.TimeDAO;
import roomescape.dto.RequestTime;
import roomescape.model.Time;

import java.util.List;

@RestController
@RequestMapping("times")
public class TimeManageController {
    private final TimeDAO timeDAO;

    public TimeManageController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @PostMapping
    public ResponseEntity<Time> addTime(@RequestBody RequestTime requestTime) {
        Time newTime = timeDAO.saveTime(requestTime.time());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/times/" + newTime.getId())
                .body(newTime);
    }

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(timeDAO.getTimes());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeDAO.deleteTime(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
