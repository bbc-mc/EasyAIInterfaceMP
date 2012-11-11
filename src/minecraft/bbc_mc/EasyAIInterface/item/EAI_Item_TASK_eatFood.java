package bbc_mc.EasyAIInterface.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.EasyAIInterface;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;
import bbc_mc.EasyAIInterface.util.UtilInventory;

/**
 * AI チップ: インベントリ内を検索し、ItemFood に属するアイテムがあった場合、食べて HP を回復する
 * 
 * @author bbc_mc
 */
public class EAI_Item_TASK_eatFood extends EAI_ItemBase {
    
    public EAI_Item_TASK_eatFood(int par1) {
        super(par1);
        this.setItemName("EAI_TASK_eatFood");
        this.setItemTypeBranching(false);
    }
    
    @Override
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        super.execute(manager, entity, inventory, slotnum, maxcol);
        
        // 食べ物を持ってたら食べる。次へ進む
        //
        // ロジックをなるだけ壊さないように、検索は後ろから
        for (int i = inventory.getSizeInventory() - 1; i > 0; i--) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemFood) {
                UtilInventory.decrStackSize(inventory, i, 1); // アイテムを 1 つ減らす
                ItemFood food = (ItemFood) stack.getItem();
                
                entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "random.eat", 0.5F,
                        entity.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                
                entity.setEating(true);
                entity.setEntityHealth(entity.getHealth() + 1); // HP を 1 回復する
                EasyAIInterface.debugPrint("[EAI_TASK_eatFood] Eat Food " + stack + " " + slotnum);
                
                return this.returnTrue();
            }
        }
        return this.returnFalse();
    }
    
}
