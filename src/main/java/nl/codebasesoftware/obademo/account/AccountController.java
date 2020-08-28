package nl.codebasesoftware.obademo.account;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.account.Account;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    private final Oba oba;

    public AccountController(Oba oba) {
        this.oba = oba;
    }

    @GetMapping("/connections/{connectionId}/accounts")
    public List<Account> findAccountsForConnection(@CookieValue(value = "demo-user") UUID userId,
                                                   @PathVariable UUID connectionId) {
        return oba.findAccounts(userId, connectionId);
    }
}
