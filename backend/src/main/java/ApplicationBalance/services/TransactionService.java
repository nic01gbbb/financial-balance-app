package ApplicationBalance.services;
import ApplicationBalance.dtos.transaction.TransactionCreateDTO;
import ApplicationBalance.dtos.transaction.TransactionResponseDTO;
import ApplicationBalance.entities.Account;
import ApplicationBalance.entities.Transaction;
import ApplicationBalance.entities.User;
import ApplicationBalance.mapper.TransactionMapper;
import ApplicationBalance.repositories.AccountRepository;
import ApplicationBalance.repositories.TransactionRepository;
import ApplicationBalance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.UUID;

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


    public TransactionResponseDTO createprofit(TransactionCreateDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername(); // ou userDetails.getEmail() se tiver customizado
        User user = userRepository.findByEmail(email);
        UUID userId = user.getId();

        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found by this user"));


        Transaction transaction = transactionMapper.toEntity(dto, account);
        Transaction saved = transactionRepository.save(transaction);

        return transactionMapper.toDTO(saved);

    }
}
