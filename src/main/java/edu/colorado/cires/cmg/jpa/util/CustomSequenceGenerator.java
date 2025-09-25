package edu.colorado.cires.cmg.jpa.util;


import edu.colorado.cires.cmg.jpa.model.EntityWithId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class CustomSequenceGenerator extends SequenceStyleGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object owner) throws HibernateException {
        if (this.allowAssignedIdentifiers() && owner instanceof EntityWithId) {
            EntityWithId entityWithId = (EntityWithId) owner;
            Serializable id = entityWithId.getId();
            if(id != null) {
                return id;
            }
        }

        return (Serializable) super.generate(session, owner);
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
