import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTimeNew extends UserTime {
    private String updatedAt;

    public UserTimeNew(String name, String job, String updateAt) {
        super(name, job); // конструктор для наследника, создаем автоматически

    }

    public String getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updatedAt = updateAt;
    }


    public UserTimeNew() {
    }


    @JsonCreator
    public UserTimeNew(
            // Указываем соответствие с JSON
            @JsonProperty("updatedAt") String updatedAt)   // Указываем соответствие с JSON
    {
        this.updatedAt = updatedAt;

    }
}





