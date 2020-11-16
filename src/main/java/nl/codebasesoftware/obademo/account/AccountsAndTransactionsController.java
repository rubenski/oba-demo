package nl.codebasesoftware.obademo.account;

import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.account.Account;
import com.obaccelerator.sdk.transaction.Transaction;
import com.obaccelerator.sdk.transaction.TransactionPage;
import lombok.Getter;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AccountsAndTransactionsController {

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
    public DemoTransactionPage findTransactions(@CookieValue(value = "demo-user") UUID userId,
                                                @RequestParam UUID accountId,
                                                @RequestParam int page,
                                                @RequestParam int transactionsPerPage) {
        // Looking back 100 years, because some banks chose to provide really old dates on their mock transactions
        TransactionPage transactionPage = oba.findTransactions(userId, accountId, LocalDate.now().minus(100, ChronoUnit.YEARS),
                LocalDate.now(), page, transactionsPerPage);
        return new DemoTransactionPage(transactionPage);
    }

    // Creating a little bit of custom Demo model here, because we want a different date format for our front-end
    @Getter
    private static class DemoTransactionPage {

        // Dates look like this : 2020-07-31T00:00:00+02:00
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        private final int page;
        private final Integer previousPage;
        private final Integer nextPage;
        private final List<DemoTransaction> transactions;

        public DemoTransactionPage(TransactionPage transactionPage) {
            this.page = transactionPage.getPage();
            this.previousPage = transactionPage.getPreviousPage();
            this.nextPage = transactionPage.getNextPage();
            this.transactions = transactionPage.getTransactions().stream()
                    .map(t -> new DemoTransaction(t, t.getTransactionData().getDate().format(FORMATTER)))
                    .collect(Collectors.toList());
        }
    }

    @Value
    private static class DemoTransaction {
        Transaction transaction;
        String displayDate;
    }
}
