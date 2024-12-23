package org.example.mylittleapp.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.mylittleapp.model.entity.EntityProperties.EntityTopicProperties.*;

@Entity
@Table(name = TABLE)
public class EntityTopic {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = PK_TOPIC, updatable = false)
  private String id;
  @Column(name = ORDER)
  private Integer order;
  @Column(name = TOPIC, unique = true)
  private String topic;
  @Column(name = DESCRIPTION)
  private String description;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.REFRESH
      }
  )
  @JoinColumn(name = FK_COURSE)
  private EntityCourse course;
  @OneToMany(
      cascade = {CascadeType.DETACH, CascadeType.REFRESH},
      fetch = FetchType.LAZY,
      mappedBy = "topic"
  )
  private List<EntityLesson> lessons;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EntityCourse getCourse() {
    return course;
  }

  public void setCourse(EntityCourse course) {
    this.course = course;
  }

  public List<EntityLesson> getLessons() {
    if(lessons == null) lessons = new ArrayList<>();
    return lessons;
  }

  public void setLessons(List<EntityLesson> lessons) {
    if(lessons == null) lessons = new ArrayList<>();
    if(this.lessons == null) this.lessons = new ArrayList<>();
    if(lessons.isEmpty()){
      this.lessons.forEach(lesson -> lesson.setTopic(null));
    }else {
      lessons.forEach(lesson -> lesson.setTopic(this));
    }
    this.lessons = lessons;
  }

  public void addLesson(EntityLesson lesson){
    if(lesson == null) return;
    if(this.lessons == null) this.lessons = new ArrayList<>();
    if(!this.lessons.contains(lesson)) {
      lesson.setTopic(this);
      this.lessons.add(lesson);
    }
  }

  public void removeLesson(EntityLesson lesson) {
    if(lesson == null) return;
    if(lessons == null) lessons = new ArrayList<>();
    if(lessons.contains(lesson) && lessons.remove(lesson)){
      lesson.setTopic(null);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EntityTopic that)) return false;
    return Objects.equals(id, that.id) && Objects.equals(topic, that.topic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, topic);
  }
}
