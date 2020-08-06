package com.mud.game.worlddata.db.repository;

import com.mud.game.worlddata.db.models.ConsignmentInformation;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ConsignmentInfomationRepository extends SpecificationRepository<ConsignmentInformation, Long> {
    ConsignmentInformation findConsignmentInformationById(Long id);
    Page<ConsignmentInformation> findAll(Specification<ConsignmentInformation> spec, Pageable pageable);
}
