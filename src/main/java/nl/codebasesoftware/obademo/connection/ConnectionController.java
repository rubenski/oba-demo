package nl.codebasesoftware.obademo.connection;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.connection.Connection;
import com.obaccelerator.sdk.connection.CreateConnectionRequest;
import com.obaccelerator.sdk.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class ConnectionController {

    private final Oba oba;

    public ConnectionController(Oba oba) {
        this.oba = oba;
    }

    @PostMapping("/connections")
    public Connection createConnection(@CookieValue(value = "demo-user") UUID userId,
                                       @Valid @RequestBody ConnectionDetails connectionDetails) {
        return oba.createConnection(userId, new CreateConnectionRequest(connectionDetails.getStateId()));
    }

    @GetMapping("/connections")
    public List<Connection> findConnections(@CookieValue(value = "demo-user") UUID userId) {
        return oba.findConnections(userId);
    }

    @GetMapping("/connections/{connectionId}")
    public ResponseEntity<Connection> findConnection(@CookieValue(value = "demo-user") UUID userId,
                                                     @PathVariable UUID connectionId) {
        try {
            return new ResponseEntity<>(oba.findConnection(userId, connectionId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
