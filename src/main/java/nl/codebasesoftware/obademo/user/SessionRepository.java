package nl.codebasesoftware.obademo.user;

import com.fasterxml.uuid.Generators;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class SessionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SessionRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public UUID createUserSession(UUID userId) {
        UUID sessionId = Generators.timeBasedGenerator().generate();
        this.namedParameterJdbcTemplate.update(
                "insert into session VALUES (:id, :userId)", new HashMap<String, Object>() {
                    {
                        put("userId", userId.toString());
                        put("id", sessionId.toString());
                    }
                });
        return sessionId;
    }

    public UUID findUserSession(UUID sessionId) {
        return DataAccessUtils.singleResult(this.namedParameterJdbcTemplate.query(
                "select BIN_TO_UUID(id) as sessionId, BIN_TO_UUID(user_id) as userId from session where id = :id",
                new HashMap<String, Object>() {
                    {
                        put("id", sessionId.toString());
                    }
                }, (rs, i) -> {
                    rs.next();
                    return UUID.fromString(rs.getString("user_id"));
                }));
    }
}
