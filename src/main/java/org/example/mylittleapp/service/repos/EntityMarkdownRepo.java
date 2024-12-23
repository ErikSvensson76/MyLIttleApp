package org.example.mylittleapp.service.repos;

import org.example.mylittleapp.model.entity.EntityMarkdown;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityMarkdownRepo extends JpaRepository<EntityMarkdown, String> {

}
