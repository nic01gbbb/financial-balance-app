package ApplicationBalance.services;

import ApplicationBalance.dtos.expense.ExpenseCreateDTO;
import ApplicationBalance.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExpenseService {


    @Autowired
    ExpenseRepository expenseRepository;

    public void createExpenseService() {


    }


}
