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



/*
 * Descripción preliminar
Pero primero, vamos a introducir el sistema:

Todos los artículos (Item) tienen una propiedad sellIn que denota el número de días que tenemos para venderlo
Todos los artículos tienen una propiedad quality que denota cúan valioso es el artículo
Al final de cada día, nuestro sistema decrementa ambos valores para cada artículo mediante el método updateQuality
Bastante simple, ¿no? Bueno, ahora es donde se pone interesante:

Una vez que ha pasado la fecha recomendada de venta, la calidad se degrada al doble de velocidad
La calidad de un artículo nunca es negativa
El "Queso Brie envejecido" (Aged brie) incrementa su calidad a medida que se pone viejo
Su calidad aumenta en 1 unidad cada día
luego de la fecha de venta su calidad aumenta 2 unidades por día
La calidad de un artículo nunca es mayor a 50
El artículo "Sulfuras" (Sulfuras), siendo un artículo legendario, no modifica su fecha de venta ni se degrada en calidad
Una "Entrada al Backstage", como el queso brie, incrementa su calidad a medida que la fecha de venta se aproxima
si faltan 10 días o menos para el concierto, la calidad se incrementa en 2 unidades
si faltan 5 días o menos, la calidad se incrementa en 3 unidades
luego de la fecha de venta la calidad cae a 0
El requerimiento
Hace poco contratamos a un proveedor de artículos conjurados mágicamente. Esto requiere una actualización del sistema:

Los artículos conjurados degradan su calidad al doble de velocidad que los normales
Siéntete libre de realizar cualquier cambio al mensaje updateQuality y agregar el código que sea necesario, mientras que todo siga funcionando correctamente. Sin embargo, no alteres el objeto Item ni sus propiedades ya que pertenecen al goblin que está en ese rincón, que en un ataque de ira te va a liquidar de un golpe porque no cree en la cultura de código compartido.

Notas finales
Para aclarar: un artículo nunca puede tener una calidad superior a 50, sin embargo las Sulfuras siendo un artículo legendario posee una calidad inmutable de 80.package com.gildedrose;
 */
