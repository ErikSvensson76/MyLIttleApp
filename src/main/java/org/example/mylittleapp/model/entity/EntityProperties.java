package org.example.mylittleapp.model.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityProperties {

  @NoArgsConstructor
  public static class EntityCourseProperties{
    public static final String TABLE = "course";
    public static final String PK_COURSE = "pk_course";
    public static final String LANGUAGE = "language";
    public static final String DIFFICULTY = "difficulty";
  }

  @NoArgsConstructor
  public static class EntityTopicProperties{
    public static final String TABLE = "topic";
    public static final String PK_TOPIC = "pk_topic";
    public static final String ORDER = "topic_order";
    public static final String TOPIC = "topic";
    public static final String DESCRIPTION = "description";
    public static final String FK_COURSE = "fk_course";
  }

  @NoArgsConstructor
  public static class EntityLessonProperties{
    public static final String TABLE = "lesson";
    public static final String PK_LESSON = "pk_lesson";
    public static final String LESSON = "lesson";
    public static final String ORDER = "lesson_order";
    public static final String FK_TOPIC = "fk_topic";
  }

  @NoArgsConstructor
  public static class EntityMarkdownProperties{
    public static final String TABLE = "markdown";
    public static final String PK_MARKDOWN = "pk_markdown";
    public static final String ORDER = "markdown_order";
    public static final String MARKDOWN_CONTENT = "markdown_content";
    public static final String FK_LESSON = "fk_lesson";
  }

}
