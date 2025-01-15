package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;
  
  private String title;

  @Column(length = 5000)
  private String content;

  private String writer;

  private int viewCount;

  private LocalDate postDate;

  public void changeTitle(String title){
    this.title = title;
  }
  public void changeContent(String content){
    this.content = content;
  }

  public void incrementViewCount() {
    this.viewCount += 1;
  }

  public void changePostDate(LocalDate postDate){
    this.postDate = postDate;
  }


}
