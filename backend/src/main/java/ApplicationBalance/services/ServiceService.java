package ApplicationBalance.services;

import ApplicationBalance.dtos.service.ServiceCreateDTO;
import ApplicationBalance.repositories.ServiceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ApplicationBalance.entities.Service;

import java.util.List;

@org.springframework.stereotype.Service
@Valid
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    public void ServiceRecord(ServiceCreateDTO serviceCreateDTO) {

        if (serviceRepository.existsByName(serviceCreateDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service already exists");
        }

        Service service = new Service();
        service.setName(serviceCreateDTO.getName());
        service.setPrice(serviceCreateDTO.getPrice());
        service.setDescription(serviceCreateDTO.getDescription());
        serviceRepository.save(service);
    }

public List<Service> ListServices(){
 List<Service>listServices = serviceRepository.findAll();
 if(listServices.isEmpty() || listServices.getFirst() == null ){
     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No services found");
 }
 return listServices;
    }
}
