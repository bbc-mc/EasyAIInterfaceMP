package bbc_mc.EasyAIInterface;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;
import bbc_mc.EasyAIInterface.api.EAI_ItemBase;
import bbc_mc.EasyAIInterface.item.EAI_Item_SYS_start;

/**
 * AI チップ処理クラス
 * 
 * 登録されたインベントリ内を参照し、認識している currentSlot にある AI チップを実行する
 * 
 * @author bbc_mc
 */
public class EAI_Manager {
    // 処理を開始するスロット
    public int slot_start;
    
    // 本EAI_Manager が保持される Entity. AI 処理の結果として動くEntity.
    private EntityLiving parentEntity;
    // 所有アイテムのインベントリ。アイテムの利用等に使用する
    private IInventory itemInventory;
    // AIチップ配置用インベントリ
    private IInventory logicInventory;
    
    // 処理対象スロット
    private int currentSlot;
    // AI チップ配置用インベントリのスロット横幅
    private int maxcol;
    private boolean flg_init;
    
    public EasyAIInterface mod;
    private EAI_Memory memory;
    private int count = 0;
    
    public EAI_Manager(EasyAIInterface mod) {
        this.mod = mod;
        this.memory = new EAI_Memory();
        
        this.slot_start = 0;
        
        this.flg_init = false;
    }
    
    public void init(EntityLiving entity, IInventory inventory, int slotnum, int maxcol) {
        this.init(entity, inventory, inventory, slotnum, maxcol);
    }
    
    public void init(EntityLiving entity, IInventory itemInventory, IInventory logicInventory, int slotnum, int maxcol) {
        this.parentEntity = entity;
        this.itemInventory = itemInventory;
        this.logicInventory = logicInventory;
        this.currentSlot = slotnum;
        this.maxcol = maxcol;
        this.flg_init = true;
    }
    
    public int getCurrentSlot() {
        return this.currentSlot;
    }
    
    public void setCurrentSlot(int slot) {
        if (0 <= slot && slot < this.logicInventory.getSizeInventory()) {
            this.currentSlot = slot;
        }
    }
    
    public boolean execute() {
        if (this.parentEntity.worldObj.isRemote) {
            return true;
        }
        if (!this.flg_init) {
            this.mod.debugPrint("[EAI_Manager] Not yet Initialized");
            return false; // abort
        }
        if (count > 0) {
            count--;
            return true; // continue
        } else {
            count = EasyAIInterface.loopWait;
        }
        
        if (this.currentSlot < 0 || this.logicInventory.getSizeInventory() < this.currentSlot) {
            this.mod.debugPrint("[EAI_Manager] slotnum is out of range. abort. : " + this.currentSlot);
            return false; // abort
        }
        
        //
        ItemStack currentSlotItem = this.logicInventory.getStackInSlot(this.currentSlot);
        
        // EAI_Item かどうかを確認。
        if (currentSlotItem == null) {
            this.mod.debugPrint("[EAI_Manager] currentSlotItem is null " + currentSlotItem);
            return false;
        } else if (!this.mod.items.isEAIItem(currentSlotItem)) {
            this.mod.debugPrint("[EAI_Manager] Item is not EAI_ITEM " + currentSlotItem.toString());
            return false; // abort
        }
        
        //
        EasyAIInterface.debugPrint("[EAI_Manager] execute");
        int ret = ((EAI_ItemBase) currentSlotItem.getItem()).execute(this, this.parentEntity, this.logicInventory, this.currentSlot, this.maxcol);
        
        // 終了前処理
        if (ret == -1) {
            this.mod.debugPrint("[EAI_Manager] returned : " + ret + " " + ((EAI_ItemBase) currentSlotItem.getItem()).getItemName());
            this.currentSlot = this.slot_start;
            return true; // manager が認識している start チップへ戻る
        } else if (ret > this.logicInventory.getSizeInventory()) {
            this.mod.debugPrint("[EAI_Manager] return to 0. reach right end.");
            this.currentSlot = this.slot_start;
            return true; // manager が認識している start チップへ戻る
        } else {
            this.currentSlot = ret;
            return true;
        }
    }
    
    public int findStartTip(IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && this.mod.items.isEAIItem(stack) && (stack.getItem() instanceof EAI_Item_SYS_start)) {
                return i;
            }
        }
        return -1;
    }
    
    // =====================================================
    // Target Helper Function
    //
    public Entity getTargetEntity() {
        return this.memory.getTargetEntity();
    }
    
    public void setTarget(Entity targetEntity) {
        this.memory.setTarget(targetEntity);
    }
    
    public Vec3 getTargetPos() {
        return this.memory.getTargetPos();
    }
    
    public void setTarget(double posX, double posY, double posZ) {
        this.memory.setTarget(posX, posY, posZ);
    }
    
    public void setTarget(Vec3 pos) {
        this.memory.setTarget(pos.xCoord, pos.yCoord, pos.zCoord);
    }
    
    public void clearTarget() {
        this.memory.clearTarget();
    }
    
    public boolean hasTarget() {
        return this.memory.hasTarget();
    }
    
    public boolean isEntity() {
        return this.memory.isEntity();
    }
    
}
