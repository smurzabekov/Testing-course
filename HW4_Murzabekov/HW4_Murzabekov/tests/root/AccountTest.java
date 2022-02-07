package root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;

    @BeforeEach
    public void initAccount() {
        account = new Account();
    }

    @Test
    void deposit() {
        //Проверка на зачисление средств
        account.deposit(500);
        assertEquals(account.getBalance(), 500);

        //Пополнение баланса заблокированного пользоветеля
        account.block();
        assertFalse(account.deposit(500));

        account.unblock();
        //Пополнение отрицательной суммы на аккаунт
        assertFalse(account.deposit(-100));

        //Пополнение суммы превышающей лимит
        assertFalse(account.deposit(99999999));

        //Пополнение суммы влекающее переполнение баланса аккаунта
        account.balance = 1000000;
        assertFalse(account.deposit(1));
    }

    @Test
    void withdraw() {
        //Проверка на списание средств
        account.deposit(3000);
        account.withdraw(1000);
        assertEquals(account.getBalance(), 2000);

        //Списание средств из заблокированного аккаунта
        account.block();
        assertFalse(account.withdraw(500));

        account.unblock();
        //Списание отрицательной суммы
        assertFalse(account.withdraw(-100));

        //Списание суммы превышающей лимит
        assertFalse(account.withdraw(99999999));

        //Списание суммы влекающее изменение баланса, превосходящее BOUND
        account.balance = 2000000;
        assertFalse(account.withdraw(100));

        //Проверка на снятие со счета всего доступного кредита
        account.balance = 0;
        assertFalse(account.withdraw(2000));
    }

    @Test
    void getBalance() {
        //Проверка баланса нового пользователя
        assertEquals(account.getBalance(), 0);

        //Проверка баланса пользователя после зачисления средств
        account.deposit(220);
        assertEquals(account.getBalance(), 220);
    }

    @Test
    void getMaxCredit() {
        //Проверка кредитного максимума нового пользователя
        assertEquals(account.getMaxCredit(), 1000);
    }

    @Test
    void isBlocked() {
        //Проверка на блок нового пользователя
        assertFalse(account.isBlocked());

        //Проверка заблокированного пользователя
        account.block();
        assertTrue(account.isBlocked());
    }

    @Test
    void block() {
        account.block();
        assertTrue(account.isBlocked());
    }

    @Test
    void unblock() {
        //Разблокировка пользователя
        account.block();
        assertTrue(account.unblock());

        //Разблокировка пользователя с балансом меньшим отрицательного кредитного максимума
        account.balance = -9999;
        assertFalse(account.unblock());
    }

    @Test
    void setMaxCredit() {
        //Изменение кредитного максимума у незаблокированного пользователя
        assertFalse(account.setMaxCredit(3000));

        //Изменение кредитного максимума превышающий лимит
        account.block();
        assertFalse(account.setMaxCredit(9999999));

        //Изменение кредитного максимума
        account.setMaxCredit(2000);
        assertEquals(account.getMaxCredit(), 2000);
    }
}