package org.megatome.frame2.util;

import java.text.MessageFormat;
import java.util.Locale;

import junit.framework.TestCase;

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
}
