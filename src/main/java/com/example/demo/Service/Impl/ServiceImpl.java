package com.example.demo.Service.Impl;

import com.example.demo.LoggerUtil;
import com.example.demo.Model.Model;
import com.example.demo.Service.MyService;
import org.slf4j.Logger;

import org.springframework.stereotype.Service;
import com.example.demo.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ServiceImpl implements MyService {
    private final Repository repository;

    private final Logger logger;

    @Autowired
    public ServiceImpl(Repository repository, LoggerUtil logger) {
        this.repository = repository;
        this.logger = logger.getLogger(ServiceImpl.class);
    }

    @Override
    public List<Model> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<Model> findByUuid(String uuid) {
        Optional<Model> modelObject = repository.findByUuid(uuid);

        if (modelObject.isPresent()) {
            return modelObject;
        } else {
//            throw new CustomExceptionMessage("My Custom Error", HttpStatus.INTERNAL_SERVER_ERROR.getCode());
            logger.info("User not found!");
            return Optional.empty();
        }
    }

    @Override
    public Model saveUser(@RequestBody Model model) {
        model.setUuid((UUID.randomUUID()).toString());
        return repository.save(model);
    }

    @Override
    public Model updateUser(String uuid, Model model) {
        Optional<Model> oldModelObject = repository.findByUuid(uuid);

        if (oldModelObject.isPresent()) {
            Model existingModel = oldModelObject.get();
            existingModel.setFirstName(model.getFirstName());
            existingModel.setLastName(model.getLastName());
            existingModel.setAge(model.getAge());

            return repository.save(existingModel);
        } else {
            logger.info("User not found!");
            return null;
        }
    }

    @Override
    public void deleteUser(String uuid) {
        repository.deleteByUuid(uuid);
        logger.info("User deleted successfully!");
    }
}
