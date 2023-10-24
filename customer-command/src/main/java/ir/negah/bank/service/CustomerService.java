package ir.negah.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.negah.bank.clients.civilRegistry.CivilRegistryServiceClient;
import ir.negah.bank.clients.civilRegistry.domain.ShahkarRequestDTO;
import ir.negah.bank.clients.civilRegistry.domain.ShahkarResponseDTO;
import ir.negah.bank.exception.MobileVerificationMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CivilRegistryServiceClient civilRegistryServiceClient;

    public ShahkarResponseDTO mobileVerification(ShahkarRequestDTO shahkarRequestDTO) throws JsonProcessingException {
        ShahkarResponseDTO shahkarResponseDTO = null;
        try {
            ResponseEntity<ShahkarResponseDTO> shahkarResponseDTOResponseEntity = civilRegistryServiceClient.mobileVerification(shahkarRequestDTO);
            if ((shahkarResponseDTOResponseEntity.getBody().response()==600)) {
                throw new MobileVerificationMismatchException(shahkarResponseDTOResponseEntity.getBody().comment());
            }else  shahkarResponseDTO = shahkarResponseDTOResponseEntity.getBody();
        } catch (HttpClientErrorException httpException) {
            System.out.println("Error...");
            ObjectMapper mapper = new ObjectMapper();
//            String list = mapper.readValue(httpException.getMessage(), String.class);

            Map<String, Object> map = mapper.readValue(httpException.getResponseBodyAsString(), Map.class);
            shahkarResponseDTO.errorCode(httpException.getStatusCode().toString());
            shahkarResponseDTO.errorMessage(map.get("errorDesc").toString());
            return shahkarResponseDTO;
        }
        return shahkarResponseDTO;
    }
}
