package o.api.library.specification;

import jakarta.persistence.criteria.Predicate;
import o.api.library.domain.BookSortingMethod;
import o.api.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withSortingMethod(BookSortingMethod sortingMethod, Integer minimumPrice, Integer maximumPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            // Sorting
            switch (sortingMethod) {
                case TITLE -> query.orderBy(criteriaBuilder.asc(root.get("title")));
                case ISBN -> query.orderBy(criteriaBuilder.asc(root.get("isbn")));
                case PRICE -> query.orderBy(criteriaBuilder.asc(root.get("price")));
                default -> throw new IllegalArgumentException("Invalid sorting method: " + sortingMethod);
            }
            // Filtering by price range
            if (minimumPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minimumPrice));
            }
            if (maximumPrice != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), maximumPrice));
            }
            return predicate;
        };
    }
}