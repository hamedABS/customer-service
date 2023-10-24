package ir.negah.bank.service.impl;

import ir.negah.bank.clients.external_ervices.ExternalServicesClient;
import ir.negah.bank.domain.dto.MobileVerificationRequestDTO;
import ir.negah.bank.domain.dto.ShahkarResponseDTO;
import ir.negah.bank.service.CustomerService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲۱
 * TIME: ۱۸:۰۷
 */

@Service
public record CustomerServiceImpl(RestTemplate restTemplate, Environment environment,
                                  ExternalServicesClient externalServicesClient) implements CustomerService {

    @Override
    public ShahkarResponseDTO validateMobileNumber(MobileVerificationRequestDTO requestDTO) {
        HttpEntity<MobileVerificationRequestDTO> requestEntity = new HttpEntity<>(requestDTO);
        String activeProfile = environment.getActiveProfiles()[0];
        System.out.println(activeProfile);

        ResponseEntity<ShahkarResponseDTO> exchange =
                restTemplate.exchange("http://localhost:9004",
                        HttpMethod.POST,
                        requestEntity,
                        ShahkarResponseDTO.class);
        return exchange.getBody();
    }
}
