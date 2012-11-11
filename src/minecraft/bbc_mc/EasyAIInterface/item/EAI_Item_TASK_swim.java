package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: 泳ぐ
 * 
 * TODO: this is dummy code.
 * 
 * @author bbc_mc
 */
public class EAI_Item_TASK_swim extends EAI_ItemBase {
    
    public EAI_Item_TASK_swim(int par1) {
        super(par1);
        this.setItemName("EAI_TASK_swim");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        // TODO: implement
        return this.returnTrue();
    }
}
