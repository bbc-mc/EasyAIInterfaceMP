package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: ターゲット変数に格納されている Entity に対して、近距離攻撃を仕掛ける
 * 
 * @author bbc_mc
 */
public class EAI_Item_TASK_attackOnCollide extends EAI_ItemBase {
    
    public EAI_Item_TASK_attackOnCollide(int par1) {
        super(par1);
        this.setItemName("EAI_TASK_attackOnCollide");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        if (!manager.hasTarget()) {
            return this.returnFalse();
        }
        Entity target = manager.getTargetEntity();
        double distance = entity.getDistanceToEntity(target);
        int damage = 1;
        // nearby enough
        if (distance < 2.0D) {
            target.attackEntityFrom(DamageSource.causeMobDamage(entity), damage);
            return this.returnTrue();
        } else {
            return this.returnFalse();
        }
    }
}
