package ApplicationBalance.mapper;


import ApplicationBalance.dtos.transaction.TransactionCreateDTO;
import ApplicationBalance.dtos.transaction.TransactionResponseDTO;
import ApplicationBalance.entities.Account;
import ApplicationBalance.entities.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {


        public Transaction toEntity(TransactionCreateDTO dto, Account account) {
            Transaction transaction = new Transaction();
            transaction.setAmount(dto.getAmount());
            transaction.setDescription(dto.getDescription());
            transaction.setType(dto.getType());
            transaction.setAccount(account);
            transaction.setCreatedAt(LocalDateTime.now());
            return transaction;
        }

        public TransactionResponseDTO toDTO(Transaction transaction) {
            TransactionResponseDTO dto = new TransactionResponseDTO();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());
            dto.setDescription(transaction.getDescription());
            dto.setCreatedAt(transaction.getCreatedAt());
            dto.setType(transaction.getType());
            return dto;
        }
    }


