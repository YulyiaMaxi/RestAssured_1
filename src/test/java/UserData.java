import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {
    private String id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    // Конструктор по умолчанию
    public UserData() {
    }


    @JsonCreator
    public UserData(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("first_name") String first_name, // Указываем соответствие с JSON
            @JsonProperty("last_name") String last_name,   // Указываем соответствие с JSON
            @JsonProperty("avatar") String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}


