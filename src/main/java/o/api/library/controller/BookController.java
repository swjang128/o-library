package o.api.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListDto;
import o.api.library.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "도서 관리 API", description = "도서 관리 API 모음")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "조건에 맞는 도서 목록 조회", description = "조건에 맞는 도서 목록을 가져오는 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/list")
    public ApiResponseManager bookList(BookListDto bookListDto) {
        return bookService.bookList(bookListDto);
    }

    @Operation(summary = "도서 위탁", description = "위탁한 도서를 Book, BookHistory 테이블에 Insert")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/consign")
    public ApiResponseManager consignBook(@Valid @RequestBody BookConsignDto bookConsignDto) {
        return bookService.consignBook(bookConsignDto);
    }

    @Operation(summary = "도서 대여", description = "도서를 대여하고 Book 테이블에 Update, BookHistory 테이블에 Insert")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping("/checkout")
    public ApiResponseManager checkoutBook(@Valid @RequestBody BookCheckoutDto bookCheckoutDto) {
        return bookService.checkoutBook(bookCheckoutDto);
    }
}
