package nl.codebasesoftware.obademo.consentsession;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.consentsession.ConsentSession;
import com.obaccelerator.sdk.consentsession.UserReturnedUrl;
import com.obaccelerator.sdk.exception.BadRequestException;
import lombok.*;
import nl.codebasesoftware.obademo.ObaDemoProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return oba.createConsentSession(request, userId);
    }

    @PatchMapping("/oauth-consent-sessions/{stateId}")
    public ConsentSession patchConsentSessionWithReturnedUser(@CookieValue(value = "demo-user") UUID userId,
                                                              @RequestBody @Valid ConsentSessionController.DemoUserReturnedUrl demoUserReturnedUrl,
                                                              HttpServletRequest httpServletRequest,
                                                              @PathVariable UUID stateId) {
        try {
            UserReturnedUrl userReturnedUrl = new UserReturnedUrl(demoUserReturnedUrl.userReturnedUrl, httpServletRequest.getRemoteAddr());
            return oba.updateConsentSessionWithReturningUser(stateId, userId, userReturnedUrl);
        } catch (BadRequestException e) {
            if (e.getCode().equals("CST008")) {

            }
        }
        return null;
    }

    @GetMapping("/oauth-consent-sessions/{stateId}")
    public ConsentSession getConsentSession(@CookieValue(value = "demo-user") UUID userId,
                                            @PathVariable UUID stateId) {
        return oba.findConsentSession(userId, stateId);
    }

    @Getter
    @Setter
    private static class DemoUserReturnedUrl {
        @NonNull
        String userReturnedUrl;
    }
}
