select * from card;

drop table card;

use deckgendb;

Create table card (
card_id varchar(36),
card_name varchar(50),
mana_cost varchar(10),
art_description varchar(300),
card_type varchar(100),
card_subtype varchar(100),
rarity varchar(8),
rules_text varchar(500),
flavor_text varchar(500),
power varchar(2),
toughness varchar(2),
artist varchar(200),
copyright varchar(200),
deck_id varchar(36)
-- type and subtype: can have more than one so maybe make it seperate tables later?
);
