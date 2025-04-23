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
    ExpenseRepository expenseRepository;

    @Autowired
    BillRepository billRepository;


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

        List<Bill> bills = billRepository.findAllByUser(user);
        if (bills.isEmpty()) {
            throw new RuntimeException("There are no bills in this user");
        }

        List<BigDecimal> allowes = new ArrayList<>();
        for (Bill b : bills) {
            if (!b.getIs_paid()) {
                allowes.add(b.getAmount());
            }
        }

        BigDecimal total = allowes.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean isaccountbalanceuper = account.getBalance().subtract(total).compareTo(BigDecimal.ZERO) > 0;
        BigDecimal accountbalance = account.getBalance();

        if (existprofit == null) {
            Profit newprofit = new Profit();
            newprofit.setUser(user);
            newprofit.setAmount(isaccountbalanceuper ? accountbalance.subtract(total) : BigDecimal.ZERO);
            newprofit.setCreatedAt(LocalDateTime.now());
            profitRepository.save(newprofit);
        } else {
            existprofit.setAmount(isaccountbalanceuper ? accountbalance.subtract(total) : BigDecimal.ZERO);
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
            throw new RuntimeException(" User not found by this name");
        }
        Account account = accountRepository.findByUser(user);
        if (account == null) {
            throw new RuntimeException(" account not found by this user: " + user);
        } else if (account.getBalance().compareTo(dto.getAmount()) < 0) {
            System.out.println("account balance is less than expense value");
            throw new RuntimeException(" account balance is less than expense value");
        }
        BigDecimal myamount = dto.getAmount();

        Expense expense = new Expense();
        expense.setExpenseType(ExpenseType.valueOf(dto.getExpenseType()));
        expense.setDescription(dto.getExpenseDescription());
        expense.setAmount(myamount);
        expense.setDue_date(LocalDateTime.now());
        expense.setUser(user);
        expenseRepository.save(expense);
        account.setBalance(account.getBalance().subtract(dto.getAmount()));
        account.setLastUpdated(LocalDateTime.now());
        accountRepository.save(account);

        BigDecimal zero = BigDecimal.ZERO;
        Profit profit = profitRepository.findByUser(user);
        List<Bill> bills = billRepository.findAllByUser(user);
        if (bills.isEmpty()) {
            throw new RuntimeException("There are no bills in this user");
        }

        List<BigDecimal> allowes = new ArrayList<>();
        for (Bill b : bills) {
            if (!b.getIs_paid()) {
                allowes.add(b.getAmount());
            }
        }

        BigDecimal total = allowes.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean isaccountbalanceuper = account.getBalance().subtract(total).compareTo(BigDecimal.ZERO) > 0;
        BigDecimal accountbalance = account.getBalance();

        if (profit == null) {
            profit = new Profit();
            profit.setUser(user);
            profit.setAmount(isaccountbalanceuper ? accountbalance.subtract(total) : BigDecimal.ZERO);
            profit.setCreatedAt(LocalDateTime.now());
        } else {
            profit.setAmount(isaccountbalanceuper ? accountbalance.subtract(total) : BigDecimal.ZERO);
            profit.setCreatedAt(LocalDateTime.now());
        }


        Transaction transaction = transactionMapper.toEntity(dto, account);
        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toDTO(saved);

    }


}