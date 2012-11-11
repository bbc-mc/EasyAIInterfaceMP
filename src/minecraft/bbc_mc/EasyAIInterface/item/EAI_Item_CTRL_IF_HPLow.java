package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.EasyAIInterface;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: HP が半分以下かどうかを返す
 * 
 * @author bbc_mc
 */
public class EAI_Item_CTRL_IF_HPLow extends EAI_ItemBase {
    
    public EAI_Item_CTRL_IF_HPLow(int par1) {
        super(par1);
        this.setItemName("EAI_CTRL_IF_HPLow");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        if (entity.getHealth() < entity.getMaxHealth() / 2) {
            EasyAIInterface.instance.debugPrint("[" + this.getItemName() + "] HP Low == true");
            return this.returnTrue();
        }
        
        return this.returnFalse();
    }
}
