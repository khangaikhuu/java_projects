package mn.guren.gis.model;

import io.ebean.DB;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CustomerTest {

    @Test
    public void insertFindDelete() {

        Customer customer = new Customer();
        customer.setName("Rob");
        customer.save();

        final Customer rob =
                DB.find(Customer.class)
                        .where().istartsWith("name", "ro")
                        .findOne();

        assertNotNull(rob);

        rob.delete();

        final Customer notThere = DB.find(Customer.class, customer.getId());
        assertNull(notThere);
    }

}
