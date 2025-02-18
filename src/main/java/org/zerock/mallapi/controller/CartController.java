package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;
import org.zerock.mallapi.service.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {
    private CartService cartService;

    // 수량 변경 요청 - 수량이 0보다 작게 들어오면 삭제 , 1이상 이면 변경
    @PreAuthorize("#itemDTO.email == authentication.name")
    @PostMapping("/change")
    public List<CartItemListDTO> changeCart(@RequestBody CartItemDTO itemDTO){

        log.info(itemDTO);

        if (itemDTO.getQty()<= 0 ){
            // 현재 장바구니 상품 번호에 해당하는것 삭제
            return cartService.remove(itemDTO.getCion());
        }

        return cartService.addOrModify(itemDTO);
    }

    // 현재 로그인한 사용자를 기준으로 장바구니 아이템 조회
    @PreAuthorize("hasAnyRole('RoLE_USER')")
    @GetMapping("/items")
    public List<CartItemListDTO> getCartItems(Principal principal){

        String email = principal.getName();
        log.info("------------------------------------------------");
        log.info("email: "+email);

        return cartService.getCartItems(email);
    }

    // 장바구니 아이템 삭제
    @PreAuthorize("hasAnyRole('RoLE_USER')")
    @DeleteMapping("/{cino}")
    public List<CartItemListDTO> removeFromCart(@PathVariable("cino") Long cino){


        log.info("cart item no: "+cino);

        return cartService.remove(cino);
    }
}
