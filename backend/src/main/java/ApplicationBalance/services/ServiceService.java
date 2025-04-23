package ApplicationBalance.services;

import ApplicationBalance.dtos.service.ServiceCreateDTO;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.ServiceRepository;
import ApplicationBalance.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import ApplicationBalance.entities.Service;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Valid
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UserRepository userRepository;

    public void ServiceRecord(ServiceCreateDTO serviceCreateDTO) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();

        User user = userRepository.findByname(name);
        if (user == null) {
            throw new RuntimeException("User not found by this name: " + name);
        }
        if (serviceRepository.existsByName(serviceCreateDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service already exists");
        }

        Service service = new Service();
        service.setName(serviceCreateDTO.getName());
        service.setPrice(serviceCreateDTO.getPrice());
        service.setDescription(serviceCreateDTO.getDescription());
        service.setAvailable(true);
        service.setUser(user);
        serviceRepository.save(service);
    }

    public List<Service> ListServices() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.toString();

        User user = userRepository.findByname(name);
        if (user == null) {
            throw new RuntimeException("User not found by this name: " + name);
        }

        List<Service> listServices = serviceRepository.findAllByUser(user);
        if (listServices.isEmpty() || listServices.getFirst() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No services found");
        }
        return listServices;
    }
}
