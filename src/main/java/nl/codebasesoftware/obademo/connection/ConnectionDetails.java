package nl.codebasesoftware.obademo.connection;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ConnectionDetails {

    @NotNull
    private UUID stateId;
}
