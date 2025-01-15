package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;
  private String pname;
  private int price;
  private String pdesc;
  private boolean delFlag;
  private String keyword;

  public void changeDel(boolean delFlag) {
    this.delFlag = delFlag;
  }

  /* 
    imageList가 엔티티의 값이 아닌(컬럼이 아닌) 컬렉션요소
    컬렉션은 별도의 테이블로 저장이 된다
   */
  @ElementCollection  
  @Builder.Default // 빌더패턴 적용하면 빈객체로 생성
  private List<ProductImage> imageList = new ArrayList<>();
  public void changePrice(int price) {
    this.price = price;
  }
  public void changeDesc(String desc){
      this.pdesc = desc;
  }
  public void changeName(String name){
      this.pname = name;
  }
  public void addImage(ProductImage image) {

      image.setOrd(this.imageList.size());
      imageList.add(image);
  }

  // 2) 생성된 객체 받아서 imageList객체 추가
  public void addImageString(String fileName){
  // 받아온 파일 이름으로 객체 생성
    ProductImage productImage = ProductImage.builder()
    .fileName(fileName)
    .build();
    addImage(productImage);

  }

  public void clearList() {
      this.imageList.clear();
  }
}
