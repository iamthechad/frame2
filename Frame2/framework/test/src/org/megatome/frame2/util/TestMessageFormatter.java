package org.megatome.frame2.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.util.MessageFormatter;

import com.clarkware.profiler.Profiler;
/**
 * 
 */
public class TestMessageFormatter extends TestCase {

	public void testFormatting() {
		assertEquals("Is this Frame2?", MessageFormatter.format("Is this Frame2?", null));
		assertEquals(
			"Is this really Frame2?",
			MessageFormatter.format("Is this {0} Frame2?", new String[] { "really" }));
		assertEquals(
			"Is this 'really' Frame2?",
			MessageFormatter.format("Is this ''{0}'' Frame2?", new String[] { "really" }));
		assertEquals(
			"Is this \"really\" Frame2?",
			MessageFormatter.format("Is this \"{0}\" Frame2?", new String[] { "really" }));
		assertEquals(
			"Is this 100,000 Frame2?",
			MessageFormatter.format("Is this {0} Frame2?", new Object[] { new Integer(100000)}));
	}

	public void testFormatting_Locale() {
		assertEquals(
			"Is this Frame2?",
			MessageFormatter.format("Is this Frame2?", Locale.FRENCH, null));
		assertEquals(
			"Is this really Frame2?",
			MessageFormatter.format("Is this {0} Frame2?", Locale.FRENCH, new String[] { "really" }));
		assertEquals(
			"Is this 'really' Frame2?",
			MessageFormatter.format(
				"Is this ''{0}'' Frame2?",
				Locale.FRENCH,
				new String[] { "really" }));
		assertEquals(
			"Is this \"really\" Frame2?",
			MessageFormatter.format(
				"Is this \"{0}\" Frame2?",
				Locale.FRENCH,
				new String[] { "really" }));
		assertEquals(
			"Is this 10,01 Frame2?",
			MessageFormatter.format(
				"Is this {0} Frame2?",
				Locale.FRENCH,
				new Object[] { new Double(10.01)}));
	}

	public void testGetFormatter() {
		MessageFormat format = MessageFormatter.getFormat("Is this Frame2?", Locale.UK);
		assertNotNull(format);
		assertEquals(Locale.UK, format.getLocale());
		assertEquals("Is this Frame2?", format.format(null));
		assertSame(format, MessageFormatter.getFormat("Is this Frame2?", Locale.UK));
	}

	/**
	 * This crudely compares caching and newing MessageFormat objects.  Current
	 * measurements (JDK 1.4) indicate not much difference.  Updates to the JVM may motivate
	 * revisiting the measurements.
	 */

	public void xtestCacheTrade() {
		int count = 2500;
		String pattern = "a simple pattern {0} {1}";
		Locale locale = Locale.UK;
		Object[] args = new Object[] { new Integer(5), "ribbit" };

		Map cache = new HashMap();

      MessageFormat format = null;

      format = new MessageFormat(pattern, locale);   

		cache.put(locale + "." + pattern, format);

		Profiler.enableMemory(true);

		Profiler.begin("Cached");

		for (int i = 0; i < count; i++) {
			MessageFormat cachedFormat = (MessageFormat) cache.get(locale + "." + pattern);
			cachedFormat.format(args);
		}

		Profiler.end("Cached");

		Profiler.begin("Not cached");

		for (int i = 0; i < count; i++) {
         format = new MessageFormat(pattern, locale);   

			format.format(args);
		}

		Profiler.end("Not cached");

		Profiler.begin("Double cached");

		for (int i = 0; i < count; i++) {
			format = MessageFormatter.getFormat(pattern, locale);
			format.format(args);
		}

		Profiler.end("Double cached");
		Profiler.print();

	}

}
