package br.com.hyper.services;

import br.com.hyper.constants.ErrorCodes;
import br.com.hyper.dtos.requests.FollowRequestDTO;
import br.com.hyper.dtos.responses.FollowResponseDTO;
import br.com.hyper.dtos.responses.pages.FollowPageResponseDTO;
import br.com.hyper.entities.CustomerEntity;
import br.com.hyper.entities.FollowEntity;
import br.com.hyper.exceptions.CustomerException;
import br.com.hyper.exceptions.FollowNotFoundException;
import br.com.hyper.exceptions.InvalidFollowDataException;
import br.com.hyper.repositories.CustomerRepository;
import br.com.hyper.repositories.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    @Autowired
    private final FollowRepository followRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    public FollowResponseDTO save(FollowRequestDTO follow) {

        FollowEntity followEntity;
        try {
            followEntity = modelMapper.map(follow, FollowEntity.class);

            followEntity = followRepository.save(followEntity);

            return modelMapper.map(followEntity, FollowResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new InvalidFollowDataException(ErrorCodes.DUPLICATED_DATA,
                    ErrorCodes.DUPLICATED_DATA.getMessage());
        }
    }

    @Override
    public FollowPageResponseDTO find(String email, Pageable pageable) {

        Page<FollowEntity> followEntities;

        CustomerEntity customer = findByEmailOrThrowUserDataNotFoundException(email);

        if(email != null){
            followEntities = followRepository.findByCustomer(customer, pageable);
        } else {
            followEntities = followRepository.findAll(pageable);
        }
        return modelMapper.map(followEntities, FollowPageResponseDTO.class);
    }


    @Override
    public FollowResponseDTO update(Long id, FollowRequestDTO cart) {
        FollowEntity followCurrent = findByIdOrThrowFollowDataNotFoundException(id);

        followCurrent.setId(cart.getId());

        followRepository.save(followCurrent);

        return modelMapper.map(followCurrent, FollowResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        FollowEntity followCurrent = findByIdOrThrowFollowDataNotFoundException(id);

        modelMapper.map(followCurrent, FollowResponseDTO.class);
        followRepository.delete(followCurrent);
    }
    private FollowEntity findByIdOrThrowFollowDataNotFoundException(Long id) {
        return followRepository.findById(id).orElseThrow(
                () -> new FollowNotFoundException(ErrorCodes.DATA_NOT_FOUND, ErrorCodes.DATA_NOT_FOUND.getMessage()));
    }

    private CustomerEntity findByEmailOrThrowUserDataNotFoundException(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new CustomerException(ErrorCodes.DATA_NOT_FOUND, ErrorCodes.DATA_NOT_FOUND.getMessage()));
    }
}
