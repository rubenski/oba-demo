package nl.codebasesoftware.obademo.consentsession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateConsentSessionRequest {
    private final String countryDataProviderSystemName;

    @JsonCreator
    public CreateConsentSessionRequest(@JsonProperty String countryDataProviderSystemName) {
        this.countryDataProviderSystemName = countryDataProviderSystemName;
    }
}
