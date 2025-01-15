package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
  
}
