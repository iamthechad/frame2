package org.megatome.frame2.introspector;

final class BeanCommandFactory {
	private static final BeanCommand INDEXED_COMMAND = new IndexedCommand();
	private static final BeanCommand SIMPLE_COMMAND = new SimpleCommand();
	
	private BeanCommandFactory() {
		// Not public
	}

	static BeanCommand getInstance(String key) {
		if (KeyHelper.isIndexed(key)) {
			return INDEXED_COMMAND;
		}

		return SIMPLE_COMMAND;
	}

}
