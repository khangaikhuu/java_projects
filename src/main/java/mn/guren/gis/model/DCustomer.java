package mn.guren.gis.model;

import io.ebean.annotation.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Customer entity bean.
 */
@Data
@Entity
@Table(name = "customer")
public class DCustomer extends BaseModel {

    @NotNull
    private String name;

    private LocalDate registered;
}

