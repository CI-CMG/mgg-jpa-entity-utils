package edu.colorado.cires.cmg.jpa.util;

import edu.colorado.cires.cmg.jpa.model.GeneratedIdIfNotProvided;
import edu.colorado.cires.cmg.jpa.model.EntityWithId;
import java.io.Serializable;
import java.util.Properties;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class AssignedSequenceGenerator extends SequenceStyleGenerator {

  private final String sequenceName;
  private final int incrementSize;

  public AssignedSequenceGenerator(GeneratedIdIfNotProvided config) {
    sequenceName = config.sequenceName();
    incrementSize = config.incrementSize();
  }


  @Override
  public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {
    parameters.put(SEQUENCE_PARAM, sequenceName);
    parameters.put(INCREMENT_PARAM, incrementSize);
    super.configure(type, parameters, serviceRegistry);
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
