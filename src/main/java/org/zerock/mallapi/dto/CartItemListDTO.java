package org.zerock.mallapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;
    private int qty;
    private Long pno;
    private String pname;
    private int price;
    private String imageFile;

    // JPQL 이용하여 직접 dto 생성하는 Projection 방식을 이용하기 위함
    public CartItemListDTO(Long cion, int qty, Long pno, String pname, int price, String imageFile){
        this.cino = cion;
        this.qty = qty;
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.imageFile = imageFile;

    }
}
