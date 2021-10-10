package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.constant.NumberConstants;
import com.vosouq.bookkeeping.model.journalizing.JournalizingPage;
import com.vosouq.bookkeeping.repository.JournalizingPageRepository;
import com.vosouq.bookkeeping.service.business.JournalizingPageBusinessService;
import com.vosouq.bookkeeping.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
public class JournalizingPageBusinessServiceImpl implements JournalizingPageBusinessService {

    private final JournalizingPageRepository journalizingPageRepository;

    public JournalizingPageBusinessServiceImpl(JournalizingPageRepository journalizingPageRepository) {
        this.journalizingPageRepository = journalizingPageRepository;
    }


    @Override
    public JournalizingPage getCurrentJournalizingPage() {
        Date today = new Date();

        // find current day journalizing page
        JournalizingPage journalizingPage = journalizingPageRepository.findFirstByOrderByDateDesc();
        try {
            if(AppUtil.datesWithoutTimeIsEqual(journalizingPage.getDate(),today))
                return journalizingPage;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        JournalizingPage newInstance = new JournalizingPage();
        newInstance.setId(null);
        newInstance.setDate(today);
        newInstance.setTime(today);
        newInstance.setNumber(journalizingPage.getNumber() + NumberConstants.ONE_LONG);
        return journalizingPageRepository.save(newInstance);
    }

    private JournalizingPage createJournalizingPage(Date date, Long number) {
        JournalizingPage journalizingPage = new JournalizingPage();
        journalizingPage.setNumber(number);
        journalizingPage.setDate(date);
        journalizingPage.setTime(date);
        return journalizingPageRepository.save(journalizingPage);
    }
}
