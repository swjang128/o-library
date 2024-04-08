package o.api.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import o.api.library.config.ApiResponseManager;
import o.api.library.domain.BookSortingMethod;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListRequestDto;
import o.api.library.service.BookService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "도서 관리 API", description = "도서 관리 API 모음")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "조건에 맞는 도서 목록 조회", description = "조건에 맞는 도서 목록을 가져오는 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("list")
    public ApiResponseManager bookList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "TITLE") BookSortingMethod bookSortingMethod,
            @RequestParam(defaultValue = "100") int minimumPrice,
            @RequestParam(defaultValue = "10000") int maximumPrice,
            @RequestParam(defaultValue = "true") boolean status) {
        // 페이징 파라미터를 담은 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(bookSortingMethod.name()));
        // 서비스 호출 시 페이징 관련 파라미터도 전달
        return bookService.bookList(
                BookListRequestDto.builder()
                        .bookSortingMethod(bookSortingMethod)
                        .minimumPrice(minimumPrice)
                        .maximumPrice(maximumPrice)
                        .status(status)
                        .build(),
                pageable
        );
    }

    @Operation(summary = "도서 위탁", description = "위탁한 도서를 Book, BookHistory 테이블에 Insert")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/consign")
    public ApiResponseManager consignBooks(@Validated @RequestBody List<BookConsignDto> bookConsignDtoList) {
        return bookService.consignBooks(bookConsignDtoList);
    }

    @Operation(summary = "도서 대여", description = "도서를 대여하고 Book 테이블에 Update, BookHistory 테이블에 Insert")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping("/checkout")
    public ApiResponseManager checkoutBooks(@Validated @RequestBody List<BookCheckoutDto> bookCheckoutDtoList) {
        return bookService.checkoutBooks(bookCheckoutDtoList);
    }
}
