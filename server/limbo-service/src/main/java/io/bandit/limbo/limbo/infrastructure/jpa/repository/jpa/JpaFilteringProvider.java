package io.bandit.limbo.limbo.infrastructure.jpa.repository.jpa;


import io.bandit.limbo.limbo.infrastructure.converters.TypeConverter;
import io.bandit.limbo.limbo.modules.shared.model.FilterOptions;
import org.springframework.data.jpa.domain.Specification;


public class JpaFilteringProvider
{
    private TypeConverter typeConverter;

    public JpaFilteringProvider(final TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    public Specification specification(final FilterOptions filterOptions) {
        return new JpaFiltering(typeConverter, filterOptions);
    }
}
