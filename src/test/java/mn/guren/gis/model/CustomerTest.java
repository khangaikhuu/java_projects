package mn.guren.gis.model;

import io.ebean.DB;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CustomerTest {

    @Test
    public void insertFindDelete() {

        DCustomer customer = new DCustomer();
        customer.setName("Rob");
        customer.save();

        final DCustomer rob =
                DB.find(DCustomer.class)
                        .where().istartsWith("name", "ro")
                        .findOne();

        assertNotNull(rob);

        rob.delete();

        final DCustomer notThere = DB.find(DCustomer.class, customer.getId());
        assertNull(notThere);
    }

}
