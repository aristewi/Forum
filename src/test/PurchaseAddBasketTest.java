package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.DisplayName;

import domain.Article;

import domain.Purchase;

import exceptions.PurchaseException;

public class PurchaseAddBasketTest {

	private static Purchase basket = new Purchase();
	private double price;

	private int quantity = 1;

	private static Article article;

	@BeforeAll

	public static void initialize() {

		System.out.println("Inicializo y compruebo ...");

		assertNull(basket.getDate());

		assertEquals(basket.getCost(), 0);

		double price = 234.99;

		int quantity = 1;

		article = new Article("404", "MASK PINK", price, false, quantity);

	}

	@Test

	@DisplayName("Test 1: El precio obtenido y el esperado es el mismo ")

	public void testAddBasket1() {

		quantity = 1;

		price = 234.99;

		double expected = quantity * price;

		double obtained = basket.addBasket(article, quantity);

		assertEquals(expected, obtained);

		try {

			basket.removeBasket(article, quantity);

			assertTrue(true);

		} catch (PurchaseException e) {

			fail("Impossible!!");

		}

	}

	@Test

	@DisplayName("Test 2: La cantidad deseada supera a la añadida")

	public void testAddBasket2() {

		quantity = 3;

		assertThrows(RuntimeException.class, () -> basket.addBasket(article, quantity));

	}

	@Test

	@DisplayName("Test 3: The purchase is closed")

	public void testAddBasket3() throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date date = format.parse("23/12/2020");

		basket.buy(date);

		assertThrows(RuntimeException.class, () -> basket.addBasket(article, quantity));

	}

	
}
