package profile_service.digitalmoneyhouseprofile.dto;

public class UpdateUserDTO {

    private String nombre;

    private String apellido;

    private String dni;

    private String email;

    private String telefono;

    private String contrasena;

    private String cvu;

    private String alias;

    public UpdateUserDTO(String nombre, String apellido, String dni, String email, String telefono, String contrasena, String cvu, String alias) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.cvu = cvu;
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCvu() {
        return cvu;
    }

    public String getAlias() {
        return alias;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
