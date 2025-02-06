package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Cart;
import org.zerock.mallapi.domain.CartItem;
import org.zerock.mallapi.domain.Member;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;
import org.zerock.mallapi.repository.CartItemRepository;
import org.zerock.mallapi.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO) {

        // DTO값 변수에 담아 놓기
        String email = cartItemDTO.getEmail();
        Long pno = cartItemDTO.getPno();
        int qty = cartItemDTO.getQty();
        Long cino = cartItemDTO.getCion();

        // 장바구니 수량만 변경하는 경우
        if (cino != null){
            Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);
            CartItem cartItem = cartItemResult.orElseThrow();

            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return getCartItems(email); // 현재 추가된 장바구니 전체 상품 목록 조회하여 리턴
        }

        // 장바구니에 해당 상품 번호가 없는 경우 장바구니 가져오기 장바구니가 없으면 새롭게 생성
        // 사용자의 카트
        Cart cart = getCart(email);

        CartItem cartItem =null;

        // 이미 동일한 상품이 담긴적이 있으므로
        cartItem = cartItemRepository.getItemOfPno(email,pno);

        if (cartItem == null){
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).qty(qty).build();
        }else {
            cartItem.changeQty(qty);
        }
        // 상품 아이템 저장
        cartItemRepository.save(cartItem);

        return getCartItems(email);
    }

    // 사용자의 장바구니가 없었다면 새로운 장바구니를 생성하고 반환
    private Cart getCart(String email) {
        
        Cart cart = null;
        
        Optional<Cart> result = cartRepository.getCartOfMember(email); // 이메일 기준으로 장바구니 찾아오기
        
        // 장바구니가 없으면 새롭게 생성
        if (result.isEmpty()){
            
            log.info("Cart of the member is not exist!!");
            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().owner(member).build();
            cart = cartRepository.save(tempCart);
            
        }else {
            cart = result.get();
        }

        return cart;
    }

    @Override
    public List<CartItemListDTO> getCartItems(String email) {
        // 이메일 기준으로 cartItem 전체 목록 조회
        return cartItemRepository.getItemsOfCartDTOByEmail(email);
    }

    // 장바구니 삭제
    @Override
    public List<CartItemListDTO> remove(Long cino) {

        // 장바구니의 상품 번호를 통해 장바구니의 번호를 찾기
        Long cno = cartItemRepository.getCartFromItem(cino);
        log.info("Cart no: "+ cno);
        cartItemRepository.deleteById(cino); // 장바구니의 상품 삭제 
        return cartItemRepository.getItemsOfCartDTOByCart(cno); // 장바구니 번호로 다시 상품내역 조회
    }
}
