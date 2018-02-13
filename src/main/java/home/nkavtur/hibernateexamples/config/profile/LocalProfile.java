package home.nkavtur.hibernateexamples.config.profile;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

/**
 * NikolaiKavtur.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile("local")
public @interface LocalProfile {
}
