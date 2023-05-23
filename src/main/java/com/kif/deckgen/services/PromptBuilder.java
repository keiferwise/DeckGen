package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kif.deckgen.models.Card;

@Component
public class PromptBuilder {


	@Value("${com.kif.cardDetailsTemplate}")
	private String cardDetailsTemplate;
	
    @Value("${com.kif.deckListTemplate}")
    private String DeckListTemplate;
	
    


    //@Value("${com.kif.creatureRules}")
    private String cRules="Creatures must have a power, toughtness, and a subtype. Rules text is optional but recommended. If there is no rules text, then flavor text in manditory. ";
    
   // @Value("${com.kif.enchatmentRules}")
    private String eRules="Enchantments must have rules text, they have no power or toughness and don't have a subtype.";
    
    //@Value("${com.kif.sorceryRules}")
    private String sRules="Sorceries must have rules text, flavor text is optional. Sorceries have no power or toughness and don't have a subtype.";
    
    //@Value("${com.kif.instantRules}")
    private String iRules="Instants must have rules text, flavor text is optional. Instants have no power or toughness and don't have a subtype.";
    
    //@Value("${com.kif.artifactRules}")
    private String aRules="Artifacts must have rules text, flavor text is optional. Artifacts have no power or toughness and don't have a subtype. ";
    
	public PromptBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String buildDeckPrompt(String theme, String mana){
		
    	String prompt = DeckListTemplate.replace("<MYTHEME>", theme);
    	prompt = prompt.replace("<MANA>", mana);
		
		
		return prompt;
	}
	
	public String buildCardPrompt(Card card, String theme) {
		//System.out.println("Base rompt is : "+ cardDetailsTemplate);
		String prompt = cardDetailsTemplate.replace("<NAME>", card.getName());
		prompt = prompt.replace("<TYPE>", card.getType());
		prompt = prompt.replace("<THEME>", theme);
		prompt = prompt.replace("<MANACOST>",card.getManaCost());
		if(card.getType().toLowerCase().contains("creature")) {
			prompt=prompt.replace("<CARDRULES>",cRules);

		}
		else if(card.getType().toLowerCase().contains("enchantment")) {
			prompt=prompt.replace("<CARDRULES>",eRules);
		}
		else if(card.getType().toLowerCase().contains("artifact")) {
			prompt=prompt.replace("<CARDRULES>",aRules);

		}
		else if(card.getType().toLowerCase().contains("instant")) {
			prompt=prompt.replace("<CARDRULES>",iRules);

		}
		else if(card.getType().toLowerCase().contains("sorcery")) {
			prompt=prompt.replace("<CARDRULES>",sRules);

		}
		else {
			prompt=prompt.replace("<CARDRULES>","");
			System.out.println("ERROR, CARD NOT VALID");
		}
		//System.out.println("The prompt is: "+prompt);
		return prompt;
	}

}
