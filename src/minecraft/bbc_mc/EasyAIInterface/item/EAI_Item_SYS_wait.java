package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: 指定された時間だけ処理を一時停止する
 * 
 * @author bbc_mc
 */
public class EAI_Item_SYS_wait extends EAI_ItemBase {
    
    public EAI_Item_SYS_wait(int par1) {
        super(par1);
        this.setItemName("EAI_SYS_wait");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        // TODO: dummy code
        // wait
        return this.returnTrue();
    }
    
}
