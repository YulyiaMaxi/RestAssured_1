import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnsuccessReg {
    private static String error;

    public UnsuccessReg() {
    }

    public static String getError() {
        return error;
    }

    public static void setError(String error) {
        UnsuccessReg.error = error;
    }

    @JsonCreator
    public UnsuccessReg(

            @JsonProperty("error") String error) {


        UnsuccessReg.error = error;
    }

}
