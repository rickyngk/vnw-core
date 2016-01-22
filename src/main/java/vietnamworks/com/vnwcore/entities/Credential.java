package vietnamworks.com.vnwcore.entities;

/**
 * Created by duynk on 1/22/16.
 */
public class Credential {
    String email;
    String password;
    public Credential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}