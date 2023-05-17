use deckgendb;

Create table card (
card_id varchar(36),
card_name varchar(50),
mana_cost varchar(50),
art_description varchar(1000),
card_type varchar(100),
card_subtype varchar(100),
rarity varchar(20), -- Mythic rare
rules_text varchar(500),
flavor_text varchar(500),
power varchar(6),
toughness varchar(6),
artist varchar(200),
copyright varchar(200),
deck_id varchar(36)
);
