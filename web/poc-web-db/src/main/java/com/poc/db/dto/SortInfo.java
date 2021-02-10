package com.poc.db.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SortInfo {
    private String field;
    private String direction;

    public SortInfo(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }
}
