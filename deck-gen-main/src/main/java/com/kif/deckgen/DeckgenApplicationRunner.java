package com.kif.deckgen;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.kif.deckgenmodels.daos.CardDao;

@Component
public class DeckgenApplicationRunner implements ApplicationRunner {
	
	@Autowired
	CardDao cardDao;
	
	@Override
    public void run(ApplicationArguments args) {
        System.out.println("Running startup logic with ApplicationRunner");
        // your startup code here
        cardDao.purgeUnfinishedCards();
        
    }

}
