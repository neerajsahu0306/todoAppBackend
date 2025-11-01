package com.firstProject.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class UserModel {
    @Id
    private ObjectId id;

    private String clerkId;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String userName;

    private List<ObjectId> todoId = new ArrayList<>();

}
