package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: 処理をスタートする位置を示す
 * 
 * @author bbc_mc
 */
public class EAI_Item_SYS_start extends EAI_ItemBase {
    
    public EAI_Item_SYS_start(int par1) {
        super(par1);
        this.setItemName("EAI_SYS_start");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        return this.returnTrue();
    }
}