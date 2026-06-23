package edu.colorado.cires.cmg.jpa.util;

import edu.colorado.cires.cmg.jpa.model.GeneratedIdIfNotProvided;
import edu.colorado.cires.cmg.jpa.model.EntityWithId;
import java.io.Serializable;
import java.util.Properties;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class AssignedSequenceGenerator extends SequenceStyleGenerator {

  private final String sequenceName;
  private final int incrementSize;

  public AssignedSequenceGenerator(GeneratedIdIfNotProvided config) {
    sequenceName = config.sequenceName();
    incrementSize = config.incrementSize();
  }

  @Override
  public void configure(GeneratorCreationContext creationContext, Properties parameters) throws MappingException {
    parameters.put(SEQUENCE_PARAM, sequenceName);
    parameters.put(INCREMENT_PARAM, incrementSize);
    super.configure(creationContext, parameters);
  }

  @Override
  public Object generate(SharedSessionContractImplementor s, Object obj) {
    if (obj instanceof EntityWithId) {
      EntityWithId<?> entityWithId = (EntityWithId) obj;
      Serializable id = entityWithId.getId();
      if (id != null) {
        return id;
      }
    }
    return super.generate(s, obj);
  }

  @Override
  public boolean allowAssignedIdentifiers() {
    return true;
  }
}
