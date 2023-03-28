package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class GildedRoseTest {

	@Nested
	class Quality{

		@ParameterizedTest(name = "{0}")
		@CsvSource(value = {
				"+5 Dexterity Vest, 0, 20" , 
				"Elixir of the Mongoose, 0, 20",
				"Other, 0, 20",
				})
		void testQualityWith0Days(String name, int sellin, int quality) {
	        
	        Item[] items = new Item[] {
	                new Item(name, sellin,quality)
	        		};
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	
	        assertEquals(18,app.items[0].quality);
	    }
	
		@ParameterizedTest(name = "{0}")
		@CsvSource(value = {
				"+5 Dexterity Vest, 1, 0" , 
				"Aged Brie, 2, 0" ,
				"Elixir of the Mongoose, 1, 0",
				"'Sulfuras, Hand of Ragnaros', 0, 80",
				"Backstage passes to a TAFKAL80ETC concert, 1, 0",
				"Conjured Mana Cake, 3, 0",
				"Other, 3, 0",
				})
		void testQualityPositive(String name, int sellin, int quality) {
	        
	        Item[] items = new Item[] {
	                new Item(name, sellin,quality)
	        		};
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	
	        assertTrue(app.items[0].quality>=0);
	    }

	}
	
    @Nested
    class Days{
		@ParameterizedTest(name = "{0} - {1} to {3}")
		@CsvSource(value = {
				"+5 Dexterity Vest, 1, 0, 0" , 
				"Aged Brie, 2, 0, 1" ,
				"'Sulfuras, Hand of Ragnaros', 0, 80, 0",
				"Other, 0, 0, 0",
				})
		void testSellInDays(String name, int sellin, int quality, int finalDays){
	        
	        Item[] items = new Item[] {
	                new Item(name,sellin,quality)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	        assertEquals(finalDays,app.items[0].sellIn);

	    }
    }
	

    @Nested
    class Brie{
    	
		@ParameterizedTest(name = "{0} - {1} to {3}")
		@CsvSource(value = {
				"Aged Brie, 2, 0, 1" ,
				"Aged Brie, 0, 0, 2" ,
				"Aged Brie, 2, 50, 50" ,
				})
		void testBrieQuality1(String name, int sellIn, int quality, int finalQuality){
	        
	        Item[] items = new Item[] {
	                new Item(name,sellIn,quality)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	
	        assertEquals(finalQuality,app.items[0].quality);
	    }
    }
	
    @Nested
    class Sulfuras{
    	
		@ParameterizedTest(name = "{0}")
		@CsvSource(value = {
				"'Sulfuras, Hand of Ragnaros', 0, 80",
				"'Sulfuras, Hand of Ragnaros', 5, 80",
				})
		void testSulfurasDateAndQuality(String name, int sellin, int quality){
	        
	        Item[] items = new Item[] {
	                new Item(name,sellin,quality)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	        
	        assertAll("Attributes",
		        ()-> assertEquals(sellin,app.items[0].sellIn),
		        ()-> assertEquals(quality,app.items[0].quality)
		        );
	    }
    }
	
    @Nested
    class Passes{
		@ParameterizedTest(name = "{0} - {2} to {3}")
		@CsvSource(value = {
				"Backstage passes to a TAFKAL80ETC concert, 11, 20, 21, 10",
				"Backstage passes to a TAFKAL80ETC concert, 10, 20, 22, 9",
				"Backstage passes to a TAFKAL80ETC concert, 5, 20, 23, 4",
				"Backstage passes to a TAFKAL80ETC concert, 0, 0, 0, 0"
				})
		void testPassesQuality(String name, int sellin, int quality, int finalQuality,int finalSellIn){
	        
	        Item[] items = new Item[] {
	                new Item(name,sellin,quality)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	        
	        assertAll("Attributes",
		        ()-> assertEquals(finalSellIn,app.items[0].sellIn),
		        ()-> assertEquals(finalQuality,app.items[0].quality)
		        );
	    }

    }   
    @Nested
    class Conjured{
    	
		@ParameterizedTest(name = "{0}")
		@CsvSource(value = {
				"Conjured Mana Cake, 1, 6, 4",
				"Conjured Mana Cake, 0, 6, 2",
				})
		void testSulfurasDateAndQuality(String name, int sellin, int quality, int finalQuality){
	        
	        Item[] items = new Item[] {
	                new Item(name,sellin,quality)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	        
		    assertEquals(finalQuality,app.items[0].quality);
	    }
    }
    
    @Nested
    class ToString{
    	
    	@Test
		void testToString(){
	        
	        Item[] items = new Item[] {
	                new Item("Other",1,15)};
	        
	        GildedRose app = new GildedRose(items);
	        	        
		    assertEquals("Other, 1, 15",app.items[0].toString());
	    }
    }
}