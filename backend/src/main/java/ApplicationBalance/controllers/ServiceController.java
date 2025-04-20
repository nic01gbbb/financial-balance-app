package ApplicationBalance.controllers;


import ApplicationBalance.dtos.service.ServiceCreateDTO;
import ApplicationBalance.dtos.user.UserDTO;
import ApplicationBalance.entities.Service;
import ApplicationBalance.services.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public ResponseEntity<?> createService(@RequestBody @Valid ServiceCreateDTO serviceCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        serviceService.ServiceRecord(serviceCreateDTO);
        return ResponseEntity.ok("Service registered successfully!");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/listServices")
    public List<Service> listservices() {
        return serviceService.ListServices();
    }

}
