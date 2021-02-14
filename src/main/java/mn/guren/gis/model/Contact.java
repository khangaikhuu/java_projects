package mn.guren.gis.model;

import lombok.Data;

@Data
public class Contact {
    private final String name;
    private final Integer age;
    private final Address address;
}