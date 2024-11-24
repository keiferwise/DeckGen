package com.kif.cardgen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.DeckIdea;

@Component
public class PromptBuilder {
    private static final Logger logger = LoggerFactory.getLogger(PromptBuilder.class);


	@Value("${com.kif.cardDetailsTemplate}")
	private String cardDetailsTemplate;
	
	@Value("${com.kif.cardDetailsTemplateForDeck}")
	private String cardDetailsTemplateForDeck;
	
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
    
    private String twoColourRules = "Since this deck is two coloured, at least half the cards should have both <MANA> mana colours in it's cost";
    
    private String threeColourRules = "Since this deck is Three coloured, Roughtly one third of the cards should have all three of the mana colours(<MANA>) in it's cost, the others can be one or two mana colours, with at least 1 colourless artifact";
    
    private String multicolourRules = "Since this deck is a multicoloured deck, Roughtly one third of the cards should have all the mana colours(<MANA>) in it's cost, the others can be one or two, or three mana colours, with at least 1 colourless artifact";
    
	public PromptBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String buildDeckPrompt(String theme, String mana){
		
    	String prompt = DeckListTemplate.replace("<MYTHEME>", theme);
    	prompt = prompt.replace("<MANA>", mana);
		
		
		return prompt;
	}
	
	
	public String buildCardPrompt(Card card, DeckIdea deckIdea) {
		//System.out.println("Base prompt is : "+ cardDetailsTemplate);
		String prompt = null;
		
		System.out.println(deckIdea.getDeckIdeaId());
		if(deckIdea.getDeckIdeaId().equals("none")) {
			
			prompt = cardDetailsTemplateForDeck.replace("<NAME>", card.getName());
			prompt = prompt.replace("<TYPE>", card.getType());
			prompt = prompt.replace("<SUBTYPE>", card.getSubtype());

			prompt = prompt.replace("<THEME>", deckIdea.getTheme());
			prompt = prompt.replace("<MANACOST>",card.getManaCost());
		}
		else {
			prompt = cardDetailsTemplate.replace("<NAME>", card.getName());
			prompt = prompt.replace("<TYPE>", card.getType());

			prompt = prompt.replace("<THEME>", deckIdea.getTheme());
			prompt = prompt.replace("<MANACOST>",card.getManaCost());
		}
		
		//prompt = prompt.replace("<ARTSTYLE>", deckIdea.getArtStyle());
		
		String cardColours = getDeckColours(deckIdea);
		prompt = addManaRules(prompt,deckIdea);

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
	private String addManaRules(String prompt, DeckIdea idea) {
		int colourCounter=0;
		if(idea.isWhite()) {colourCounter++;}
		if(idea.isBlue()) {colourCounter++;}
		if(idea.isBlack()) {colourCounter++;}
		if(idea.isRed()) {colourCounter++;}
		if(idea.isGreen()) {colourCounter++;}

		if(colourCounter==0) 
		{
			prompt=prompt.replace("<MANARULES", "This deck is colourless, so none of the cards should have any mana cost other than colorless");
		}
		else if(colourCounter==1) {
			prompt=prompt.replace("<MANARULES","");
		}
		else if(colourCounter==2) {
			prompt=prompt.replace("<MANARULES",twoColourRules);
		}
		else if(colourCounter==3) {
			prompt=prompt.replace("<MANARULES",threeColourRules);
		}
		else {
			prompt=prompt.replace("<MANARULES",multicolourRules);

		}
		
		
		
		return prompt;
	}
	private String getDeckColours(DeckIdea idea) {
		String cardColour="";
		//String path = "D:\\deckgen\\src\\main\\resources\\images\\";
		int colourCounter=0;
		//Get Colour Identity String
		if(idea.isWhite()) {cardColour+="white, "; colourCounter++;}
		if(idea.isBlue()) {cardColour+="blue, "; colourCounter++;}
		if(idea.isBlack()) {cardColour+="black, "; colourCounter++;}
		if(idea.isRed()) {cardColour+="red, "; colourCounter++;}
		if(idea.isGreen()) {cardColour+="green, "; colourCounter++;}


		else if(colourCounter == 0) {
			cardColour="Colourless, ";
		}

		return cardColour.substring(0, cardColour.length()-2);
	}
}
