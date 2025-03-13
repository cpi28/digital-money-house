package profile_service.digitalmoneyhouseprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profile_service.digitalmoneyhouseprofile.model.Account;
import profile_service.digitalmoneyhouseprofile.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService cuentaService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> obtenerCuenta(@PathVariable Long id) {
        Account cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Account> actualizarCuenta(@PathVariable Long id, @RequestBody Account cuentaActualizada) {
        Account cuenta = cuentaService.actualizarCuenta(id, cuentaActualizada);
        return ResponseEntity.ok(cuenta);
    }
}
