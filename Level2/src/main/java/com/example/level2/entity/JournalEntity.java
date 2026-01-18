package com.example.level2.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "journals")
public class JournalEntity {

    @Id
    private ObjectId id;

    @NonNull
    private String title;
    private String content;
    private LocalDate date;
}
