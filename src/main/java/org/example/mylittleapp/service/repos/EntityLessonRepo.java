package org.example.mylittleapp.service.repos;

import org.example.mylittleapp.model.entity.EntityLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EntityLessonRepo extends JpaRepository<EntityLesson, String> {

  @Query("SELECT l FROM EntityLesson l " +
      "WHERE l.topic.id = :topicId")
  List<EntityLesson> findByTopic(String topicId);

  Optional<EntityLesson> findByLesson(String lesson);

}
