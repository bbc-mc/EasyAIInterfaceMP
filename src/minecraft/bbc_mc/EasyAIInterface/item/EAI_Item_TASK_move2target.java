package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import net.minecraft.src.Vec3;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;

/**
 * AI チップ: ターゲット変数に格納されている Entity/位置 へ移動する
 * 
 * @author bbc_mc
 */
public class EAI_Item_TASK_move2target extends EAI_ItemBase {
    
    public EAI_Item_TASK_move2target(int par1) {
        super(par1);
        this.setItemName("EAI_TASK_move2target");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        if (!manager.hasTarget()) {
            // no target.
            return this.returnFalse();
        } else {
            float speed = entity.getAIMoveSpeed();
            speed = (speed == 0 ? 0.3F : speed);
            if (manager.isEntity()) {
                Entity target = manager.getTargetEntity();
                if (target != null) {
                    if (entity.getDistanceToEntity(target) > 2.5D) {
                        entity.getNavigator().tryMoveToXYZ(target.posX, target.posY, target.posZ, speed);
                    }
                    return this.returnTrue();
                } else {
                    return this.returnFalse();
                }
            } else {
                Vec3 targetPos = manager.getTargetPos();
                if (entity.getNavigator().tryMoveToXYZ(targetPos.xCoord, targetPos.yCoord, targetPos.zCoord, speed)) {
                    return this.returnTrue();
                } else {
                    return this.returnFalse();
                }
            }
        }
    }
}