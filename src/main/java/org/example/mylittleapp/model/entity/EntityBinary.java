package org.example.mylittleapp.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

import static org.example.mylittleapp.model.entity.EntityProperties.EntityBinaryProperties.*;

@Entity
@Table(name = TABLE)
public class EntityBinary {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = PK_BINARY_ASSET, updatable = false)
  private String id;
  @Column(name = FILE_NAME, unique = true)
  private String fileName;
  @Column(name = MIME_TYPE)
  private String mimeType;
  @Column(name = URL)
  private String url;
  @Lob @Basic(fetch = FetchType.LAZY)
  @Column(name = BINARY_DATA)
  private byte[] binaryData;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = FK_MARKDOWN)
  private EntityMarkdown parent;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public byte[] getBinaryData() {
    return binaryData;
  }

  public void setBinaryData(byte[] binaryData) {
    this.binaryData = binaryData;
  }

  public EntityMarkdown getParent() {
    return parent;
  }

  public void setParent(EntityMarkdown parent) {
    this.parent = parent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EntityBinary that)) return false;
    return Objects.equals(id, that.id)
        && Objects.equals(fileName, that.fileName)
        && Objects.equals(mimeType, that.mimeType)
        && Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fileName, mimeType, url);
  }
}
