package home.nkavtur.hibernateexamples.config.generator.value;

import home.nkavtur.hibernateexamples.config.generator.value.annotation.CreateTs;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**
 * NikolaiKavtur.
 */
public class CreateTsValueGenerator implements AnnotationValueGeneration<CreateTs> {

    @Override
    public void initialize(CreateTs annotation, Class<?> propertyType) {

    }

    @Override
    public GenerationTiming getGenerationTiming() {
        return GenerationTiming.INSERT;
    }

    @Override
    public ValueGenerator<?> getValueGenerator() {
        return null;
    }

    @Override
    public boolean referenceColumnInSql() {
        return true;
    }

    @Override
    public String getDatabaseGeneratedReferencedColumnValue() {
        return "current_timestamp";
    }
}
