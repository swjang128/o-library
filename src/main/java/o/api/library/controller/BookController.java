package o.api.library.controller;

import lombok.RequiredArgsConstructor;
import o.api.library.config.ApiResponse;
import o.api.library.dto.BookRequestDto;
import o.api.library.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class BookController {
    private final BookService bookService;

    @GetMapping("/book")
    public ApiResponse readAll() {
        return bookService.readAll();
    }

    @GetMapping("/book/{id}")
    public ApiResponse readOne(@PathVariable("id") Long id) {
        return bookService.readOne(id);
    }

    @PostMapping("/book")
    public ApiResponse create(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.create(bookRequestDto);
    }

    @PutMapping("/book")
    public ApiResponse update(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.update(bookRequestDto);
    }

    @DeleteMapping("/book/{id}")
    public ApiResponse delete(@PathVariable("id") Long id) {
        return bookService.delete(id);
    }
}
