package profile_service.digitalmoneyhouseprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile_service.digitalmoneyhouseprofile.exception.AccountNotFoundException;
import profile_service.digitalmoneyhouseprofile.model.Account;
import profile_service.digitalmoneyhouseprofile.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account obtenerCuentaPorId(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada con ID: " + id));
    }

    public Account actualizarCuenta(Long id, Account cuentaActualizada) {
        Account cuenta = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada con ID: " + id));

        cuentaActualizada.setId(cuenta.getId());
        return accountRepository.save(cuentaActualizada);
    }
}
