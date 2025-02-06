package org.zerock.mallapi.repository;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.mallapi.domain.Cart;
import org.zerock.mallapi.domain.CartItem;
import org.zerock.mallapi.domain.Member;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.dto.CartItemListDTO;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // 장바구니 아이템 추가 테스트
    @Transactional
    @Commit
    @Test
    public void testInsertByProduct(){
        log.info("-------------test1-------------");
        // 사용자 정보
        String email = "user0@aaa.com";
        Long pno = 2L;
        int qty = 1;

        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        if(cartItem != null){ // 카트에 상품이 존재하면?
            cartItem.changeQty(qty); // 수량 변경
            cartItemRepository.save(cartItem); // 저장

            return; // 종료
        }

        // 장바구니 없다면 생성
        // 카트에 이메일이 있는지 검색
        Optional<Cart> result = cartRepository.getCartOfMember(email);

        Cart cart = null;
        if(result.isEmpty()){ // 이메일 없음 = 장바구니 생성x
            log.info("MemberCart is not exist!!");
            // 장바구니 생성 로직
            Member member = Member.builder().email(email).build(); // 멤버 생성
            Cart tempCart = Cart.builder().owner(member).build(); // 임시 장바구니 생성
            cart = cartRepository.save(tempCart); // 신규 장바구니 저장하고 - 저장된 장바구니 cart에 반환

        } else {
            cart = result.get(); // 이메일 조회가 되면 장바구니 반환
        }

        log.info(cart);

        // 카트에 해당 상품이 없다면?
        if(cartItem == null){
            Product product = Product.builder().pno(pno).build(); // 상품 객체 생성
            cartItem = CartItem.builder().product(product).cart(cart).qty(qty).build(); // 카트 아이템 객체 생성
        }

        // 상품 아이템 저장
        cartItemRepository.save(cartItem); //저장
    }

    // 수정 테스트
    @Test
    @Commit
    public void testUpdateByCino(){

        Long cino = 1L;
        int qty = 4;
        Optional<CartItem> result = cartItemRepository.findById(cino); // 카트의 상품 찾아오기
        CartItem cartItem = result.orElseThrow();
        cartItem.changeQty(qty);
        cartItemRepository.save(cartItem);
    }

    // 현재 사용자의 장바구니 아이템 목록 테스트
    @Test
    public void testListOfMember(){

        String email = "user1@aaa.com";
        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByEmail(email);

        for (CartItemListDTO dto : cartItemList){
            log.info(dto);
        }
    }

    // 장바구니 아이템 삭제와 목록조회
    @Test
    public void testDeleteThenList(){
        Long cino = 1L;

        // 장바구니 번호
        Long cno = cartItemRepository.getCartFromItem(cino);

        //삭제닌 임시로 주석처리
        cartItemRepository.deleteById(cino);

        // 목록
        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        for (CartItemListDTO dto : cartItemList){
            log.info(dto);
        }
    }

}
