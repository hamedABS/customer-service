package ir.negah.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.negah.bank.clients.centralBank.CentralBankServiceClient;
import ir.negah.bank.clients.centralBank.domain.ChequeRequestDTO;
import ir.negah.bank.clients.centralBank.domain.ChequeResponseDTO;
import ir.negah.bank.clients.civilRegistry.CivilRegistryServiceClient;
import ir.negah.bank.clients.civilRegistry.domain.CivilRegistrationResponseDTO;
import ir.negah.bank.clients.civilRegistry.domain.ImageResultResponseDTO;
import ir.negah.bank.clients.civilRegistry.domain.ShahkarRequestDTO;
import ir.negah.bank.clients.civilRegistry.domain.ShahkarResponseDTO;
import ir.negah.bank.clients.newsPaper.NewsPaperServiceClient;
import ir.negah.bank.clients.newsPaper.domain.NewsRequestDTO;
import ir.negah.bank.clients.newsPaper.domain.NewsResponseDTO;
import ir.negah.bank.clients.postalCode.PostalCodeServiceClient;
import ir.negah.bank.clients.postalCode.domain.AddressResponseDTO;
import ir.negah.bank.exception.MobileVerificationMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CivilRegistryServiceClient civilRegistryServiceClient;

    @Autowired
    private CentralBankServiceClient centralBankServiceClient;

    @Autowired
    private NewsPaperServiceClient newsPaperServiceClient;

    @Autowired
    private PostalCodeServiceClient postalCodeServiceClient;

    public ShahkarResponseDTO mobileVerification(ShahkarRequestDTO shahkarRequestDTO) throws JsonProcessingException {
        ShahkarResponseDTO shahkarResponseDTO = null;
        try {
            ResponseEntity<ShahkarResponseDTO> shahkarResponseDTOResponseEntity = civilRegistryServiceClient.mobileVerification(shahkarRequestDTO);
            if ((shahkarResponseDTOResponseEntity.getBody().response() == 600)) {
                throw new MobileVerificationMismatchException(shahkarResponseDTOResponseEntity.getBody().comment());
            } else shahkarResponseDTO = shahkarResponseDTOResponseEntity.getBody();
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

    public ChequeResponseDTO estelam(ChequeRequestDTO chequeRequestDTO) throws JsonProcessingException {
        ChequeResponseDTO chequeResponseDTO = new ChequeResponseDTO();

        try {
            ResponseEntity<ChequeResponseDTO> exchange = centralBankServiceClient.estelam(chequeRequestDTO);
            chequeResponseDTO = exchange.getBody();
        } catch (HttpClientErrorException httpException) {
            System.out.println("Error...");
            ObjectMapper mapper = new ObjectMapper();
//            String list = mapper.readValue(httpException.getMessage(), String.class);
            if (httpException.getStatusCode() != HttpStatus.UNAUTHORIZED) {
                Map<String, Object> map = mapper.readValue(httpException.getResponseBodyAsString(), Map.class);
                chequeResponseDTO.setErrorCode(Integer.valueOf(httpException.getStatusCode().toString()));
                chequeResponseDTO.setErrorMsg(map.get("errorDesc").toString());
                return chequeResponseDTO;
            } else {
                chequeResponseDTO.setHasError(Boolean.TRUE);
                chequeResponseDTO.setReturnValue("توکن منقضی شده است");
                chequeResponseDTO.setErrorCode(httpException.getRawStatusCode());
                chequeResponseDTO.setErrorMsg(httpException.getStatusText());
                return chequeResponseDTO;
            }
        }
        return chequeResponseDTO;
    }

    public CivilRegistrationResponseDTO getPersonalInfo(Long birthDate, String nationalCode) throws JsonProcessingException {
        CivilRegistrationResponseDTO civilRegistrationResponseDTO = new CivilRegistrationResponseDTO();
        try {
            ResponseEntity<CivilRegistrationResponseDTO> personalInfo = civilRegistryServiceClient.getPersonalInfo(birthDate, nationalCode);
            civilRegistrationResponseDTO = personalInfo.getBody();
        } catch (HttpClientErrorException httpException) {
            System.out.println("Error...");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(httpException.getResponseBodyAsString(), Map.class);
            civilRegistrationResponseDTO.setErrorCode(Integer.valueOf(httpException.getStatusCode().toString()));
            civilRegistrationResponseDTO.setErrorMsg(map.get("errorDesc").toString());
            return civilRegistrationResponseDTO;
        }
        return civilRegistrationResponseDTO;
    }


    public ImageResultResponseDTO getPhotoByNationalCode(String cardSerialNo, String nationalCode) throws JsonProcessingException {
        ImageResultResponseDTO imageResultResponseDTO = new ImageResultResponseDTO();
        try {
            ResponseEntity<ImageResultResponseDTO> exchange = civilRegistryServiceClient.getPhotoByNationalCode(cardSerialNo, nationalCode);
            imageResultResponseDTO = exchange.getBody();
        } catch (HttpClientErrorException httpException) {
            System.out.println("Error...");
            ObjectMapper mapper = new ObjectMapper();
//            String list = mapper.readValue(httpException.getMessage(), String.class);
            Map<String, Object> map = mapper.readValue(httpException.getResponseBodyAsString(), Map.class);
            imageResultResponseDTO.setErrorCode(Integer.valueOf(httpException.getStatusCode().toString()));
            imageResultResponseDTO.setErrorMsg(map.get("errorDesc").toString());
            return imageResultResponseDTO;
        }
        return imageResultResponseDTO;
    }

    public NewsResponseDTO getNews(NewsRequestDTO newsRequestDTO) throws JsonProcessingException {
        NewsResponseDTO newsResponseDTO = null;
        try {
            ResponseEntity<NewsResponseDTO> news = newsPaperServiceClient.getNews(newsRequestDTO);
            newsResponseDTO = news.getBody();
        } catch (HttpClientErrorException httpException) {
            System.out.println("Error...");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(httpException.getResponseBodyAsString(), Map.class);
            newsResponseDTO.setErrorCode(httpException.getStatusCode().toString());
            newsResponseDTO.setErrorMessage(map.get("errorDesc").toString());
            return newsResponseDTO;
        }
        return newsResponseDTO;
    }

    public AddressResponseDTO getAddress(String postalCode) throws JsonProcessingException {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        try {
            ResponseEntity<AddressResponseDTO> address = postalCodeServiceClient.getAddress(postalCode);
            addressResponseDTO = address.getBody();
        } catch (HttpClientErrorException httpException) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(httpException.getResponseBodyAsString(), Map.class);
            addressResponseDTO.setErrorCode(httpException.getStatusCode().toString());
            addressResponseDTO.setErrorMessage(map.get("errorDesc").toString());
            return addressResponseDTO;
        }
        return addressResponseDTO;
    }
}
