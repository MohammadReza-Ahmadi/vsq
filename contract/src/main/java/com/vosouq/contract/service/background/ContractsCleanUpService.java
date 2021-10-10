package com.vosouq.contract.service.background;

import com.vosouq.contract.config.SchedulingSwitch;
import com.vosouq.contract.model.ContractState;
import com.vosouq.contract.model.ContractTemplate;
import com.vosouq.contract.model.SuggestionState;
import com.vosouq.contract.repository.ContractRepository;
import com.vosouq.contract.repository.ContractTemplateRepository;
import com.vosouq.contract.repository.SuggestionRepository;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Service
@Slf4j
public class ContractsCleanUpService {

    private final ContractRepository contractRepository;
    private final ContractTemplateRepository contractTemplateRepository;
    private final SuggestionRepository suggestionRepository;
    private final int shelfLife;
    private final SchedulingSwitch schedulingSwitch;

    public ContractsCleanUpService(ContractRepository contractRepository,
                                   ContractTemplateRepository contractTemplateRepository,
                                   SuggestionRepository suggestionRepository,
                                   @Value("${cleanUpService.staleContracts.shelfLife:3600000}") int shelfLife,
                                   SchedulingSwitch schedulingSwitch) {
        this.contractTemplateRepository = contractTemplateRepository;
        this.suggestionRepository = suggestionRepository;
        this.schedulingSwitch = schedulingSwitch;
        log.debug("Creating cleanup service with contract shelf life of {}", shelfLife);
        this.contractRepository = contractRepository;
        this.shelfLife = shelfLife;
    }

    /**
     * Cleans stale contracts that are signed by {@code Seller} but are not signed by {@code Buyer}
     * after a specific configurable timeout.
     */
    @Scheduled(fixedRateString = "${cleanUpService.staleContracts.rate:300000}")
    @SchedulerLock(name = "stale_contracts",
            lockAtLeastFor = "${cleanUpService.staleContracts.lockAtLeastFor:240000}",
            lockAtMostFor = "${cleanUpService.staleContracts.lockAtMostFor:600000}")
    @Transactional
    public void removeStaleContracts() {
        if(!schedulingSwitch.isEnabled()){
            log.warn("Scheduling is disabled by SchedulingSwitch Bean configuration.");
            return;
        }
        log.info("Started running cleanup task for stale contracts.");
        contractRepository.findBySellerSignDateBeforeAndStateIs(
                new Timestamp(System.currentTimeMillis() - shelfLife),
                ContractState.SIGN_BY_SELLER)
                .forEach(contract -> {
                    contract.setState(ContractState.STALE);
                    contract.setUpdateDate(nowInTimestamp());
                    contractRepository.save(contract);
                    ContractTemplate contractTemplate = contract.getContractTemplate();
                            suggestionRepository.findByContractTemplateAndBuyerIdOrderByCreateDateDesc(contractTemplate,
                                    contract.getBuyerId()).forEach(suggestion -> {
                                suggestion.setSuggestionState(SuggestionState.DENIED);
                                suggestion.setSuggestionStateDate(nowInTimestamp());
                                suggestion.setUpdateDate(nowInTimestamp());
                                suggestionRepository.save(suggestion);
                            });

                    log.debug("Found Template: {}", contractTemplate);
                    contractTemplate.setNumberOfRepeats(contractTemplate.getNumberOfRepeats() + 1);
                    contractTemplate.setUpdateDate(nowInTimestamp());
                    contractTemplateRepository.save(contractTemplate);
                    log.debug("Updated template: {}", contractTemplate);
                });
        log.info("Finished cleanup task.");
    }
}
