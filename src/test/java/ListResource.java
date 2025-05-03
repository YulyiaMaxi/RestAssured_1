import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListResource {


    public ListResource() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPantone_value() {
        return pantone_value;
    }

    public void setPantone_value(String pantone_value) {
        this.pantone_value = pantone_value;
    }

    private int id;
    private String name;
    private int year;
    private String color;
    private String pantone_value;

    @JsonCreator
    public void Datum(

            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("year") int year,
            @JsonProperty("color") String color,
            @JsonProperty("pantone_value") String pantone_value) {

        this.id = id;

        this.name = name;

        this.year = year;

        this.color = color;
        this.pantone_value = pantone_value;
    }

}
