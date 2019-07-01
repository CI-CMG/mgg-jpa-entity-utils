package edu.colorado.cires.cmg.jpa.util;

import edu.colorado.cires.cmg.jpa.model.EntityWithId;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class EntityUtil {

  public static <T extends EntityWithId<?>> void addParent(
      T parent,
      Collection<T> parents) {
    addAndParent(parent, parents, parent, parents::remove, parents::add);
  }

  public static <T, C extends EntityWithId<?>> void addAndParent(
      T thisEntity,
      C childEntity,
      Consumer<C> setChild,
      Consumer<T> setParent) {
    setChild.accept(childEntity);
    setParent.accept(thisEntity);
  }

  public static <T, C extends EntityWithId<?>> void removeAndOrphan(
      Consumer<C> setChild,
      Consumer<T> setParent) {
    setChild.accept(null);
    setParent.accept(null);
  }

  public static <T, C extends EntityWithId<?>> void addAndParent(
      T thisEntity,
      Collection<C> childEntities,
      C childEntity,
      Consumer<C> handleRemoveChild,
      Consumer<T> handleSetParent) {
    if (childEntity.getId() == null) {
      childEntities.add(childEntity);
    } else {
      if (childEntities.contains(childEntity)) {
        handleRemoveChild.accept(childEntity);
      }
      childEntities.add(childEntity);
    }
    handleSetParent.accept(thisEntity);
  }

  public static <T, C extends EntityWithId<?>> void removeAndOrphan(
      Collection<C> childEntities,
      C childEntity,
      Consumer<T> handleSetParent) {
    childEntities.remove(childEntity);
    handleSetParent.accept(null);
  }

  public static <T, C extends EntityWithId<?>> void clearAndOrphan(
      Collection<C> childEntities,
      BiConsumer<C, T> handleSetParent
  ) {
    childEntities.forEach(childEntity -> handleSetParent.accept(childEntity, null));
    childEntities.clear();
  }

  public static <T extends EntityWithId<?>> boolean equals(T thisEntity, Object obj) {
    if (thisEntity == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    if (!thisEntity.getClass().isInstance(obj)) {
      return false;
    }
    EntityWithId<?> other = (EntityWithId) obj;
    Object thisId = thisEntity.getId();
    Object otherId = other.getId();
    if (thisId == null || otherId == null) {
      return false;
    }
    return thisId.equals(otherId);
  }

  public static <T extends EntityWithId<?>> int hashCodeFinalId(T thisEntity) {
    return Objects.hashCode(thisEntity.getId());
  }

  public static int hashCodeGeneratedId() {
    // A constant must be returned if generated ids are used to maintain the equals + hashcode contract
    // this will effect performance, but is necessary
    return 1;
  }

  private EntityUtil() {

  }

}
