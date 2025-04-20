package ApplicationBalance.controllers;

import ApplicationBalance.dtos.transaction.TransactionCreateDTO;
import ApplicationBalance.dtos.transaction.TransactionResponseDTO;
import ApplicationBalance.repositories.ExpenseRepository;
import ApplicationBalance.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
@Valid
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ExpenseRepository expenseRepository;


    @PostMapping("/createprofit")
    public ResponseEntity<?> TransactionProfits(@RequestBody @Valid TransactionCreateDTO transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        } else if (!transaction.getTransactionType().equals("INCOME") && !transaction.getTransactionType().equals("EXPENSE")) {
            return ResponseEntity.badRequest().body("Transaction Type is not valid");
        }

        TransactionResponseDTO transactionCreateDTO = transactionService.createprofit(transaction);
        return ResponseEntity.ok(transactionCreateDTO);
    }


    @PostMapping("/createexpense")
    public ResponseEntity<?> createExpense(@RequestBody @Valid TransactionCreateDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        TransactionResponseDTO response = transactionService.createexpense(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
