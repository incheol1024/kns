package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionBugDao;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SolutionBugRepo  extends PagingAndSortingRepository<SolutionBugDao, Long> {
}
