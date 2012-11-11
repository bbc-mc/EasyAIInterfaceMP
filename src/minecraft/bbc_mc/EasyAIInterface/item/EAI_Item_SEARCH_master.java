package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityTameable;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

public class EAI_Item_SEARCH_master extends EAI_ItemBase {
    
    public EAI_Item_SEARCH_master(int par1) {
        super(par1);
        this.setItemName("EAI_SEARCH_master");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        if (entity instanceof EntityTameable) {
            manager.setTarget(((EntityTameable) entity).getOwner());
            return this.returnTrue();
            
        } /*else if (entity instanceof EntityYoujo) {
            manager.setTarget(((EntityYoujo) entity).targetPlayer);
            return this.returnTrue();
          }
          */
        /*
         else if ( entity instanceof EntityLittleMaid){

        }
        */
        else {
            manager.clearTarget();
            return this.returnFalse();
        }
    }
}
