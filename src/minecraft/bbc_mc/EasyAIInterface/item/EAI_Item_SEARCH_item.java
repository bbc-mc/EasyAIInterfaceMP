package bbc_mc.EasyAIInterface.item;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;
import bbc_mc.EasyAIInterface.util.SorterDistanceToEntity;

/**
 * AI チップ: 周辺にアイテムが落ちているか調べ、落ちている場合そのアイテムを target レジスタへ記憶する
 * 
 * @author bbc_mc
 */
public class EAI_Item_SEARCH_item extends EAI_ItemBase {
    
    // アイテムを探索する広さ
    private double range = 15D;
    
    public EAI_Item_SEARCH_item(int par1) {
        super(par1);
        this.setItemName("EAI_SEARCH_item");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        // Find Hostile mob
        
        List list = entity.worldObj.getEntitiesWithinAABB(EntityItem.class, entity.boundingBox.expand(this.range, 1D, this.range));
        if (list.size() > 0) {
            Collections.sort(list, new SorterDistanceToEntity(entity));
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                if (obj instanceof EntityItem) {
                    manager.setTarget((EntityItem) obj);
                    return this.returnTrue();
                }
            }
        }
        return this.returnFalse();
    }
}
