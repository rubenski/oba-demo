package nl.codebasesoftware.obademo.account;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.account.Account;
import com.obaccelerator.sdk.account.TransactionPage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountsAndTransactionsController {

    private static final int TRANSACTIONS_PER_PAGE = 100;

    private final Oba oba;

    public AccountsAndTransactionsController(Oba oba) {
        this.oba = oba;
    }

    @GetMapping("/accounts")
    public List<Account> findAccountsForConnection(@CookieValue(value = "demo-user") UUID userId,
                                                   @RequestParam("connectionId") UUID connectionId) {
        return oba.findAccounts(userId, connectionId);
    }

    @GetMapping("/accounts/{accountId}")
    public Account findAccount(@CookieValue(value = "demo-user") UUID userId,
                               @PathVariable UUID accountId) {
        return oba.findAccount(userId, accountId);
    }

    @GetMapping("/transactions")
    public TransactionPage findTransaction(@CookieValue(value = "demo-user") UUID userId,
                                           @RequestParam UUID accountId,
                                           @RequestParam int page) {
        // Looking back 50 years, because some banks chose to provide really old dates on their mock transactions
        return oba.findTransactions(userId, accountId, LocalDate.now().minus(100, ChronoUnit.YEARS),
                LocalDate.now(), page, TRANSACTIONS_PER_PAGE);
    }
}
