package home.nkavtur.hibernateexamples.config.generator.value.annotation;

import home.nkavtur.hibernateexamples.config.generator.value.UpdateTsValueGenerator;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NikolaiKavtur.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@ValueGenerationType(generatedBy = UpdateTsValueGenerator.class)
public @interface UpdateTs {
}
