package bbc_mc.EasyAIInterface.item;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;
import bbc_mc.EasyAIInterface.util.SorterDistanceToEntity;

/**
 * AI チップ: 周辺に敵対Mobが存在するかどうかを返す
 * 
 * @author bbc_mc
 */
public class EAI_Item_CTRL_IF_EnemyNearby extends EAI_ItemBase {
    
    private int ret_true;
    private int ret_false;
    
    public EAI_Item_CTRL_IF_EnemyNearby(int par1) {
        super(par1);
        this.setItemName("EAI_CTRL_IF_EnemyNearby");
        this.setItemTypeBranching(true);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        double range = 20D;
        List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(range, 4D, range));
        Collections.sort(list, new SorterDistanceToEntity(entity));
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof EntityMob && ((EntityMob) obj).getAttackTarget().equals(entity)) {
                return this.returnTrue();
            }
        }
        return this.returnFalse();
    }
}
