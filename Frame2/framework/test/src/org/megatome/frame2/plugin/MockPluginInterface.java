package org.megatome.frame2.plugin;

import java.util.Map;

import javax.servlet.ServletContext;

import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.plugin.PluginInterface;

public class MockPluginInterface implements PluginInterface {
    
   private int _startIndex;
   
   public MockPluginInterface(){
      _startIndex = -1;
   }
   
   public MockPluginInterface(int startIndex)
   {
       _startIndex = startIndex;
   }

	public void destroy(ServletContext context, Map initParams) throws PluginException {
     if (context == null) throw new PluginException("Plugin Init Exception");
     if (initParams.get("throwsDestroyParam") != null){
         _startIndex = 999; // value if throw
         throw new PluginException("got throwsDestoryParam, throw for test");
     }
     _startIndex = 20;
	}

	public void init(ServletContext context, Map initParams) throws PluginException {
      if (context == null) throw new PluginException("Plugin Init Exception");
      if (initParams.get("throwsParam") != null){
         throw new PluginException("got throwsParam, throw for test");
      }
      
      _startIndex = 10;
	}

    /**
     * @return
     */
    public int getStartIndex() {
    	return _startIndex;
    }

}
