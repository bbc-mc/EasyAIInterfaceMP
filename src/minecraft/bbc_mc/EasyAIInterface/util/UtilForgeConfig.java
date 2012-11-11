package bbc_mc.EasyAIInterface.util;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * Util(Forge): UtilConfig
 * 
 * Class Method Library for manipulate Forge-Configuration.
 * 
 * @author bbc_mc
 * @date 2012/09/27 1st version.
 * @date 2012/10/26 update to Forge 6.0.1.332
 * @date 2012/11/11 update to Forge 6.0.1.341
 */
public class UtilForgeConfig {
    
    /**
     * Load value from Configuration by Int
     * 
     * @param cfg
     * @param key
     * @param category
     * @param defaultID
     * @param comment
     * @return
     */
    public static int loadConfigInt(Configuration cfg, String key, String category, int defaultID, String comment) {
        Property p;
        if (category == "block") {
            p = cfg.getBlock(key, defaultID);
            // p = cfg.getOrCreateBlockIdProperty(key, defaultID);
        } else {
            // p = cfg.getOrCreateIntProperty(key, type, defaultID);
            p = cfg.get(category, key, defaultID);
        }
        p.comment = comment;
        return Integer.parseInt(p.value);
    }
    
    /**
     * Load value from Configuration by String
     * 
     * @param cfg
     * @param key
     * @param category
     * @param defaultValue
     * @param comment
     * @return
     */
    public static String loadConfigString(Configuration cfg, String key, String category, String defaultValue, String comment) {
        Property p;
        p = cfg.get(category, key, defaultValue);
        // p = cfg.getOrCreateProperty(key, type, defaultValue);
        p.comment = comment;
        
        return p.value;
    }
    
    /**
     * Load value from Configuration by Boolean
     * 
     * @param cfg
     * @param key
     * @param category
     * @param defaultValue
     * @param comment
     * @return
     */
    public static boolean loadConfigBoolean(Configuration cfg, String key, String category, Boolean defaultValue, String comment) {
        Property p;
        p = cfg.get(category, key, defaultValue);
        // p = cfg.getOrCreateBooleanProperty(key, type, defaultBoolean);
        p.comment = comment;
        
        return Boolean.parseBoolean(p.value);
    }
}
