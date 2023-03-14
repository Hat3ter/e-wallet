package com.kuehne.nagel.ewalletapi.services;

import com.kuehne.nagel.ewalletapi.models.dtos.UserDetailDto;
import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import com.kuehne.nagel.ewalletapi.models.requests.TransferMoneyRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashInRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashOutRequest;
import com.kuehne.nagel.ewalletapi.repositories.WalletRepository;
import com.kuehne.nagel.ewalletapi.services.wallet.WalletService;
import com.kuehne.nagel.ewalletapi.services.wallet.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc()
public class WalletServiceImplTest {

    private WalletRepository walletRepository;

    private WalletService walletService;

    private static final UUID USER_ID = UUID.randomUUID();


    @BeforeEach
    public void setup() {

        walletRepository = Mockito.mock(WalletRepository.class);
        walletService = new WalletServiceImpl(walletRepository);
        UserDetailDto userDetails = new UserDetailDto();
        userDetails.setId(USER_ID);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Test
    public void testGetWalletById() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setName("My Wallet");
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setCurrencyType("USD");
        when(walletRepository.findByIdAndUserId(walletId, USER_ID)).thenReturn(Optional.of(wallet));

        WalletDto walletDto = walletService.getWalletById(walletId);

        assertEquals(walletId, walletDto.getId());
        assertEquals("My Wallet", walletDto.getName());
        assertEquals(BigDecimal.valueOf(100), walletDto.getBalance());
        assertEquals("USD", walletDto.getCurrencyType());
    }

    @Test
    public void testGetWalletsByUser() {

        Wallet wallet1 = new Wallet();
        wallet1.setId(UUID.randomUUID());
        wallet1.setName("My Wallet 1");
        wallet1.setBalance(BigDecimal.valueOf(100));
        wallet1.setCurrencyType("USD");
        Wallet wallet2 = new Wallet();
        wallet2.setId(UUID.randomUUID());
        wallet2.setName("My Wallet 2");
        wallet2.setBalance(BigDecimal.valueOf(200));
        wallet2.setCurrencyType("USD");
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);
        when(walletRepository.findAllByUserId(USER_ID)).thenReturn(wallets);

        List<WalletDto> walletDtos = walletService.getWalletsByUser();

        assertEquals(2, walletDtos.size());
        assertEquals(wallet1.getId(), walletDtos.get(0).getId());
        assertEquals(wallet1.getName(), walletDtos.get(0).getName());
        assertEquals(wallet1.getBalance(), walletDtos.get(0).getBalance());
        assertEquals(wallet1.getCurrencyType(), walletDtos.get(0).getCurrencyType());
        assertEquals(wallet2.getId(), walletDtos.get(1).getId());
        assertEquals(wallet2.getName(), walletDtos.get(1).getName());
        assertEquals(wallet2.getBalance(), walletDtos.get(1).getBalance());
        assertEquals(wallet2.getCurrencyType(), walletDtos.get(1).getCurrencyType());

    }

    @Test
    public void testCreateWallet() {

        CreateWalletRequest request = new CreateWalletRequest();
        request.setWalletName("My Wallet");
        request.setCurrencyType("USD");
        when(walletRepository.save(Mockito.any(Wallet.class))).thenAnswer(i -> i.getArguments()[0]);

        WalletDto walletDto = walletService.createWallet(request);

        assertEquals("My Wallet", walletDto.getName());
        assertEquals(BigDecimal.ZERO, walletDto.getBalance());
        assertEquals("USD", walletDto.getCurrencyType());
    }

    @Test
    public void testCashIn() {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(50.0);
        WalletCashInRequest cashInRequest = new WalletCashInRequest();
        cashInRequest.setAmount(amount);

        Wallet wallet = new Wallet(walletId, "wallet", BigDecimal.ZERO, "USD", UUID.randomUUID());
        when(walletRepository.findByIdAndUserId(walletId, USER_ID)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        WalletDto walletDto = walletService.cashIn(walletId, cashInRequest);

        Mockito.verify(walletRepository).findByIdAndUserId(walletId, USER_ID);
        Mockito.verify(walletRepository).save(wallet);

        assertEquals(amount, wallet.getBalance());
        assertEquals(wallet.getId(), walletDto.getId());
        assertEquals(wallet.getBalance(), walletDto.getBalance());
        assertEquals(wallet.getName(), walletDto.getName());
        assertEquals(wallet.getCurrencyType(), walletDto.getCurrencyType());
    }

    @Test
    public void testCashOut() {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(10.0);
        WalletCashOutRequest cashOutRequest = new WalletCashOutRequest();
        cashOutRequest.setAmount(amount);

        Wallet wallet = new Wallet(walletId, "wallet", BigDecimal.TEN, "USD", UUID.randomUUID());
        when(walletRepository.findByIdAndUserId(walletId, USER_ID)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        WalletDto walletDto = walletService.cashOut(walletId, cashOutRequest);

        Mockito.verify(walletRepository).findByIdAndUserId(walletId, USER_ID);
        Mockito.verify(walletRepository).save(wallet);

        BigDecimal expectedBalance = BigDecimal.valueOf(0.0);
        assertEquals(expectedBalance, walletDto.getBalance());
        assertEquals(wallet.getId(), walletDto.getId());
        assertEquals(wallet.getBalance(), walletDto.getBalance());
        assertEquals(wallet.getName(), walletDto.getName());
        assertEquals(wallet.getCurrencyType(), walletDto.getCurrencyType());
    }


    @Test
    public void testTransferMoney() {

        UUID fromWalletId = UUID.randomUUID();
        UUID toWalletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.TEN;

        Wallet fromWallet = new Wallet(fromWalletId, "wallet", BigDecimal.TEN, "USD", UUID.randomUUID());
        Wallet toWallet = new Wallet(toWalletId, "wallet", BigDecimal.ZERO, "USD", UUID.randomUUID());

        when(walletRepository.findByIdAndUserId(fromWalletId, USER_ID)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findByIdAndUserId(toWalletId, USER_ID)).thenReturn(Optional.of(toWallet));
        when(walletRepository.saveAll(Arrays.asList(fromWallet, toWallet))).thenReturn(Arrays.asList(fromWallet, toWallet));

        walletService.transferMoney(fromWalletId, new TransferMoneyRequest(toWalletId, amount));

        assertEquals(BigDecimal.ZERO, fromWallet.getBalance());
        assertEquals(BigDecimal.TEN, toWallet.getBalance());
        Mockito.verify(walletRepository).saveAll(Arrays.asList(fromWallet, toWallet));
    }
}