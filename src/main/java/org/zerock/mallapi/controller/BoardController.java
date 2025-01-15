package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.BoardDTO;
import org.zerock.mallapi.service.BoardService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/board")
public class BoardController {

  private final BoardService service;

  @GetMapping("/list/{bno}")
  public BoardDTO get(
          @PathVariable(name ="bno") Long bno,
          @RequestParam(value = "incrementView", defaultValue = "false") boolean incrementView) {

    return service.get(bno, incrementView);
  }

  @GetMapping("/list")
  public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

    log.info(pageRequestDTO);

    return service.list(pageRequestDTO);
  }

  @PostMapping("/")
  public Map<String, Long> register(@RequestBody BoardDTO boardDTO){

    log.info("BoardDTO: " + boardDTO);

    Long bno = service.register(boardDTO);

    return Map.of("RESULT", bno);
  }

  @PutMapping("/{bno}")
  public Map<String, String> modify(
          @PathVariable(name="bno") Long bno,
          @RequestBody BoardDTO boardDTO) {

    boardDTO.setBno(bno);

    log.info("Modify: " + boardDTO);

    service.modify(boardDTO);

    return Map.of("RESULT", "SUCCESS");
  }

  @DeleteMapping("/{bno}")
  public Map<String, String> remove(@PathVariable(name="bno") Long bno){

    log.info("Remove:  " + bno);

    service.remove(bno);

    return Map.of("RESULT", "SUCCESS");
  }

}
