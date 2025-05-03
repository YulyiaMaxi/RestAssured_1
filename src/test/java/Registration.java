import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Registration {
    private final String email;
    private String password;

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


//public Registration(){}


    @JsonCreator
    public Registration(

            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {

        this.email = email;
        this.password = password;
    }
}











