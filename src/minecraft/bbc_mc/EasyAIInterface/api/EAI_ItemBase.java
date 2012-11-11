package bbc_mc.EasyAIInterface.api;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

import org.lwjgl.input.Keyboard;

import bbc_mc.EasyAIInterface.EAI_Manager;
import bbc_mc.EasyAIInterface.EasyAIInterface;

/**
 * AI チップのベースクラス
 * 
 * AI チップは、本クラスを継承して作成する
 * 
 * @author bbc_mc
 */
public class EAI_ItemBase extends Item {
    
    // 処理移動先を示すための方向列挙
    public enum Direction {
        RIGHT, //
        DOWN_RIGHT, //
        DOWN, //
        DOWN_LEFT, //
        LEFT, //
        UPPER_LEFT, //
        UP, //
        UPPER_RIGHT;
        /**
         * 次の処理対象スロットを取得する
         * 
         * インベントリの壁面処理を行い、次の指示先が壁面を指す場合に -1 を返す
         * 
         * @param inventory
         * @param slotnum
         * @param maxcol
         * @param dir
         * @return nextslot 次の処理対象スロット
         * @return -1 インベントリ壁面を指している
         */
        public static int getNextSlot(IInventory inventory, int slotnum, int maxcol, Direction dir) {
            int ret = 0;
            switch (dir) {
                case RIGHT:
                    ret = 1;
                    if ((slotnum + 1) % maxcol == 0) {
                        return -1;
                    }
                    break;
                case DOWN_RIGHT:
                    ret = 1 + maxcol;
                    if ((slotnum + 1) % maxcol == 0) {
                        return -1;
                    }
                    if (inventory.getSizeInventory() - slotnum < maxcol) {
                        return -1;
                    }
                    break;
                case DOWN:
                    ret = maxcol;
                    if (inventory.getSizeInventory() - slotnum < maxcol) {
                        return -1;
                    }
                    break;
                case DOWN_LEFT:
                    ret = -1 + maxcol;
                    if (inventory.getSizeInventory() - slotnum < maxcol) {
                        return -1;
                    }
                    break;
                case LEFT:
                    ret = -1;
                    if (slotnum % maxcol == 0) {
                        return -1;
                    }
                    break;
                case UPPER_LEFT:
                    ret = -1 - maxcol;
                    if (slotnum % maxcol == 0) {
                        return -1;
                    }
                    if (slotnum < maxcol) {
                        return -1;
                    }
                    break;
                case UP:
                    ret = -maxcol;
                    if (slotnum < maxcol) {
                        return -1;
                    }
                    break;
                case UPPER_RIGHT:
                    ret = 1 - maxcol;
                    if ((slotnum + 1) % maxcol == 0) {
                        return -1;
                    }
                    if (slotnum < maxcol) {
                        return -1;
                    }
                    break;
            }
            return slotnum + ret;
        }
    }
    
    // 条件分岐する場合、条件判定が成功した場合に進む先のスロット番号
    public int value_true;
    // 条件分岐する場合、条件判定が失敗した場合に進む先のスロット番号
    public int value_false;
    // AI チップが分岐するかどうか
    protected boolean isBranchingItem;
    
    public EAI_ItemBase(int par1) {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.isBranchingItem = false;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        int currentDamage = par1ItemStack.getItemDamage();
        int maxDamage;
        if (this.isBranchingItem) {
            maxDamage = 64;
        } else {
            maxDamage = 8;
        }
        if (par3EntityPlayer.isSneaking() || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            currentDamage = (currentDamage + 8) % maxDamage;
        } else {
            currentDamage = ((currentDamage / 8 * 8) + (currentDamage % 8 + 1) % 8) % maxDamage;
        }
        par1ItemStack.setItemDamage(currentDamage);
        EasyAIInterface.instance.debugPrint(" currentItemDamage : " + par1ItemStack.getItemDamage());
        return par1ItemStack;
    }
    
    @Override
    public int getIconFromDamage(int par1) {
        int i = MathHelper.clamp_int(par1, 0, 63);
        // mod_EasyAIInterface.getInstance().mod.debugPrint(" true :" + Direction.values()[i % 8]);
        // mod_EasyAIInterface.getInstance().mod.debugPrint(" false:" + Direction.values()[i / 8]);
        if (this.isBranchingItem) {
            return i % 8 + i / 8 * 16;
        } else {
            return i % 8;
        }
        // return iconIndex;
    }
    
    @Override
    public String getTextureFile() {
        if (this.getItemName() == null || this.getItemName().equals("")) {
            return "";
        } else {
            return "/bbc_mc/EasyAIInterface/texture/" + this.getItemName().replace("item.", "") + ".png";
        }
    }
    
    @Override
    public boolean isDamageable() {
        return true;
    }
    
    /**
     * この関数をオーバーライドし、各 AI チップ固有の処理を記述する
     * 
     * ☆注意 かならず super.execute か setReturnValue を最初に行う事。
     * 
     * setReturnValue により、次の処理対象スロットを算出しているため。
     * 
     * @param manager
     * @param entity
     * @param inventory
     * @param slotnum
     * @param maxcol
     * @return
     */
    public int execute(EAI_Manager manager, EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        this.setReturnValue(inventory, slotnum, maxcol);
        EasyAIInterface.debugPrint("[" + this.getItemName() + "] " + slotnum + " : " + this.returnTrue() + "/" + this.returnFalse());
        // write your code HERE
        
        // return your answer
        return this.returnTrue();
    }
    
    protected void setReturnValue(IInventory inventory, int slotnum, int maxcol) {
        
        this.value_true = Direction.getNextSlot(inventory, slotnum, maxcol, getDirectionTrueFromDamage(inventory, slotnum));
        this.value_false = Direction.getNextSlot(inventory, slotnum, maxcol, getDirectionFalseFromDamage(inventory, slotnum));
    }
    
    protected int returnTrue() {
        return this.value_true;
    }
    
    /**
     * 条件判定失敗時の次の処理対象スロットを返す
     * 
     * 条件分岐無しの場合は、returnTrue 関数と同じ値を返す
     * 
     * @return
     */
    protected int returnFalse() {
        if (this.isBranchingItem) {
            return this.value_false;
        } else {
            return this.value_true;
        }
    }
    
    /**
     * ダメージ値から、条件判定 "成功" 時に次に進む方向を取得する
     * 
     * @param inventory
     * @param slotnum
     * @return
     */
    protected Direction getDirectionTrueFromDamage(IInventory inventory, int slotnum) {
        int currentDamage = inventory.getStackInSlot(slotnum).getItemDamage() % 8;
        return Direction.values()[currentDamage];
    }
    
    /**
     * ダメージ値から、条件判定 "失敗" 時に次に進む方向を取得する
     * 
     * @param inventory
     * @param slotnum
     * @return
     */
    protected Direction getDirectionFalseFromDamage(IInventory inventory, int slotnum) {
        int currentDamage = inventory.getStackInSlot(slotnum).getItemDamage() / 8;
        return Direction.values()[currentDamage];
    }
    
    /**
     * AI チップが条件分岐を行うかどうかを設定する
     * 
     * @see isBranchingItem
     * @param flg
     */
    protected void setItemTypeBranching(boolean flg) {
        this.isBranchingItem = flg;
    }
}
