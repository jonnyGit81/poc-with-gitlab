package com.poc.db.util;

import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

public final class SortDirectionUtil {
    public enum Direction {
        ASC("asc"),
        DESC("desc");

        private String name;

        Direction(String name) {
            this.name = name;
        }

        public String getSort() {
            return this.name;
        }

        public Direction getDirection(String str) {
            return Arrays.stream(Direction.values()).filter(d -> d.name.equals(str)).findFirst().orElse(null);
        }
    }

    private SortDirectionUtil() {
        throw new AssertionError();
    }

    public static Sort.Direction getSortDirection(String direction) {
        if(ObjectUtils.nullSafeEquals(Direction.ASC.getSort(), direction) ) {
            return Sort.Direction.ASC;
        }else if( ObjectUtils.nullSafeEquals(Direction.DESC.getSort(), direction) ) {
            return Sort.Direction.DESC;
        }else {
            // defaul to ASC
            return Sort.Direction.ASC;
        }
    }
}
