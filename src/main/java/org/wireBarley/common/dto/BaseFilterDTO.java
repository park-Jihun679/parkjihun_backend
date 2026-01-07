package org.wireBarley.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseFilterDTO {

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 100;
    private List<String> sort;

    @JsonIgnore
    public PageRequest getPageRequest() {
        if (this.size == -1) {
            size = Integer.MAX_VALUE;
        }
        Sort sortOrderSum = null;
        if (sort != null) {
            for (String s : sort) {
                String[] sortTokens = s.split("\\.");
                Sort sortItem = Sort.by(sortTokens[0]);
                if (sortTokens.length >= 2 && "desc".equalsIgnoreCase(sortTokens[1])) {
                    sortItem = sortItem.descending();
                }
                sortOrderSum = sortOrderSum == null ? sortItem : sortOrderSum.and(sortItem);
            }
        }

        return sortOrderSum == null ? PageRequest.of(page, size)
            : PageRequest.of(page, size, sortOrderSum);
    }
}

