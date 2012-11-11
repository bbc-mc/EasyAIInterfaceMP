package bbc_mc.EasyAIInterface.client;

import java.util.Map;

import net.minecraft.src.Item;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import bbc_mc.EasyAIInterface.EAICommonProxy;
import bbc_mc.EasyAIInterface.EasyAIInterface;
import cpw.mods.fml.client.FMLClientHandler;

public class EAIClientProxy extends EAICommonProxy {
    
    public void doOnLoadRegistration(EasyAIInterface mod) {
        Map<String, Item> itemmap = mod.items.getEAIItemList();
        
        for (Item item : itemmap.values()) {
            String texture = "/bbc_mc/EasyAIInterface/texture/" + item.getItemName().replace("item.", "") + ".png";
            MinecraftForgeClient.preloadTexture(texture);
        }
    }
    
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
    
    public void doPreLoadRegistration(EasyAIInterface mod) {
        MinecraftForge.EVENT_BUS.register(new EAISounds());
    }
    
}
