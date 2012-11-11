package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: 周辺の "暗い" 場所を探索し、target へ格納する
 * 
 * @author bbc_mc
 */
public class EAI_Item_SEARCH_darkPoint extends EAI_ItemBase {
    
    public EAI_Item_SEARCH_darkPoint(int par1) {
        super(par1);
        this.setItemName("EAI_SEARCH_darkPoint");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        // TODO: implement
        return this.returnTrue();
    }
}
