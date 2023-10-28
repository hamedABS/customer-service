package ir.negah.bank.service;

import ir.negah.bank.domain.dto.MobileVerificationRequestDTO;
import ir.negah.bank.domain.dto.ShahkarResponseDTO;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲۱
 * TIME: ۱۸:۰۵
 */

public interface CustomerCommandService {

    ShahkarResponseDTO validateMobileNumber(MobileVerificationRequestDTO requestDTO);
}
