package nl.codebasesoftware.obademo.consentsession;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserReturnedUrl {
    @NotNull @URL
    private String userReturnedUrl;
}
