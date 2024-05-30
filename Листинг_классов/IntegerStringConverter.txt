package application;
import javafx.util.StringConverter;

public class IntegerStringConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer object) {
        return object != null ? object.toString() : "";
    }

    @Override
    public Integer fromString(String string) {
        try {
            return string != null && !string.isEmpty() ? Integer.parseInt(string) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}