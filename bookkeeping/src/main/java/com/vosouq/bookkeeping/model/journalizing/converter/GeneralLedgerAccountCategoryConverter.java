package com.vosouq.bookkeeping.model.journalizing.converter;

import com.vosouq.bookkeeping.enumeration.GeneralLegerAccountCategory;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class GeneralLedgerAccountCategoryConverter implements AttributeConverter<GeneralLegerAccountCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GeneralLegerAccountCategory generalLegerAccountCategory) {
        return generalLegerAccountCategory.getCode();
    }

    @Override
    public GeneralLegerAccountCategory convertToEntityAttribute(Integer code) {
        return GeneralLegerAccountCategory.resolve(code);
    }
}
