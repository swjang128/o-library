package o.api.library.service;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.api.library.config.ApiResponse;
import o.api.library.dto.BookRequestDto;
import o.api.library.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Override
    public ApiResponse create(BookRequestDto bookRequestDto) {
        return ApiResponse.success(bookRepository.save(bookRequestDto.create()));
    }

    @Override
    @Description("게시물 전체 조회")
    public ApiResponse readAll() {
        return ApiResponse.success(bookRepository.findAll());
    }

    @Override
    @Description("특정 게시물 조회")
    public ApiResponse readOne(Long id) {
        return ApiResponse.success(bookRepository.findById(id));
    }

    @Override
    @Description("특정 게시물 수정")
    public ApiResponse update(BookRequestDto bookRequestDto) {
        return ApiResponse.success(bookRepository.save(bookRequestDto.update()));
    }

    @Override
    @Description("특정 게시물 삭제")
    public ApiResponse delete(Long id) {
        bookRepository.deleteById(id);
        return ApiResponse.ok();
    }

    @Override
    @Description("(v2) 조건에 맞는 게시판 목록 조회")
    public ApiResponse boardList(BookRequestDto bookRequestDto) {
        // TODO: 조건에 맞게 게시판 목록을 조회하도록 추가할 것
        return ApiResponse.success(bookRepository.findAll());
    }
}
