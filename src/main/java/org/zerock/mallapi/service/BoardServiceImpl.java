package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Board;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.BoardDTO;
import org.zerock.mallapi.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor  // 생성자 자동 주입
public class BoardServiceImpl implements BoardService {

  // 자동주입 대상은 final로
  private final ModelMapper modelMapper;

  private final BoardRepository boardRepository;

  @Override
  public Long register(BoardDTO boardDTO) {

    log.info("Registering board...");

    Board board = modelMapper.map(boardDTO, Board.class);

    Board savedBoard = boardRepository.save(board);

    return savedBoard.getBno();

  }

  @Override
  public BoardDTO get(Long bno, boolean incrementView) {

    Optional<Board> result = boardRepository.findById(bno);

    Board board = result.orElseThrow();
    if(incrementView) {
      board.incrementViewCount();
    }

    BoardDTO dto = modelMapper.map(board, BoardDTO.class);

    return dto;
  }

  @Override
  public void modify(BoardDTO boardDTO) {

    Optional<Board> result = boardRepository.findById(boardDTO.getBno());

    Board board = result.orElseThrow();

    board.changeTitle(boardDTO.getTitle());
    board.changeContent(boardDTO.getContent());
    board.changePostDate(boardDTO.getPostDate());

    boardRepository.save(board);

  }

  @Override
  public void remove(Long bno) {

    boardRepository.deleteById(bno);

  }

  @Override
  public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

    Pageable pageable =
            PageRequest.of(
                    pageRequestDTO.getPage() - 1 ,  // 1페이지가 0이므로 주의
                    pageRequestDTO.getSize(),
                    Sort.by("bno").descending());

    Page<Board> result = boardRepository.findAll(pageable);

    List<BoardDTO> dtoList = result.getContent().stream()
            .map(board -> modelMapper.map(board, BoardDTO.class))
            .collect(Collectors.toList());

    long totalCount = result.getTotalElements();

    PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(totalCount)
            .build();

    return responseDTO;
  }
}
