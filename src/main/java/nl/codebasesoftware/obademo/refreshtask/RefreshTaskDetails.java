package nl.codebasesoftware.obademo.refreshtask;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RefreshTaskDetails {
    List<UUID> connectionIds;
}
