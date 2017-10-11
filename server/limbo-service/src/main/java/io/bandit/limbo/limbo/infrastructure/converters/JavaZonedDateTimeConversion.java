package io.bandit.limbo.limbo.infrastructure.converters;

import com.toddfast.util.convert.TypeConverter;
import org.joda.time.DateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class JavaZonedDateTimeConversion implements TypeConverter.Conversion<Object> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[0];
    }

    @Override
    public Object convert(Object o) {

        if (o instanceof String) {
            DateTimeFormatter f = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
            return ZonedDateTime.parse(o.toString(), f);
        }

        return DateTime.parse(o.toString()).toString("yyyy-MM-dd HH:mm:ss");
    }
}
