package edu.colorado.cires.cmg.jpa.util;

import edu.colorado.cires.cmg.jpa.model.EntityWithId;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class EntityUtil {

  /**
   * Deprecated. Use @{link {@link #addAndParent(Object, Collection, EntityWithId, Consumer, Consumer)}}
   *
   * @param parent the parent
   * @param parents the parents
   * @param <T> the type
   */
  @Deprecated
  public static <T extends EntityWithId<?>> void addParent(
      T parent,
      Collection<T> parents) {
    addAndParent(parent, parents, parent, parents::remove, parents::add);
  }

  /**
   * Deprecated. Use @{link {@link #addAndParent(Object, Collection, EntityWithId, Consumer, Consumer)}}
   *
   * @param thisEntity the entity
   * @param childEntity the child
   * @param setChild set child method
   * @param setParent set parent method
   * @param <T> the parent type
   * @param <C> the child type
   */
  @Deprecated
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

  /**
   * Adds a the provided entity to the collection and sets the parent of that entity. If the collection contains
   * the given child, where equals() evaluates to true, that child will be removed from the collection before adding
   * the given child.  It is recommended that the handleRemoveChild implementation
   * use @{link {@link #removeAndOrphan(Collection, EntityWithId, Consumer)}}
   *
   * @param thisEntity the parent entity (this)
   * @param childEntities the collection of child entities
   * @param childEntity the child entity to add
   * @param handleRemoveChild the method to remove a child from the collection
   * @param handleSetParent the method to set the parent of the child
   * @param <T> the parent type
   * @param <C> the child type
   */
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

  /**
   * Removes the given child entity from the given collection.  If more than one child exists in the collection
   * where equals() evaluates to true, all of these entities will be removed.  The parent of the provided child entity
   * will be set to null.
   *
   * @param childEntities the collection of entities
   * @param childEntity the child entity to remove
   * @param handleSetParent the method to set the child entity parent
   * @param <T> the parent type
   * @param <C> the child type
   */
  public static <T, C extends EntityWithId<?>> void removeAndOrphan(
      Collection<C> childEntities,
      C childEntity,
      Consumer<T> handleSetParent) {
    Iterator<C> it = childEntities.iterator();
    while (it.hasNext()) {
      C c = it.next();
      if(childEntity.equals(it)) {
        it.remove();
      }
    }
    handleSetParent.accept(null);
  }

  /**
   * Removes all entities from the collection and sets the parents of those entities to null.
   *
   * @param childEntities the entity collection
   * @param handleSetParent the method to set the entity parent
   * @param <T> the parent type
   * @param <C> the child type
   */
  public static <T, C extends EntityWithId<?>> void clearAndOrphan(
      Collection<C> childEntities,
      BiConsumer<C, T> handleSetParent
  ) {
    childEntities.forEach(childEntity -> handleSetParent.accept(childEntity, null));
    childEntities.clear();
  }

  /**
   * Uses the entity ID to determine equality.  If two entities are the same object, true will be returned.
   * If two entities have the same non-null ID, true will be returned. If either or both IDs are null, false
   * will be returned.
   *
   * @param thisEntity the entity represented by "this"
   * @param obj the other object to compare with
   * @param <T> the entity type
   * @return true if both objects are the same or have the same non-null ID
   */
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

  /**
   * Returns the hash code for the entity ID.  This method can only be used when the ID is guaranteed
   * to be effectively immutable and non-null.
   * @param thisEntity the entity with an immutable ID
   * @param <T> the entity type
   * @return a hash code for the entity based on the ID
   */
  public static <T extends EntityWithId<?>> int hashCodeFinalId(T thisEntity) {
    return Objects.hashCode(thisEntity.getId());
  }

  /**
   * Returns a constant value. This is used to maintain the equals + hashcode contract when an ID is mutable.
   * This will effect performance, but is necessary in this situation.
   * @return a constant hash code
   */
  public static int hashCodeGeneratedId() {
    return 1;
  }

  private EntityUtil() {

  }

}
