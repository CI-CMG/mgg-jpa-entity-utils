//package edu.colorado.cires.cmg.jpa.util;
//
//import edu.colorado.cires.cmg.jpa.model.EntityWithId;
//import java.io.Serializable;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.id.IdentityGenerator;
//
///**
// * Extends the default {@link IdentityGenerator} and only sets an identity if the supplied id value is not null.
// */
//public class AssignedIdentityGenerator extends IdentityGenerator {
//
////  @Override
////  public Serializable generate(SharedSessionContractImplementor s, Object obj) {
////    if(obj instanceof EntityWithId) {
////      EntityWithId entityWithId = (EntityWithId) obj;
////      Serializable id = entityWithId.getId();
////      if(id != null) {
////        return id;
////      }
////    }
////    return super.generate(s, obj);
//
//
//
//    // https://hibernate.atlassian.net/browse/HHH-16692?focusedCommentId=113950
//    return super.generatedOnExecution(obj, s);
//  }
//}
