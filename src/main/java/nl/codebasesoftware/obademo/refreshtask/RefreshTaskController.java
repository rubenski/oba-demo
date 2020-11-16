package nl.codebasesoftware.obademo.refreshtask;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.refreshtask.CreateRefreshTaskRequest;
import com.obaccelerator.sdk.refreshtask.RefreshTask;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class RefreshTaskController {

    private final Oba oba;

    public RefreshTaskController(Oba oba) {
        this.oba = oba;
    }

    @PostMapping("/refresh-tasks")
    public RefreshTask createRefreshTask(@CookieValue(value = "demo-user") UUID userId,
                                         HttpServletRequest request,
                                         @Valid @RequestBody RefreshTaskDetails details) {
        return oba.createRefreshTask(userId,
                new CreateRefreshTaskRequest(details.getConnectionIds(), request.getRemoteAddr()));
    }

    @GetMapping("/refresh-tasks")
    public List<RefreshTask> findRefreshTasks(@CookieValue(value = "demo-user") UUID userId,
                                              @RequestParam(value = "finished", required = false) Boolean finished) {
        if(finished == null) {
            return oba.findRefreshTasks(userId);
        }
        return oba.findRefreshTasks(userId, finished);
    }

    @GetMapping("/refresh-tasks/{refreshTaskId}")
    public RefreshTask findRefreshTask(@CookieValue(value = "demo-user") UUID userId,
                                       @PathVariable UUID refreshTaskId) {
        return oba.findRefreshTask(userId, refreshTaskId);
    }
}
