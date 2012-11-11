package bbc_mc.EasyAIInterface.util;

/**
 * Util: Inventory
 *
 * @version 1.0.0
 * @author bbc_mc
 * @date 2012/07/14
 * @date 2012/09/16 add searchItem
 */

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

/**
 * インベントリ操作用の Utility クラス
 * 
 * @author bbc_mc
 */
public class UtilInventory {
    
    /**
     * @param inventory
     * @param par1ItemStack
     * @return true when ItemStack is successfully added to inventory.
     * @return false when failed.
     */
    public static boolean addItemStackToInventory(IInventory inventory, ItemStack par1ItemStack) {
        
        if (par1ItemStack.isItemDamaged()) {
            int slotnum = getFirstEmptyStack(inventory);
            
            if (slotnum >= 0) {
                inventory.setInventorySlotContents(slotnum, ItemStack.copyItemStack(par1ItemStack));
                inventory.getStackInSlot(slotnum).animationsToGo = 5;
                par1ItemStack.stackSize = 0;
                return true;
            } else {
                return false;
            }
        } else {
            int stacksize;
            do {
                stacksize = par1ItemStack.stackSize;
                par1ItemStack.stackSize = storePartialItemStack(inventory, par1ItemStack);
            } while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < stacksize);
            
            return par1ItemStack.stackSize < stacksize;
        }
    }
    
    /**
     * general implementation of IInventory.decrStackSize.
     * 
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     * 
     * @param inventory
     * @param i
     * @param j
     * @return
     */
    public static ItemStack decrStackSize(IInventory inventory, int i, int j) {
        
        if (inventory.getStackInSlot(i) != null) {
            ItemStack itemstack;
            if (inventory.getStackInSlot(i).stackSize <= j) {
                itemstack = inventory.getStackInSlot(i);
                inventory.setInventorySlotContents(i, null);
            } else {
                itemstack = inventory.getStackInSlot(i).splitStack(j);
                if (inventory.getStackInSlot(i).stackSize == 0) {
                    inventory.setInventorySlotContents(i, null);
                }
            }
            inventory.onInventoryChanged();
            return itemstack;
        } else {
            return null;
        }
    }
    
    public static boolean dropItemSlot(IInventory inventory, int i, double x, double y, double z, double vx, double vy, double vz) {
        
        return true;
    }
    
    //
    // copied from "mcp62_1.2.5 ItemStack.java" for backward compatibility, (for in mc 1.3.2)
    //
    private static boolean func_46154_a(ItemStack par0ItemStack, ItemStack par1ItemStack) {
        if (par0ItemStack == null && par1ItemStack == null) {
            return true;
        }
        
        if (par0ItemStack == null || par1ItemStack == null) {
            return false;
        }
        
        if (par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null) {
            return false;
        }
        
        return par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound);
    }
    
    public static int getFirstEmptyStack(IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (inventory.getStackInSlot(i) == null) {
                return i;
            }
        }
        
        return -1;
    }
    
    public static ItemStack getStackInSlot(IInventory inventory, int i) {
        if (0 <= i && i < inventory.getSizeInventory()) {
            return inventory.getStackInSlot(i);
        } else {
            return null;
        }
    }
    
    /**
     * Get Inventory Usage(%) by slot. Do not consider about stacksize.
     * 
     * @param inventory
     * @return percentage of Inventory Usage.
     */
    public static float getUsageOfInventory(IInventory inventory) {
        int count = 0;
        int slotnum = inventory.getSizeInventory();
        for (int i = 0; i < slotnum; i++) {
            ItemStack items = inventory.getStackInSlot(i);
            if (items != null) {
                count++;
            }
        }
        return (float) count / slotnum;
    }
    
    // =====================================================
    // Subroutines for Inventory
    //
    private static int search(List<UtilPosition> list_search, List<UtilPosition> list, Entity baseEntity, double distance, int x, int y, int z,
            World world) {
        int ret = 0;
        UtilPosition pos = new UtilPosition(x, y, z);
        if (list_search.contains(pos)) {
            return 0;
        } else {
            list_search.add(pos);
        }
        if (baseEntity.getDistance(x, y, z) > distance) {
            return 0;
        }
        if (Block.chest.blockID == world.getBlockId(x, y, z)) {
            list.add(pos);
        } else if ((IInventory) world.getBlockTileEntity(x, y, z) != null) {
            list.add(pos);
        }
        ret += search(list_search, list, baseEntity, distance, x + 1, y, z, world);
        ret += search(list_search, list, baseEntity, distance, x - 1, y, z, world);
        // ret += search( list_search, list, baseEntity, distance, x, y + 1, z,
        // world
        // );
        // ret += search( list_search, list, baseEntity, distance, x, y - 1, z,
        // world
        // );
        ret += search(list_search, list, baseEntity, distance, x, y, z + 1, world);
        ret += search(list_search, list, baseEntity, distance, x, y, z - 1, world);
        return ret;
    }
    
    /**
     * 
     * @param list_search
     * @param list
     * @param baseEntity
     * @param distance
     * @param world
     * @return Num of searched block. ( <returned value> == list_search.size() )
     */
    public static int searchChest(List<UtilPosition> list_search, List<UtilPosition> list, Entity baseEntity, double distance, World world) {
        int ret = 0;
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX + 1, (int) baseEntity.posY, (int) baseEntity.posZ, world);
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX - 1, (int) baseEntity.posY, (int) baseEntity.posZ, world);
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX, (int) baseEntity.posY + 1, (int) baseEntity.posZ, world);
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX, (int) baseEntity.posY - 1, (int) baseEntity.posZ, world);
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX, (int) baseEntity.posY, (int) baseEntity.posZ + 1, world);
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX, (int) baseEntity.posY, (int) baseEntity.posZ - 1, world);
        
        ret += search(list_search, list, baseEntity, distance, (int) baseEntity.posX, (int) baseEntity.posY, (int) baseEntity.posZ, world);
        return ret;
    }
    
    /**
     * search target Item in Inventory, and return Slot number List<Integer>
     * 
     * @param inventory
     * @param item
     * @return List<Integer>
     */
    public static List searchItem(IInventory inventory, ItemStack par1ItemStack) {
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (inventory.getStackInSlot(i) != null) {
                ItemStack istk = inventory.getStackInSlot(i);
                if (istk.itemID == par1ItemStack.itemID) {
                    ret.add(i);
                }
            }
        }
        return ret;
    }
    
    private static int storeItemStack(IInventory inventory, ItemStack par1ItemStack) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (inventory.getStackInSlot(i) != null) {
                ItemStack istk = inventory.getStackInSlot(i);
                if (istk.itemID == par1ItemStack.itemID && istk.isStackable()) {
                    if (istk.stackSize < istk.getMaxStackSize() && istk.stackSize < inventory.getInventoryStackLimit()) {
                        if (!istk.getHasSubtypes() || istk.getItemDamage() == par1ItemStack.getItemDamage()) {
                            if (func_46154_a(istk, par1ItemStack)) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        
        return -1;
    }
    
    private static int storePartialItemStack(IInventory inventory, ItemStack par1ItemStack) {
        int itemid = par1ItemStack.itemID;
        int stacksize = par1ItemStack.stackSize;
        int slotnum;
        
        if (par1ItemStack.getMaxStackSize() == 1) {
            slotnum = getFirstEmptyStack(inventory);
            
            if (slotnum < 0) {
                return stacksize;
            } else {
                if (inventory.getStackInSlot(slotnum) == null) {
                    inventory.setInventorySlotContents(slotnum, ItemStack.copyItemStack(par1ItemStack));
                }
                
                return 0;
            }
        } else {
            slotnum = storeItemStack(inventory, par1ItemStack);
            
            if (slotnum < 0) {
                slotnum = getFirstEmptyStack(inventory);
            }
            
            if (slotnum < 0) {
                return stacksize;
            } else {
                if (inventory.getStackInSlot(slotnum) == null) {
                    inventory.setInventorySlotContents(slotnum, new ItemStack(itemid, 0, par1ItemStack.getItemDamage()));
                    
                    if (par1ItemStack.hasTagCompound()) {
                        inventory.getStackInSlot(slotnum).setTagCompound((NBTTagCompound) par1ItemStack.getTagCompound().copy());
                    }
                }
                
                int var5 = stacksize;
                
                if (stacksize > inventory.getStackInSlot(slotnum).getMaxStackSize() - inventory.getStackInSlot(slotnum).stackSize) {
                    var5 = inventory.getStackInSlot(slotnum).getMaxStackSize() - inventory.getStackInSlot(slotnum).stackSize;
                }
                
                if (var5 > inventory.getInventoryStackLimit() - inventory.getStackInSlot(slotnum).stackSize) {
                    var5 = inventory.getInventoryStackLimit() - inventory.getStackInSlot(slotnum).stackSize;
                }
                
                if (var5 == 0) {
                    return stacksize;
                } else {
                    stacksize -= var5;
                    inventory.getStackInSlot(slotnum).stackSize += var5;
                    inventory.getStackInSlot(slotnum).animationsToGo = 5;
                    return stacksize;
                }
            }
        }
    }
}
