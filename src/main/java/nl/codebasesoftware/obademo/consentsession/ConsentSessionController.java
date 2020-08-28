package nl.codebasesoftware.obademo.consentsession;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.consentsession.ConsentSession;
import nl.codebasesoftware.obademo.ObaDemoProperties;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ConsentSessionController {

    private final Oba oba;
    private final ObaDemoProperties obaDemoProperties;

    public ConsentSessionController(Oba oba, ObaDemoProperties obaDemoProperties) {
        this.oba = oba;
        this.obaDemoProperties = obaDemoProperties;
    }

    @PostMapping("/oauth-consent-sessions")
    public ConsentSession createConsentSession(@RequestBody @Valid CreateConsentSessionRequest createConsentSessionRequest,
                                               @CookieValue(value = "demo-user") UUID userId) {
        com.obaccelerator.sdk.consentsession.CreateConsentSessionRequest request = new com.obaccelerator.sdk.consentsession.CreateConsentSessionRequest(
                createConsentSessionRequest.getCountryDataProviderSystemName(),
                String.format("https://%s/user-return", obaDemoProperties.getApplicationDomain()),
                UUID.randomUUID());
        try {
            return oba.createConsentSession(request, userId);
        } catch (Exception e) {
            String test = "";
        }

        return null;
    }

    @PatchMapping("/oauth-consent-sessions/{stateId}")
    public ConsentSession patchConsentSessionWithReturnedUser(@CookieValue(value = "demo-user") UUID userId,
                                                              @RequestBody @Valid UserReturnedUrl userReturnedUrl,
                                                              @PathVariable UUID stateId) {
        return oba.updateConsentSessionWithReturningUser(stateId, userId, userReturnedUrl.getUserReturnedUrl());
    }

    @GetMapping("/oauth-consent-sessions/{stateId}")
    public ConsentSession patchConsentSessionWithReturnedUser(@CookieValue(value = "demo-user") UUID userId,
                                                              @PathVariable UUID stateId) {
        return oba.findConsentSession(userId, stateId);
    }
}
