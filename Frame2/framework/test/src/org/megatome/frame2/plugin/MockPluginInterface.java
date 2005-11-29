package org.megatome.frame2.plugin;

import java.util.Map;

import javax.servlet.ServletContext;

import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.plugin.PluginInterface;

public class MockPluginInterface implements PluginInterface {
    
    public static final int STATE_INIT = 1;
    public static final int STATE_DESTROY = 2;
    public static final int STATE_THROW = 3;
    public static final int STATE_NONE = -1;
    
   private int _state;
   
   public MockPluginInterface(){
      _state = STATE_NONE;
   }
   
   public MockPluginInterface(int startIndex)
   {
       _state = startIndex;
   }

	public void destroy(ServletContext context, Map initParams) throws PluginException {
     if (context == null) throw new PluginException("Plugin Init Exception");
     if (initParams.get("throwsDestroyParam") != null){
         _state = STATE_THROW; // value if throw
         throw new PluginException("got throwsDestoryParam, throw for test");
     }
     _state = STATE_DESTROY;
	}

	public void init(ServletContext context, Map initParams) throws PluginException {
      if (context == null) throw new PluginException("Plugin Init Exception");
      if (initParams.get("throwsParam") != null){
         throw new PluginException("got throwsParam, throw for test");
      }
      
      _state = STATE_INIT;
	}

    /**
     * @return
     */
    public int getState() {
    	return _state;
    }

}
