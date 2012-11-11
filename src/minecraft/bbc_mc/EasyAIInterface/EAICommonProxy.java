package bbc_mc.EasyAIInterface;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class EAICommonProxy implements IGuiHandler {
    
    public void doOnLoadRegistration(EasyAIInterface mod) {
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    
    public void doPreLoadRegistration(EasyAIInterface mod) {
    }
    
}
