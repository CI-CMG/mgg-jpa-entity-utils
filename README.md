# mgg-jpa-entity-utils

The mgg-jpa-entity-utils project provides common utilities for comparing and using collections of JPA entities

Additional project information, javadocs, and test coverage is located at https://ci-cmg.github.io/project-documentation/mgg-jpa-entity-utils/

## Adding To Your Project

Add the following dependency to your Maven pom.xml

```xml
    <dependency>
      <groupId>io.github.ci-cmg</groupId>
      <artifactId>mgg-jpa-entity-utils</artifactId>
      <version>1.4.0</version>
    </dependency>
```

### EntityWithId
The EntityWithId interface requires is used throughout this library.  JPA entities should implement this interface, which defines the primary key
for the entity.

### EntityUtil
This class contains several static methods to help with equals() and hashCode() implementations of JPA entities.  equals() is based solely on the ID 
provided by the EntityWithId interface.  hashCode uses a constant value to account for the ID being set on saving.  While admittedly not the most 
performant approach, it does maintain the contract between equals() and hashCode() when dealing with collections.

There are additional methods to help set relationships when using @OneToMany mappings.

### Example
```java
@Entity
@Table(name = "CATALOG_METADATA")
public class CatalogMetadataEntity implements EntityWithId<String> {

  @Id
  @Column(name = "TRACKING_ID", nullable = false, unique = true)
  private String trackingId;

  @Column(name = "FILENAME", nullable = false)
  private String fileName;

  @Column(name = "DATASET", nullable = false)
  private String dataset;

  @Column(name = "TYPE", nullable = false)
  private String type;

  @Column(name = "FILE_SIZE", nullable = false)
  private Long fileSize;

  @Lob
  @Column(name = "FILE_METADATA")
  private String fileMetadata;

  @Column(name = "GEOMETRY", columnDefinition = "SDO_GEOMETRY")
  private Geometry geometry;

  @OneToMany(mappedBy = "catalogMetadata", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CatalogMetadataPathEntity> paths;

  public List<CatalogMetadataPathEntity> getPaths() {
    return Collections.unmodifiableList(paths);
  }

  public void addPath(CatalogMetadataPathEntity reference) {
    EntityUtil.addAndParent(this, paths, reference, this::removePath, reference::setCatalogMetadata);
  }

  public void removePath(CatalogMetadataPathEntity reference) {
    EntityUtil.removeAndOrphan(paths, reference, reference::setCatalogMetadata);
  }

  public void clearPaths() {
    EntityUtil.clearAndOrphan(paths, CatalogMetadataPathEntity::setCatalogMetadata);
  }

  @Override
  public String getId() {
    return getTrackingId();
  }

  public String getTrackingId() {
    return trackingId;
  }

  public void setTrackingId(String trackingId) {
    this.trackingId = trackingId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getDataset() {
    return dataset;
  }

  public void setDataset(String dataset) {
    this.dataset = dataset;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getFileSize() {
    return fileSize;
  }

  public void setFileSize(Long fileSize) {
    this.fileSize = fileSize;
  }

  public String getFileMetadata() {
    return fileMetadata;
  }

  public void setFileMetadata(String fileMetadata) {
    this.fileMetadata = fileMetadata;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }


  @Override
  public boolean equals(Object o) {
    return EntityUtil.equals(this, o);
  }

  @Override
  public int hashCode() {
    return EntityUtil.hashCodeFinalId(this);
  }


}
```







