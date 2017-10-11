package io.bandit.limbo.limbo.infrastructure.converters;

import com.toddfast.util.convert.TypeConverter;
import org.joda.time.DateTime;

public class JavaDateConversion implements TypeConverter.Conversion<Object> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[0];
    }

    @Override
    public Object convert(Object o) {

        if (o instanceof String) {
            return DateTime.parse(o.toString()).toDate();
        }

        return DateTime.parse(o.toString()).toString("yyyy-MM-dd HH:mm:ss");
    }
}
