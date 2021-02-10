package com.poc.db.base;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is used to convert when using LocalDateTime at the entity.
 * When want to persist are need to convert to timestamp for mysql.
 * this class are marked as  use autoplay hence with that we no need to supply at the model,
 * otherwise we need to manually supply at the model
 * {@Code
 *     @Convert(converter = LocalDateTimeConverter.class)
 *     @Column(name = "created_date", nullable = false)
 *     private LocalDateTime createdDate = LocalDateTime.now();
 * }
  */

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        if( null != localDateTime ) {
            return Timestamp.valueOf(localDateTime);
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        if( null != timestamp ) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }
}
