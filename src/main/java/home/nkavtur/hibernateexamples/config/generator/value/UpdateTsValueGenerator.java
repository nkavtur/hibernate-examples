package home.nkavtur.hibernateexamples.config.generator.value;

import home.nkavtur.hibernateexamples.config.generator.value.annotation.UpdateTs;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**
 * NikolaiKavtur.
 */
public class UpdateTsValueGenerator implements AnnotationValueGeneration<UpdateTs> {

    @Override
    public void initialize(UpdateTs annotation, Class<?> propertyType) {

    }

    @Override
    public GenerationTiming getGenerationTiming() {
        return GenerationTiming.ALWAYS;
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
        return "current_timestamp AT TIME ZONE 'UTC'";
    }
}
