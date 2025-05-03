import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTime {

    private String name;
    private String job;

    public UserTime() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @JsonCreator
    public UserTime(
            // Указываем соответствие с JSON
            @JsonProperty("name") String name,   // Указываем соответствие с JSON
            @JsonProperty("job") String job) {
        this.name = name;
        this.job = job;

    }
}
