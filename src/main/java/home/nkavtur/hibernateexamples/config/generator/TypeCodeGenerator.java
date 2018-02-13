package home.nkavtur.hibernateexamples.config.generator;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

/**
 * NikolaiKavtur.
 */
public class TypeCodeGenerator implements ValueGenerator<String> {

    @Override
    public String generateValue(Session session, Object owner) {
        return owner.getClass().getSimpleName();
    }
}
