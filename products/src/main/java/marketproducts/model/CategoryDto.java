package marketproducts.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import marketproducts.entity.Category;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;

    public CategoryDto(@NotNull Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
    }
}
