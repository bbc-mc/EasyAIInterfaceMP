package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: 周辺の "木" を探索し、target へ位置を格納する
 * 
 * @author bbc_mc
 */
public class EAI_Item_SEARCH_tree extends EAI_ItemBase {
    
    public EAI_Item_SEARCH_tree(int par1) {
        super(par1);
        this.setItemName("EAI_SEARCH_tree");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        // TODO: implement
        return this.returnTrue();
    }
}
