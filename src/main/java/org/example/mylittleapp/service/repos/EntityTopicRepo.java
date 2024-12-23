package org.example.mylittleapp.service.repos;

import org.example.mylittleapp.model.entity.EntityTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EntityTopicRepo extends JpaRepository<EntityTopic, String> {

  @Query("SELECT t FROM EntityTopic t " +
      "WHERE t.course.id = :courseId")
  List<EntityTopic> findByCourseId(String courseId);

  Optional<EntityTopic> findByTopic(String topic);

}
