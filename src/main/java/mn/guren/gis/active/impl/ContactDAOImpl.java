package mn.guren.gis.active.impl;

import mn.guren.gis.active.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private final List<Contact> contacts = new ArrayList<>();

    @Override
    public List<Contact> list() {
        return contacts;
    }

    @Override
    public void add(Contact user) {
        contacts.add(user);
    }
}
