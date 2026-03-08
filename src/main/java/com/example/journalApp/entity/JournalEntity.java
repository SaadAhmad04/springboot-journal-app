package com.example.journalApp.entity;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journals")
@Getter
@Setter
public class JournalEntity {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
}
