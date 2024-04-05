package o.api.library.dto;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o.api.library.entity.Book;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private int views;

    @Description("게시물 등록에 필요한 객체")
    public Book create() {
        return Book.builder()
                .title(title)
                .content(content)
                .authorId(authorId)
                .build();
    }

    @Description("게시물 수정에 필요한 객체")
    public Book update() {
        return Book.builder()
                .id(id)
                .title(title)
                .content(content)
                .authorId(authorId)
                .build();
    }

}
