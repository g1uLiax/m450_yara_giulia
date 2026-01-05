package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class AddressComparator implements Comparator<Address> {

    public enum Field {
        FIRSTNAME,
        LASTNAME,
        PHONENUMBER,
        REGISTRATIONDATE
    }

    private final Field[] fields;

    public AddressComparator() {
        this.fields = new Field[]{
                Field.LASTNAME,
                Field.FIRSTNAME,
                Field.REGISTRATIONDATE
        };
    }

    public AddressComparator(Field... fields) {
        if (fields == null || fields.length == 0) {
            this.fields = new Field[]{
                    Field.LASTNAME,
                    Field.FIRSTNAME,
                    Field.REGISTRATIONDATE
            };
        } else {
            this.fields = fields;
        }
    }

    @Override
    public int compare(Address a1, Address a2) {
        if (a1 == a2) return 0;
        if (a1 == null) return -1;
        if (a2 == null) return 1;

        for (Field f : fields) {
            int result = switch (f) {
                case LASTNAME -> compareString(a1.getLastname(), a2.getLastname());
                case FIRSTNAME -> compareString(a1.getFirstname(), a2.getFirstname());
                case PHONENUMBER -> compareString(a1.getPhonenumber(), a2.getPhonenumber());
                case REGISTRATIONDATE -> compareDate(a1.getRegistrationDate(), a2.getRegistrationDate());
            };
            if (result != 0)
                return result;
        }

        // Fallback = ID
        return Integer.compare(a1.getId(), a2.getId());
    }

    private int compareString(String s1, String s2) {
        if (Objects.equals(s1, s2)) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;
        return s1.compareToIgnoreCase(s2);
    }

    private int compareDate(Date d1, Date d2) {
        if (Objects.equals(d1, d2)) return 0;
        if (d1 == null) return -1;
        if (d2 == null) return 1;
        return d1.compareTo(d2);
    }

}
