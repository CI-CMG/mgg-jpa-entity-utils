package edu.colorado.cires.cmg.jpa.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import edu.colorado.cires.cmg.jpa.util.AssignedSequenceGenerator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;


@IdGeneratorType(AssignedSequenceGenerator.class)
@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratedIdIfNotProvided {
  String sequenceName();
  int incrementSize() default 1;
}
