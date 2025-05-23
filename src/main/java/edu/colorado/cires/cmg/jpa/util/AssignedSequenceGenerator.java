package edu.colorado.cires.cmg.jpa.util;

import edu.colorado.cires.cmg.jpa.model.EntityWithId;
import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * Extends the default {@link SequenceStyleGenerator} and only sets an identity if the supplied id value is not null.
 */
public class AssignedSequenceGenerator extends SequenceStyleGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor s, Object obj) {
    if(obj instanceof EntityWithId) {
      EntityWithId entityWithId = (EntityWithId) obj;
      Serializable id = entityWithId.getId();
      if(id != null) {
        return id;
      }
    }
    return super.generate(s, obj);
  }
}
