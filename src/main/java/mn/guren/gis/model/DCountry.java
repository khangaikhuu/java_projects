package mn.guren.gis.model;

import lombok.Data;
import org.postgis.MultiPolygon;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Data
@Entity(name = "country")
public class DCountry extends BaseModel {
    private String isoA2;
    private String isoA3;
    private String name;
    private MultiPolygon geom;
    private Timestamp date;
}
