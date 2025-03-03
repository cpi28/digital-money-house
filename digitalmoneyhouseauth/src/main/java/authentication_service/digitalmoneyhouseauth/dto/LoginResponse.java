package authentication_service.digitalmoneyhouseauth.dto;


/**
 * DTO para responder con el token de autenticación.
 */
public class LoginResponse {

    private String token;

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

