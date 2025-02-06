package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = {"cart", "product"})
@Table(
        name = "tbl_cart_item",
        indexes = {
                @Index(name = "idx_cartitem_cart", columnList = "cart_cno"),
                @Index(name = "idx_cartitem_pno_cart", columnList = "product_pno, cart_cno")
/*
    @Index(name = "인덱스이름", columnList = "fk이름")

    @Index(name = "idx_cartitem_cart", columnList = "cart_cno")
    인덱스이름 : idx_cartitem_cart
    컬럼이름 : cart_cno -- 에 대해서 단일 컬럼 인덱스 생성

    @Index(name = "idx_cartitem_pno_cart", columnList = "product_pno, cart_cno")
    인덱스 이름 : idx_cartitem_pno_cart
    복합인덱스 : product_pno, cart_cno
    - 2개이상의 컬럼을 결합하여 하나의 인덱스로 지정하는 방식 -> 조회 속도 향상시킴, 불필요한 인덱스 줄이기
    ex) where product_pno = ? and cart_cno = ?
*/
        }
)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_pno") // 필드이름 이름지정
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_cno") // 필드이름 이름지정
    private Cart cart;


    private int qty;

    public void changeQty(int qty){
        this.qty = qty;
    }
}
