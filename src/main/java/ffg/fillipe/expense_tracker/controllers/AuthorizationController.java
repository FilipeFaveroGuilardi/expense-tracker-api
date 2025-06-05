package ffg.fillipe.expense_tracker.controllers;

import ffg.fillipe.expense_tracker.dto.input.LoginAuthDTO;
import ffg.fillipe.expense_tracker.dto.input.RegisterAuthDTO;
import ffg.fillipe.expense_tracker.dto.output.RetrieveAuthTokenDTO;
import ffg.fillipe.expense_tracker.enums.RoleName;
import ffg.fillipe.expense_tracker.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<RetrieveAuthTokenDTO> login(@RequestBody LoginAuthDTO loginAuthDTO) {
        String token = authorizationService.login(loginAuthDTO);

        return ResponseEntity.ok(new RetrieveAuthTokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterAuthDTO registerAuthDTO) {
        authorizationService.register(registerAuthDTO, RoleName.USER);

        return ResponseEntity.status(201).build();
    }
}
