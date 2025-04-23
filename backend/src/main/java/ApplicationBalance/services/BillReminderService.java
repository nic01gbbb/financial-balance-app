package ApplicationBalance.services;

import ApplicationBalance.dtos.bill.BillCreateDTO;
import ApplicationBalance.entities.*;
import ApplicationBalance.enums.ExpenseType;
import ApplicationBalance.enums.TransactionType;
import ApplicationBalance.mapper.TransactionMapper;
import ApplicationBalance.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BillReminderService {


    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
    private TransactionMapper transactionMapper;


    public BillReminderService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Scheduled(cron = "0 0 8 * * *") // Roda todos os dias às 08:00
    public void checkUpcomingBills() {
        LocalDate targetDate = LocalDate.now().plusDays(3);
        List<Bill> bills = billRepository.findByDueDate(targetDate);

        for (Bill bill : bills) {
            System.out.println("⚠️ Conta prestes a vencer: " + bill.getDescription() + " - Vence em " + bill.getDueDate());
            // Aqui você pode enviar notificação, e-mail, etc.
        }
    }

    public void createBill(BillCreateDTO billCreateDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();
        System.out.println(name);
        User user = userRepository.findByname(name);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (billCreateDTO.getDueDate().isBefore(LocalDate.now()) ||
                billCreateDTO.getDueDate().equals(LocalDate.now())) {
            throw new RuntimeException("Due date must be in the future");
        }

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setDescription(billCreateDTO.getDescription());
        bill.setDueDate(LocalDate.now());
        bill.setAmount(billCreateDTO.getAmount());
        bill.setIs_paid(false);
        bill.setDueDate(billCreateDTO.getDueDate());
        billRepository.save(bill);
    }

    public void billisgoingtobepaid(UUID id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();
        User user = userRepository.findByname(name);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (id == null) {
            throw new RuntimeException("uuid is required");
        }
        Bill bill = billRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Bill not found for this user"));

        if (bill == null) {
            throw new RuntimeException("Bill not found");
        }
        if (bill.getIs_paid()) {
            throw new RuntimeException("Bill is already paid");
        }

        Account account = accountRepository.findByUser(user);
        if (account == null) {
            throw new RuntimeException("account not found by this user");
        } else if (account.getBalance().compareTo(bill.getAmount()) < 0) {
            throw new RuntimeException(" account balance is less than expense value");
        }
        bill.setIs_paid(true);

        Expense expense = new Expense();
        expense.setExpenseType(ExpenseType.BILL);
        expense.setDescription("Payment for bill bill");
        expense.setAmount(bill.getAmount());
        expense.setDue_date(LocalDateTime.now());
        expense.setUser(user);
        expenseRepository.save(expense);
        account.setBalance(account.getBalance().subtract(bill.getAmount()));
        account.setLastUpdated(LocalDateTime.now());
        accountRepository.save(account);

        List<Bill> allonBills = billRepository.findAll();
        List<BigDecimal> allowes = new ArrayList<>();
        for (Bill b : allonBills) {
            allowes.add(b.getAmount());
        }

        BigDecimal total = allowes.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal zero = BigDecimal.ZERO;


        Profit profit = profitRepository.findByUser(user);

        if (profit != null) {
            if (account.getBalance().subtract(total).compareTo(zero) <= 0) {
                profit.setAmount(zero);
            } else {
                profit.setAmount(account.getBalance().subtract(total));
            }

            profitRepository.save(profit);
        }
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(bill.getAmount());
        transaction.setDescription(bill.getDescription());
        transaction.setTransactionType(TransactionType.EXPENSE);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<Bill> Listbill() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();
        User user = userRepository.findByname(name);

        List<Bill> listbills = billRepository.findAllByUser(user);
       if(listbills.isEmpty()){
          throw  new RuntimeException("Bill not found");
       }

        return listbills;

    }
}