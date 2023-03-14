package com.kuehne.nagel.ewalletapi.repositories;

import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    BigDecimal getBalanceById(@Param("walletId") UUID walletId);

    Collection<Wallet> findAllByUserId(UUID userId);

    Optional<Wallet> findByIdAndUserId(UUID walletId, UUID userId);

    BigDecimal getBalanceByIdAndUserId(UUID walletId, UUID userId);
}
