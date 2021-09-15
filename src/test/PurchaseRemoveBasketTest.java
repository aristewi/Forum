package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.Article;
import domain.Purchase;
import exceptions.PurchaseException;

class PurchaseRemoveBasketTest {

	private static Purchase basket = new Purchase();

	private static double price;

	private static int quantity = 1;

	private static Article article;

	@BeforeAll

	public static void initialize() {

		System.out.println("Inicializo y compruebo ...");

		assertNull(basket.getDate());
		assertEquals(basket.getCost(), 0);

		price = 234.99;

		quantity = 1;

		article = new Article("404", "MASK PINK", price, false, quantity);

	}

	@Test

	@DisplayName("Test 1: La compra se ha cerrado con la fecha")

	public void testRemoveBasket() throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date date = format.parse("23/12/2020");

		basket.buy(date);

		assertNotNull(basket.getDate());

		assertThrows(RuntimeException.class, () -> basket.removeBasket(article, quantity));

	}

	@Test

	@DisplayName("Test 2: No existe el articulo")

	public void testRemoveBasket2() {

		article = null;

		assertThrows(PurchaseException.class, () -> basket.removeBasket(article, quantity));

	}

	@Test

	@DisplayName("Test 3: El numero de unidades es mayor que la del stock")

	public void testRemoveBasket3() {

		quantity = 1;

		article = new Article("404", "MASK PINK", price, false, quantity);

		basket.addBasket(article, quantity);

		int q = 5;

		assertThrows(PurchaseException.class, () -> basket.removeBasket(article, q));

	}

	@Test

	@DisplayName("Test 4: Eliminar una unidad de articulo de la cesta")

	public void testRemoveBasket4() {

		int q = 1;

		double expected = price;

		quantity = 3;

		article = new Article("404", "MASK PINK", price, false, quantity);

		basket.addBasket(article, q);

		try {

			double resultado = basket.removeBasket(article, q);

			assertEquals(expected, resultado);

		} catch (PurchaseException e) {

			e.printStackTrace();

		}

	}

	@Test

	@DisplayName("Test 5: Eliminar un articulo de la cesta actualizando el precio")

	public void testRemoveBasket5() {

		double expected = 234.99;

		quantity = 4;

		article = new Article("404", "MASK PINK", price, false, quantity);

		basket.addBasket(article, quantity);

		try {

			double resultado = basket.removeBasket(article, 1);

			assertEquals( expected,resultado);

		} catch (PurchaseException e) {

			e.printStackTrace();

		}

	}

}