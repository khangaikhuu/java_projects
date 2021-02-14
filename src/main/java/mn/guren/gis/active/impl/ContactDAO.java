package mn.guren.gis.active.impl;

import mn.guren.gis.active.model.Contact;

import java.util.List;

public interface ContactDAO {
    List<Contact> list();

    void add(Contact user);
}
