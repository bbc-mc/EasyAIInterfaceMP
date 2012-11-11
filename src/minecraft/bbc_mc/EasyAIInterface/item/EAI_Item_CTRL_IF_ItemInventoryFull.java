package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: アイテムインベントリが満杯かどうか
 * 
 * @author bbc_mc
 */
public class EAI_Item_CTRL_IF_ItemInventoryFull extends EAI_ItemBase {
    
    public EAI_Item_CTRL_IF_ItemInventoryFull(int par1) {
        super(par1);
        this.setItemName("EAI_CTRL_IF_ItemInventoryFull");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        // TODO: implement
        return this.returnTrue();
    }
}
