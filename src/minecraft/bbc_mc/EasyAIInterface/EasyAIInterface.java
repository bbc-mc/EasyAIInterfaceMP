package bbc_mc.EasyAIInterface;

import java.util.Map;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.src.KeyBinding;
import net.minecraftforge.common.Configuration;
import bbc_mc.EasyAIInterface.util.UtilForgeConfig;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * EasyAIInterface のメインクラス
 * 
 * @author bbc_mc
 */
@Mod(modid = "EasyAIInterface", name = "The Easy AI Interface", version = "1.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class EasyAIInterface {
    //
    // Settings
    //
    public static boolean debug_mode;
    public static int loopWait;
    
    // Item:Search
    public static int idItem_search_block;
    public static int idItem_search_darkPoint;
    public static int idItem_search_enemy;
    public static int idItem_search_item;
    public static int idItem_search_master;
    public static int idItem_search_mob;
    public static int idItem_search_tree;
    
    // Item:Control. (if,,)
    public static int idItem_ctrl_if_enemy_nearby;
    public static int idItem_ctrl_if_hp_low;
    public static int idItem_ctrl_if_item_in_item_inventory;
    public static int idItem_ctrl_if_item_inventory_full;
    
    // Item:Task
    public static int idItem_task_attackByRangedWeapon;
    public static int idItem_task_attackOnCollide;
    public static int idItem_task_eat_food;
    public static int idItem_task_move2target;
    public static int idItem_task_pickupItem;
    public static int idItem_task_playSound;
    public static int idItem_task_swim;
    public static int idItem_task_useItemToTarget;
    
    // Item:System
    public static int idItem_sys_noop;
    public static int idItem_sys_return;
    public static int idItem_sys_start;
    public static int idItem_sys_wait;
    
    // Item:Work
    public static int idItem_work_cutTree;
    
    // EAI_Items 管理クラスへの参照
    public EAI_Items items;
    // EAI_Localize クラスへの参照を保持
    public EAI_Localize localize;
    
    // YoujoMOD との連携用フラグ
    public boolean isLoaded_MOD_Youjo = false;
    
    @SidedProxy(clientSide = "bbc_mc.EasyAIInterface.client.EAIClientProxy", serverSide = "bbc_mc.EasyAIInterface.EAICommonProxy")
    public static EAICommonProxy proxy;
    
    @Mod.Instance("EasyAIInterface")
    public static EasyAIInterface instance;
    
    public EasyAIInterface() {
        instance = this;
    }
    
    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent var1) {
        this.loadConfiguration(new Configuration(var1.getSuggestedConfigurationFile()));
        
        // register Items and recipe
        items = new EAI_Items(this);
        
        proxy.doPreLoadRegistration(this);
    }
    
    @Mod.Init
    public void load(FMLInitializationEvent var1) {
        
        // add Localization
        localize = new EAI_Localize(this);
        
        proxy.doOnLoadRegistration(this);
    }
    
    public boolean onTickInGame(float partialTick, Minecraft mc) {
        return false;
    }
    
    public void addRenderer(Map map) {
    }
    
    public void keyboardEvent(KeyBinding keybinding) {
    }
    
    public void modsLoaded() {
    }
    
    private void loadConfiguration(Configuration var1) {
        try {
            var1.load();
            //
            // System Settings
            //
            debug_mode = UtilForgeConfig.loadConfigBoolean(var1, "debug_mode", "system", false, "");
            loopWait = UtilForgeConfig.loadConfigInt(var1, "loopWait", "system", 4, "");
            
            //
            // Items
            //
            // Item:Search
            idItem_search_block = UtilForgeConfig.loadConfigInt(var1, "Item_search_block", "item", 29001, "");
            idItem_search_darkPoint = UtilForgeConfig.loadConfigInt(var1, "Item_search_darkPoint", "item", 29002, "");
            idItem_search_item = UtilForgeConfig.loadConfigInt(var1, "Item_search_item", "item", 29003, "");
            idItem_search_enemy = UtilForgeConfig.loadConfigInt(var1, "Item_search_enemy", "item", 29004, "");
            idItem_search_master = UtilForgeConfig.loadConfigInt(var1, "Item_search_master", "item", 29005, "");
            idItem_search_mob = UtilForgeConfig.loadConfigInt(var1, "Item_search_mob", "item", 29006, "");
            idItem_search_tree = UtilForgeConfig.loadConfigInt(var1, "Item_search_tree", "item", 29007, "");
            
            // Item:Control. (if,,)
            idItem_ctrl_if_enemy_nearby = UtilForgeConfig.loadConfigInt(var1, "Item_ctrl_if_enemy_nearby", "item", 29101, "");
            idItem_ctrl_if_hp_low = UtilForgeConfig.loadConfigInt(var1, "Item_ctrl_if_hp_low", "item", 29102, "");
            idItem_ctrl_if_item_in_item_inventory = UtilForgeConfig.loadConfigInt(var1, "Item_ctrl_if_item_in_item_inventory", "item", 29103, "");
            idItem_ctrl_if_item_inventory_full = UtilForgeConfig.loadConfigInt(var1, "Item_ctrl_if_item_inventory_full", "item", 29104, "");
            
            // Item:Task
            idItem_task_attackByRangedWeapon = UtilForgeConfig.loadConfigInt(var1, "Item_task_attackByRangedWeapon", "item", 29201, "");
            idItem_task_attackOnCollide = UtilForgeConfig.loadConfigInt(var1, "Item_task_attackOnCollide", "item", 29202, "");
            idItem_task_eat_food = UtilForgeConfig.loadConfigInt(var1, "Item_task_eat_food", "item", 29203, "");
            idItem_task_move2target = UtilForgeConfig.loadConfigInt(var1, "Item_task_move2target", "item", 29204, "");
            idItem_task_pickupItem = UtilForgeConfig.loadConfigInt(var1, "Item_task_pickupItem", "item", 29205, "");
            idItem_task_playSound = UtilForgeConfig.loadConfigInt(var1, "Item_task_playSound", "item", 29206, "");
            idItem_task_swim = UtilForgeConfig.loadConfigInt(var1, "Item_task_swim", "item", 29207, "");
            idItem_task_useItemToTarget = UtilForgeConfig.loadConfigInt(var1, "Item_task_useItemToTarget", "item", 29208, "");
            
            // Item:System
            idItem_sys_noop = UtilForgeConfig.loadConfigInt(var1, "Item_sys_noop", "item", 29301, "");
            idItem_sys_return = UtilForgeConfig.loadConfigInt(var1, "Item_sys_return", "item", 29302, "");
            idItem_sys_start = UtilForgeConfig.loadConfigInt(var1, "Item_sys_start", "item", 29303, "");
            idItem_sys_wait = UtilForgeConfig.loadConfigInt(var1, "Item_sys_wait", "item", 29304, "");
            
            // Item:Work
            idItem_work_cutTree = UtilForgeConfig.loadConfigInt(var1, "Item_work_cutTree", "item", 29401, "");
            
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "EasyAIInterface has a problem loading it's configuration");
        } finally {
            var1.save();
        }
        
    }
    
    // =====================================================
    public static EAI_Manager getManagerInstance() {
        if (instance != null) {
            return instance.getManager();
        } else {
            return null;
        }
    }
    
    public EAI_Manager getManager() {
        return new EAI_Manager(this);
    }
    
    public static void debugPrint(String str) {
        if (debug_mode) {
            System.out.println(str);
        }
    }
}
