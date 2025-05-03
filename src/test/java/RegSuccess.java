import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegSuccess {
    private static Integer id;

    public static Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        RegSuccess.id = id;
    }

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        RegSuccess.token = token;
    }

    private static String token;


    public RegSuccess() {
    }

    @JsonCreator
    public RegSuccess(
            @JsonProperty("id") Integer id,
            @JsonProperty("token") String token) {

        RegSuccess.id = id;
        RegSuccess.token = token;
    }
}
