package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;


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
				"'Sulfuras, Hand of Ragnaros', 0, 0",
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
    class Brie{
    	
		@Test
		void testBrieQuality1(){
	        
	        Item[] items = new Item[] {
	                new Item("Aged Brie",0,0)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	
	        assertEquals(2,app.items[0].quality);
	    }
	    
		@Test
		void testBrieQuality2(){
	        
	        Item[] items = new Item[] {
	                new Item("Aged Brie",5,0)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	
	        assertEquals(1,app.items[0].quality);
	    }
		
		@Test
		void testMaxQuality(){
	        
	        Item[] items = new Item[] {
	                new Item("Aged Brie",5,50)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	
	        assertEquals(50,app.items[0].quality);
	    }
    }
	
    @Nested
    class Sulfuras{
    	
		@ParameterizedTest(name = "{0}")
		@CsvSource(value = {
				"'Sulfuras, Hand of Ragnaros', 0, 0",
				"'Sulfuras, Hand of Ragnaros', 5, 50",
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
				"Backstage passes to a TAFKAL80ETC concert, 10, 20, 22",
				"Backstage passes to a TAFKAL80ETC concert, 5, 20, 23",
				"Backstage passes to a TAFKAL80ETC concert, 0, 0, 0"
				})
		void testPassesQuality(String name, int sellin, int quality, int finalQuality){
	        
	        Item[] items = new Item[] {
	                new Item(name,sellin,quality)};
	        
	        GildedRose app = new GildedRose(items);
	        
	        app.updateQuality();
	        
	        assertAll("Attributes",
		        ()-> assertEquals(sellin-1,app.items[0].sellIn),
		        ()-> assertEquals(finalQuality,app.items[0].quality)
		        );
	    }
    }   
}