package org.megatome.frame2.template.config;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.jsp.PageContext;

public interface TemplateDefI extends Serializable {
	
	public abstract void setConfigDir(String configDir);

	/**
	 * @return
	 */
	public abstract Map<String, String> getPutParams();

	public abstract String getPutParam(String paramName);

	/**
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return
	 */
	public abstract String getPath();

	public abstract String getTemplateJspPath();

	/**
	 * @param map
	 */
	public abstract void setPutParams(Map<String, String> map);

	/**
	 * @param string
	 */
	public abstract void setName(String name);

	@SuppressWarnings("unchecked")
	public abstract void overridePutParam(String putName, String putValue,
			PageContext context, int scope);

	@SuppressWarnings("unchecked")
	public abstract String getPutParam(String paramName, PageContext context);

	/**
	 * @param string
	 */
	public abstract void setPath(String path);

}