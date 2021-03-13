package edu.sampleproject.movielens.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;

}
