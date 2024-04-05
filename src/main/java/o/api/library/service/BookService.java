package o.api.library.service;

import o.api.library.config.ApiResponse;
import o.api.library.dto.BookRequestDto;

public interface BookService {
    ApiResponse create(BookRequestDto bookRequestDto);
    ApiResponse readAll();
    ApiResponse readOne(Long id);
    ApiResponse update(BookRequestDto bookRequestDto);
    ApiResponse delete(Long id);
    ApiResponse boardList(BookRequestDto bookRequestDto);
}
