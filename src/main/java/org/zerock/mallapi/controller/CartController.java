package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;
import org.zerock.mallapi.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {
    private CartService cartService;

    @PreAuthorize("#itemDTO.email == authentication.name")
    @PostMapping("/change")
    public List<CartItemListDTO> changeCart(@RequestBody CartItemDTO itemDTO){

        log.info(itemDTO);

        if (itemDTO.getQty()<= 0 ){
            return cartService.remove(itemDTO.getCion());
        }

        return cartService.addOrModify(itemDTO);
    }
}
