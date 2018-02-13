package home.nkavtur.hibernateexamples.config.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * NikolaiKavtur.
 */
@Converter
public class BooleanToBitStringConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute == Boolean.TRUE ? "1" : "0";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "1".equals(dbData) ? Boolean.TRUE : Boolean.FALSE;
    }
}
