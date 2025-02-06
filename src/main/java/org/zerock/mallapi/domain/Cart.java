package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "owner") // FK제외
@Table(
        name = "tbl_cart",
        indexes = {
                @Index(name="idx_cart_email", columnList = "member_owner")
        } // 인덱스 설정
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno; // PK

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_owner") // 필드이름 이름지정
    private Member owner; // FK

    /*
        DB에서 인덱스란 무엇인가
        특정 필드를 인덱스로 지정을 하면 검색속도를 향상 시킬수 있다.
    */
}
