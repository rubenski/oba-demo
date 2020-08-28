package nl.codebasesoftware.obademo.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Slf4j
@Controller
public class LogController {

    @PostMapping("/log")
    public void log(@Valid @RequestBody Message message) {
        switch (message.level) {
            case debug:
                log.debug(message.message);
                break;
            case info:
                log.info(message.message);
                break;
            case warn:
                log.warn(message.message);
                break;
            case error:
                log.error(message.message);
                break;
            default:
                throw new RuntimeException("unknown log level");
        }
    }

}
