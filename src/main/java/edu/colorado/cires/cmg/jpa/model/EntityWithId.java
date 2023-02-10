package edu.colorado.cires.cmg.jpa.model;

import java.io.Serializable;

public interface EntityWithId<I extends Serializable> {

  I getId();

}
