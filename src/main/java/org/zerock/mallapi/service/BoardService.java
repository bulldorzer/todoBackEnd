package org.zerock.mallapi.service;

import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.BoardDTO;

public interface BoardService {

  Long register(BoardDTO boardDTO);

  BoardDTO get(Long bno, boolean incrementView);

  void modify(BoardDTO boardDTO);

  void remove(Long bno);

  PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

}
