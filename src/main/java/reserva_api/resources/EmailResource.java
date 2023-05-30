package reserva_api.resources;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reserva_api.dtos.EmailDto;
import reserva_api.models.EmailModel;
import reserva_api.services.EmailService;

import java.util.List;

@RestController
@RequestMapping(value = "/email")
public class EmailResource {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailModel> enviarEmail(@RequestBody EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.enviarEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping("/listAll")
    public List<EmailModel> listarTodosEmails() {
        return emailService.listarTodosEmails();
    }

}
