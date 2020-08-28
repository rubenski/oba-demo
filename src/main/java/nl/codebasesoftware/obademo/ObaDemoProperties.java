package nl.codebasesoftware.obademo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
@ConfigurationProperties("obademo")
public class ObaDemoProperties {

    @Value("${obademo.mysql.user}")
    private String mysqlUser;

    @Value("${obademo.mysql.password}")
    private String mysqlPassword;

    @Value("${obademo.mysql.name}")
    private String dbName;

    @Value("${obademo.mysql.url}")
    private String dbUrl;

    @Value("${obademo.application.domain}")
    private String applicationDomain;

    @Value("${server.servlet.context-path}")
    private String applicationContextPath;

    @Value("${obademo.cookie.name}")
    private String cookieName;
}
