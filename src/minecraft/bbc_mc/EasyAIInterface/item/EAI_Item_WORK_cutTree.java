package bbc_mc.EasyAIInterface.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.EasyAIInterface;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;
import bbc_mc.EasyAIInterface.util.UtilPosition;

/**
 * AI チップ: 木を伐る
 * 
 * @author bbc_mc
 */
public class EAI_Item_WORK_cutTree extends EAI_ItemBase {
    
    public EAI_Item_WORK_cutTree(int par1) {
        super(par1);
        this.setItemName("EAI_WORK_cutTree");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        // search Tree nearby
        UtilPosition pos = new UtilPosition(entity);
        Vec3 vec = pos.searchNearestTargetBlock(entity.worldObj, Block.wood.blockID, 10, 2, 10);
        EasyAIInterface.debugPrint("    " + vec);
        UtilPosition posWood = new UtilPosition(vec);
        
        // if too far, move to tree
        if (pos.distanceTo(posWood) > 2.0D) {
            // 十分に接近していないなら、接近する
            float speed = entity.getAIMoveSpeed();
            speed = (speed == 0 ? 0.3F : speed);
            entity.getNavigator().tryMoveToXYZ(posWood.posX, posWood.posY, posWood.posZ, speed);
            EasyAIInterface.debugPrint("  too far " + pos.distanceTo(posWood) + " wood: " + posWood.toString());
            return this.returnFalse();
        } else {
            // cut NOW!!!
            EasyAIInterface.debugPrint("  near " + posWood);
            posWood.iMeta = 16;
            this.cutWood(posWood, entity);
            
            return this.returnTrue();
        }
    }
    
    private boolean cutWood(UtilPosition pos, EntityLiving entity) {
        if (pos.iMeta > 15) {
            List mark = new ArrayList();
            breakWoodRecurscive(entity.worldObj, pos.xCoord, pos.yCoord, pos.zCoord, Block.wood.blockID, Block.leaves.blockID, mark, 5);
            pos.iMeta -= 9999;
        } else {
            pos.iMeta++;
        }
        return true;
    }
    
    private boolean flg_breakSubID = true;
    
    /**
     * 再帰的に木を破壊する
     */
    private boolean breakWoodRecurscive(World world, int x, int y, int z, int targetID, int subID, List mark, int power) {
        int id = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (id != targetID && id != subID) {
            return false;
        }
        UtilPosition pos = new UtilPosition(x, y, z, power);
        for (Object obj : mark) {
            UtilPosition tmppos = (UtilPosition) obj;
            if (tmppos.xCoord == pos.xCoord && tmppos.yCoord == pos.yCoord && tmppos.zCoord == pos.zCoord) {
                if (tmppos.iMeta >= pos.iMeta) {
                    return false;
                }
            }
        }
        
        if (power <= 0) {
            return false;
        }
        
        if (id == targetID || (id == subID && flg_breakSubID)) {
            
            world.setBlockWithNotify(x, y, z, 0);
            
            List lstk = Block.blocksList[id].getBlockDropped(world, x, y, z, meta, 0);
            if (!world.isRemote) {
                for (Object obj : lstk) {
                    ItemStack itm = (ItemStack) obj;
                    world.spawnEntityInWorld(new EntityItem(world, x, y, z, itm));
                }
            }
            power++;
        }
        power--;
        mark.add(pos);
        // recurs
        breakWoodRecurscive(world, x + 1, y, z, targetID, subID, mark, power);
        breakWoodRecurscive(world, x - 1, y, z, targetID, subID, mark, power);
        breakWoodRecurscive(world, x, y, z + 1, targetID, subID, mark, power);
        breakWoodRecurscive(world, x, y, z - 1, targetID, subID, mark, power);
        breakWoodRecurscive(world, x, y + 1, z, targetID, subID, mark, power);
        return true;
    }
}
