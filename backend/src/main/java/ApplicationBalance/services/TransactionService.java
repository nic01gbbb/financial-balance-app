package ApplicationBalance.services;

import ApplicationBalance.dtos.transaction.TransactionCreateDTO;
import ApplicationBalance.dtos.transaction.TransactionResponseDTO;
import ApplicationBalance.entities.*;
import ApplicationBalance.enums.ExpenseType;
import ApplicationBalance.mapper.TransactionMapper;
import ApplicationBalance.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    ProfitRepository profitRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ExpenseRepository expenseRepository;


    @Transactional
    public TransactionResponseDTO createprofit(TransactionCreateDTO dto) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();

        User user = userRepository.findByname(name);
        if (user == null) {
            throw new RuntimeException("User not found by this name: " + name);
        }

        Account account = accountRepository.findByUser(user);

        if (account == null) {
            throw new RuntimeException("Account not found by this user");
        }
        // Cria e salva a transação
        Transaction transaction = transactionMapper.toEntity(dto, account);
        Transaction saved = transactionRepository.save(transaction);

        // Atualiza o saldo da conta
        account.setBalance(account.getBalance().add(dto.getAmount()));
        account.setLastUpdated(LocalDateTime.now());
        accountRepository.save(account);

        Profit existprofit = profitRepository.findByUser(user);
        if (existprofit == null) {
            Profit newprofit = new Profit();
            newprofit.setUser(user);
            newprofit.setAmount(dto.getAmount());
            newprofit.setCreatedAt(LocalDateTime.now());
            profitRepository.save(newprofit);
        } else {
            existprofit.setAmount(existprofit.getAmount().add(dto.getAmount()));
            existprofit.setCreatedAt(LocalDateTime.now());
            profitRepository.save(existprofit);
        }
        return transactionMapper.toDTO(saved);

    }

    public TransactionResponseDTO createexpense(TransactionCreateDTO dto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();
        User user = userRepository.findByname(name);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " User not found by this name: " + name);
        }
        Account account = accountRepository.findByUser(user);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " account not found by this user: " + user);
        } else if (account.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, " account balance is less than expense value: " + account);
        }
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(dto.getAmount());
        expense.setDue_date(LocalDateTime.now());
        expense.setExpenseType(ExpenseType.valueOf(dto.getExpenseType()));
        expenseRepository.save(expense);
        account.setBalance(account.getBalance().subtract(dto.getAmount()));
        account.setLastUpdated(LocalDateTime.now());
        accountRepository.save(account);



        BigDecimal zero = BigDecimal.ZERO;

        List<BigDecimal> myBigDecimalList = new ArrayList<>();
        List<Expense> allExpenses = expenseRepository.findAll();

        for (Expense e : allExpenses) {
            myBigDecimalList.add(e.getAmount());
        }

        BigDecimal totalAmount = myBigDecimalList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Profit profit = profitRepository.findByUser(user);

        if (profit == null) {
            profit  = new Profit();
            profit.setUser(user);
        }

        if (account.getBalance().subtract(totalAmount).compareTo(zero) <= 0) {
            profit.setAmount(zero);
        } else {
            profit.setAmount(account.getBalance().subtract(totalAmount));
        }

        profit.setCreatedAt(LocalDateTime.now());
        profitRepository.save(profit);
        Transaction transaction = transactionMapper.toEntity(dto, account);
        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toDTO(saved);
    }


}
