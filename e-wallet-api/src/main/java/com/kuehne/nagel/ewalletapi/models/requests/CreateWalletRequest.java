package com.kuehne.nagel.ewalletapi.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWalletRequest {

    @NotBlank
    private String walletName;

    @NotBlank
    private String currencyType;

    private UUID userId;
}
