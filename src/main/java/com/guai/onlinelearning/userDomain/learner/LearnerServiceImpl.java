package com.guai.onlinelearning.userDomain.learner;

import com.guai.onlinelearning.common.PaginationResponse;
import com.guai.onlinelearning.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearnerServiceImpl implements ILearnerService {

    private final LearnerRepository learnerRepository;
    private final LearnerMapper learnerMapper;

    @Override
    public Integer create(LearnerRequest learnerRequest) {
        Learner learner = learnerMapper.toLearner(learnerRequest);
        return learnerRepository.save(learner).getId();
    }

    @Override
    public Integer update(Integer id, LearnerRequest learnerRequest) {
        Learner existingLearner = learnerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Learner.class, "ID", id)
        );

        Learner learner = learnerMapper.mergeForUpdateLearner(id, existingLearner, learnerRequest);
        return learnerRepository.save(learner).getId();
    }

    @Override
    public LearnerResponse findById(Integer id) {
        return learnerRepository.findById(id).map(learnerMapper::toLearnerResponse).orElseThrow(
                () -> new EntityNotFoundException(Learner.class, "ID", id)
        );
    }

    @Override
    public void delete(Integer id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException(Learner.class, "ID", id);
        }
        learnerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return learnerRepository.existsById(id);
    }

    @Override
    public PaginationResponse<LearnerResponse> findAll(Pageable pageable) {
        Page<Learner> learnerPage = learnerRepository.findAll(pageable);
        List<LearnerResponse> resList = learnerPage.stream()
                .map(learnerMapper::toLearnerResponse).toList();
        return new PaginationResponse<>(
                learnerPage.getNumber(),
                learnerPage.getSize(),
                learnerPage.getTotalPages(),
                learnerPage.getTotalElements(),
                resList,
                learnerPage.isFirst(),
                learnerPage.isLast(),
                learnerPage.isEmpty()
        );
    }
}
