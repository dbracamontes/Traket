/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.utils.converter;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author dani
 */
@Converter(autoApply = true)
public class LocalDateTimeAttrConverter implements AttributeConverter<LocalDateTime, Timestamp>{

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return (attribute == null ? null : Timestamp.valueOf(attribute));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return (dbData == null ? null : dbData.toLocalDateTime());
    }
    
}
