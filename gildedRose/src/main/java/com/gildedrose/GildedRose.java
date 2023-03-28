package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    
    private final int BRIE_MAX_QUALITY = 50;

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
        	
        	switch(items[i].name) {
        		case "Aged Brie":
    				if(items[i].quality<BRIE_MAX_QUALITY) {
        				items[i].quality += 1;
        				if(items[i].sellIn==0) {
            				items[i].quality += 1;
        				}
        				else {
            				items[i].sellIn -= 1;
        				}
    				}
        			break;

        		case "Sulfuras, Hand of Ragnaros":
        			break;
        			
        		case "Backstage passes to a TAFKAL80ETC concert":
        			if(items[i].sellIn>10) {
        				items[i].quality += 1;
        				items[i].sellIn -= 1;
        			}
        			if(items[i].sellIn>5 && items[i].sellIn<=10) {
        				items[i].quality += 2;
        				items[i].sellIn -= 1;
        			}
        			if(items[i].sellIn>0 && items[i].sellIn<=5) {
        				items[i].quality += 3;
        				items[i].sellIn -= 1;
        			}
        			else {
        				items[i].quality=0;
        			}
        			break;
        		default:
        			if(items[i].sellIn==0) {
	    				items[i].quality -= 2;
        			}else {
        				items[i].quality -= 1;
        				items[i].sellIn -= 1;
        			}
        			
        	
        			
        	}
        		
        	/*
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
            */
        }
    }
}