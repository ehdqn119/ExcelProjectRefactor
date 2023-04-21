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

    private String sourceIp;
    private String desIp;
    private String portNumber;

    public Member getPolicy(Member member) {
        Member policy = new Member();
        policy.setId(member.getId());
        policy.setName(member.getName());
        policy.setSourceIp(member.getSourceIp());
        policy.setDesIp(member.getDesIp());
        policy.setPortNumber(member.getPortNumber());
        return member;
    }

}
