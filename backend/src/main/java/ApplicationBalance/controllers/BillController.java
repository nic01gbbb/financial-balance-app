package ApplicationBalance.controllers;


import ApplicationBalance.dtos.bill.BillCreateDTO;
import ApplicationBalance.entities.Bill;
import ApplicationBalance.services.BillReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillReminderService billReminderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createBill")
    public ResponseEntity<?> createBill(@RequestBody @Valid BillCreateDTO billCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }
        billReminderService.createBill(billCreateDTO);
        return ResponseEntity.ok().body(billCreateDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/paidbill")
    public ResponseEntity<?> paidBill(@RequestParam UUID id) {
        try {
            billReminderService.billisgoingtobepaid(id);
            return ResponseEntity.ok().body("Paid Bill Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listbills")
    public ResponseEntity<?> listbills() {
        try {
            List<Bill> listbill = billReminderService.Listbill();
            return ResponseEntity.ok().body(listbill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
