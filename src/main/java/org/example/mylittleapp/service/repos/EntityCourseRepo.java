package org.example.mylittleapp.service.repos;

import org.example.mylittleapp.model.entity.EntityCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntityCourseRepo extends JpaRepository<EntityCourse, String> {

  Optional<EntityCourse> findByLanguage(@Param("language") String language);

}
