package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    
    private final int MAX_QUALITY = 50;
    private final int MIN_QUALITY = 0;

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
        	
        	switch(items[i].name) {
        		case "Aged Brie":
    				if(items[i].quality<MAX_QUALITY) {
        				items[i].quality += 1;
        				if(items[i].sellIn==0) {
            				items[i].quality += 1;
        				}
    				}
        			break;

        		case "Sulfuras, Hand of Ragnaros":
        			break;
        			
        		case "Conjured Mana Cake":
    				if(items[i].quality>MIN_QUALITY){
        				items[i].quality -= 2;
        				if(items[i].sellIn==0) {
            				items[i].quality -= 2;
        				}
    				}
        			break;
        			
        		case "Backstage passes to a TAFKAL80ETC concert":
        			if(items[i].sellIn>0) {
            			if(items[i].sellIn>10)
            				items[i].quality += 1;
            			if(items[i].sellIn>5 && items[i].sellIn<=10)
        				items[i].quality += 2;
            			if(items[i].sellIn>0 && items[i].sellIn<=5)
        				items[i].quality += 3;
        			}
        			else {
        				items[i].quality=0;
        			}
        			break;
        		default:
    				if(items[i].quality>MIN_QUALITY){
        				items[i].quality -= 1;
        				if(items[i].sellIn==0) {
            				items[i].quality -= 1;
        				}
    				}
        			break;
        	}
        	if (items[i].sellIn>0 && !items[i].name.equals("Sulfuras, Hand of Ragnaros"))items[i].sellIn -= 1;
	    }
	}
}