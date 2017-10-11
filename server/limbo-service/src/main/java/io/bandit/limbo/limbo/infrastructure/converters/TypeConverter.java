package io.bandit.limbo.limbo.infrastructure.converters;

import javax.inject.Named;

import static com.toddfast.util.convert.TypeConverter.registerTypeConversion;

@Named("TypeConverter")
public class TypeConverter {

    public TypeConverter() {
        registerTypeConversion("java.time.ZonedDateTime", new JavaZonedDateTimeConversion());
        registerTypeConversion("java.util.Date", new JavaDateConversion());
    }

    public Object convert(String className, Object value) {

        return com.toddfast.util.convert.TypeConverter.convert(className, value);
    }
}
