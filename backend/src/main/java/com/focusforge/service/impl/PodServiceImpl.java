package com.focusforge.service.impl;

import com.focusforge.dto.response.PodResponse;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.PodRepository;
import com.focusforge.service.PodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodServiceImpl implements PodService {
    public PodServiceImpl(PodRepository podRepository) {
        this.podRepository = podRepository;
    }



    private final PodRepository podRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PodResponse> getAllPods() {
        return podRepository.findAllByOrderByCodeAsc().stream()
                .map(EntityMapper::toPodResponse)
                .collect(Collectors.toList());
    }
}
