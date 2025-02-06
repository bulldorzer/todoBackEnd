package org.zerock.mallapi.service;

import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;

import java.util.List;

public interface CartService {

    // 장바구니 아이템 추가 혹은 변경
    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO);

    // 모든 장바구니 상품 목록 조회
    public List<CartItemListDTO> getCartItems(String email);

    // 장바구니 아이템 삭제
    public List<CartItemListDTO> remove(Long cino);
}
