package mn.guren.gis.model;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

/**
 * Base domain object with Id, version, whenCreated and whenModified.
 */
@Data
@MappedSuperclass
public abstract class BaseModel extends Model {

    @Id
    protected int id;

    @Version
    protected Long version;

    @WhenCreated
    protected Instant whenCreated;

    @WhenModified
    protected Instant whenModified;
}
