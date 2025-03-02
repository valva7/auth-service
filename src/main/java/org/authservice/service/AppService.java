package org.authservice.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.authservice.entity.App;
import org.authservice.repository.AppRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppRepository appRepository;

    public List<App> listApps(){
        return appRepository.findAll();
    }
}