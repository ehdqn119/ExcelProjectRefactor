package org.example.dto;

import lombok.*;

/**
 * 데이터베이스에  저장될 Member 테이블 입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Member {

    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private String gender;
    private Integer height;

    private Boolean isSave;

}
