package com.mud.game.worlddata.web.admin.service;

import com.mud.game.worlddata.db.models.ConsignmentInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface SpecificationRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
    Iterable<T> findAll(Sort var1);

    Page<T> findAll(Pageable var1);

    Page<T> findAll(Specification<ConsignmentInformation> spec, Pageable pageable);
}
