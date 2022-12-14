package Input.Validation.CustomValidators;

import Entities.Location;
import Exceptions.ValidationException;
import Input.Validation.ValidatedData;
import Input.Validation.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Валидатор для поля Person - Location
 * @author uvuv-643
 * @version 1.0
 */
public class LocationValidator implements Validator {

    private final String LOCATION_DELIMITER = " ";
    private final int MAXIMUM_FLOAT_LENGTH = 18;

    // TODO: command to enum

    private String[] parseLocationStringToArray(String data) {
        String[] location = data.replaceAll("[\s]{2,}", " ").trim().split(this.LOCATION_DELIMITER);
        if (location.length > 3) {
            String[] finalLocation = location;
            List<String> locationNameParts = IntStream.range(0, location.length)
                    .filter(i -> i >= 2)
                    .mapToObj(i -> finalLocation[i]).toList();
            String locationName = String.join(" ", locationNameParts);
            location[2] = locationName;
            location = Arrays.copyOfRange(location, 0, 3);
        }
        return location;
    }

    @Override
    public ValidatedData<Optional<Location>> validate(String data) throws ValidationException {
        if (data == null || data.isEmpty()) {
            return new ValidatedData<>(Optional.empty());
        }
        String[] location = this.parseLocationStringToArray(data);
        if (location.length != 3) {
            throw new ValidationException("Wrong location format");
        }
        String xCoordinate = location[0];
        String yCoordinate = location[1];
        if (xCoordinate.length() > MAXIMUM_FLOAT_LENGTH) {
            throw new ValidationException("Maximum size of yCoordinate exceeded");
        }
        String locationName = location[2];
        double x; int y;
        try {
            x = Double.parseDouble(xCoordinate);
        } catch (NumberFormatException e) {
            throw new ValidationException("Given X is not an integer");
        }
        try {
            y = Integer.parseInt(yCoordinate);
            if (!Integer.toString(y).equals(yCoordinate)) {
                throw new ValidationException("Given double not integer in Y coordinate");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Given Y is not an integer");
        }
        return new ValidatedData<>(Optional.of(new Location(x, y, locationName)));
    }

    public ValidatedData<Location> validate(Location data) throws ValidationException {
        if (data == null) return new ValidatedData<>(null);
        String name = data.getName();
        if (name == null) {
            throw new ValidationException("Location name cannot be null");
        }
        if (name.isEmpty()) {
            throw new ValidationException("Location name cannot be empty");
        }
        return new ValidatedData<>(data);
    }

}
