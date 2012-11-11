package bbc_mc.EasyAIInterface.util;

/**
 * Util: Position
 *
 * @author bbc_mc
 * @date 2012/07/14 1.0
 * @date 2012/07/26 2.0_1.2.5
 * @date 2012/08/22 3.0_1.3.2
 * @date 2012/10/16 3.0_1.2.5
 * @date 2012/10/28 4.0
 * @date 2012/11/08 5.0 1.4.2 add setWorld, and add init function
 */

import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class UtilPosition {
    
    private static World world;
    public double posX;
    public double posY;
    public double posZ;
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public int iMeta;
    
    public String getVersion() {
        return "5.0";
    }
    
    public UtilPosition(int x, int y, int z) {
        setPosition(x, y, z);
        iMeta = 0;
        world = ModLoader.getMinecraftInstance().theWorld;
    }
    
    public UtilPosition(int x, int y, int z, int iMeta) {
        this(x, y, z);
        this.iMeta = iMeta;
    }
    
    public UtilPosition(int x, int y, int z, World world) {
        this(x, y, z);
        this.world = world;
    }
    
    public UtilPosition(double dx, double dy, double dz) {
        setPosition(dx, dy, dz);
        iMeta = 0;
        world = ModLoader.getMinecraftInstance().theWorld;
    }
    
    public UtilPosition(double dx, double dy, double dz, World world) {
        this(dx, dy, dz);
        this.world = world;
    }
    
    public UtilPosition(Entity entity) {
        this(entity.posX, entity.posY, entity.posZ, entity.worldObj);
    }
    
    public UtilPosition(Vec3 vec) {
        this(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public void setWorld(World world) {
        this.world = world;
    }
    
    public int getBlockId(World par1World) {
        return par1World.getBlockId(xCoord, yCoord, zCoord);
    }
    
    public TileEntity getBlockTileEntity(World par1World) {
        return par1World.getBlockTileEntity(xCoord, yCoord, zCoord);
    }
    
    /**
     * @param par1World
     * @return float BlockLightValue
     */
    public float getBlockLightValue(World par1World) {
        return par1World.getBlockLightValue(xCoord, yCoord, zCoord);
    }
    
    /**
     * Returns the linear distance to another path point
     */
    public float distanceTo(UtilPosition pos) {
        Vec3 vec = Vec3.createVectorHelper(posX - pos.posX, posY - pos.posY, posZ - pos.posZ);
        return (float) vec.lengthVector();
    }
    
    public float distanceToXZ(UtilPosition pos) {
        Vec3 vec = Vec3.createVectorHelper(posX - pos.posX, 0, posZ - pos.posZ);
        return (float) vec.lengthVector();
    }
    
    public float distanceToEntity(Entity entity) {
        Vec3 vec = Vec3.createVectorHelper(posX - entity.posX, posY - entity.posY, posZ - entity.posZ);
        return (float) vec.lengthVector();
    }
    
    public float distanceToEntityXZ(Entity entity) {
        Vec3 vec = Vec3.createVectorHelper(posX - entity.posX, 0, posZ - entity.posZ);
        return (float) vec.lengthVector();
    }
    
    public float distanceTo(double dx, double dy, double dz) {
        Vec3 vec = Vec3.createVectorHelper(posX - dx, posY - dy, posZ - dz);
        return (float) vec.lengthVector();
    }
    
    public float distanceTo(Vec3 vec2) {
        Vec3 vec = Vec3.createVectorHelper(posX - vec2.xCoord, posY - vec2.yCoord, posZ - vec2.zCoord);
        return (float) vec.lengthVector();
    }
    
    @Override
    public boolean equals(Object o) {
        UtilPosition var1 = (UtilPosition) o;
        if ((var1.posX == posX) && (var1.posY == posY) && (var1.posZ == posZ)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        Integer i_posX = new Integer(xCoord);
        Integer i_posY = new Integer(yCoord);
        Integer i_posZ = new Integer(zCoord);
        
        return i_posX.hashCode() + i_posY.hashCode() + i_posZ.hashCode();
    }
    
    @Override
    public String toString() {
        return toStringDouble();
    }
    
    public String toStringDouble() {
        return "" + this.posX + ", " + this.posY + ", " + this.posZ;
        // return (new StringBuilder()).append( posX ).append( ", " ).append(
        // posY ).append( ", " ).append( posZ
        // ).toString();
    }
    
    public String toStringInt() {
        return (new StringBuilder()).append(xCoord).append(", ").append(yCoord).append(", ").append(zCoord).toString();
    }
    
    /**
     * check par1block exist at this position
     * 
     * @param par1blockID
     * @return
     */
    public boolean checkBlockExistHere(int par1blockID) {
        if (world.getBlockId(xCoord, yCoord, zCoord) == par1blockID) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkBlockExistAround(int par1blockID) {
        if (world.getBlockId(xCoord + 1, yCoord, zCoord) == par1blockID) {
            return true;
        }
        if (world.getBlockId(xCoord - 1, yCoord, zCoord) == par1blockID) {
            return true;
        }
        if (world.getBlockId(xCoord, yCoord + 1, zCoord) == par1blockID) {
            return true;
        }
        if (world.getBlockId(xCoord, yCoord - 1, zCoord) == par1blockID) {
            return true;
        }
        if (world.getBlockId(xCoord, yCoord, zCoord + 1) == par1blockID) {
            return true;
        }
        if (world.getBlockId(xCoord, yCoord, zCoord - 1) == par1blockID) {
            return true;
        }
        return false;
    }
    
    public Vec3 searchNearestTargetBlock(World world, int targetBlockID, int rangeX, int rangeY, int rangeZ) {
        boolean initialized = false;
        Vec3 targetPos = Vec3.createVectorHelper(0, 0, 0);
        double tmpX, tmpY, tmpZ;
        for (int i = -rangeX; i < rangeX; i++) {
            tmpX = this.xCoord + i;
            for (int j = -rangeY; j < rangeY; j++) {
                tmpY = this.yCoord + j;
                for (int k = -rangeZ; k < rangeZ; k++) {
                    tmpZ = this.zCoord + k;
                    if (targetBlockID == world.getBlockId((int) Math.round(tmpX), (int) Math.round(tmpY), (int) Math.round(tmpZ))) {
                        double distance2Block = this.distanceTo(tmpX, tmpY, tmpZ);
                        if (!initialized || distance2Block < this.distanceTo(targetPos)) {
                            initialized = true;
                            targetPos = Vec3.createVectorHelper((int) Math.round(tmpX), (int) Math.round(tmpY), (int) Math.round(tmpZ));
                        }
                    }
                }
            }
        }
        return targetPos;
    }
    
    // get this position on Vec3D form
    public Vec3 getVec3() {
        return Vec3.createVectorHelper(posX, posY, posZ);
    }
    
    /**
     * get Vec3D from this pos to target pos.
     */
    public Vec3 getVec3toPos(UtilPosition target) {
        return Vec3.createVectorHelper(target.posX - posX, target.posY - posY, target.posZ - posZ);
    }
    
    /**
     * get Vec3D (this pos + vec).
     */
    public UtilPosition addVector(Vec3 vec) {
        return new UtilPosition(this.posX + vec.xCoord, this.posY + vec.yCoord, this.posZ + vec.zCoord);
    }
    
    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.xCoord = (int) Math.round(x);
        this.yCoord = (int) Math.round(y);
        this.zCoord = (int) Math.round(z);
    }
    
    public void setPosition(int x, int y, int z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.xCoord = (int) x;
        this.yCoord = (int) y;
        this.zCoord = (int) z;
    }
    
    public String getBiomeName() {
        return world.getWorldChunkManager().getBiomeGenAt(this.xCoord, this.zCoord).biomeName;
    }
    
    public int getWaterDepth() {
        int depth = 0;
        Material targetMaterial = null;
        for (int i = this.yCoord; i > 0; i--) {
            Material block = world.getBlockMaterial(this.xCoord, i, this.zCoord);
            if (depth == 0) {
                if (block == Material.water) {
                    targetMaterial = block;
                    depth++;
                } else if (block == Material.lava) {
                    targetMaterial = block;
                    depth++;
                }
            } else {
                if (block != null && block == targetMaterial) {
                    depth++;
                } else {
                    break;
                }
            }
        }
        return depth;
    }
    
    // =========================================================================
    // Class function
    //
    public static boolean checkBlockExistAround(int par1blockID, int xCoord, int yCoord, int zCoord) {
        UtilPosition tmp_pos = new UtilPosition(xCoord, yCoord, zCoord);
        return tmp_pos.checkBlockExistAround(par1blockID);
    }
    
    public static String getBiomeName(int x, int y, int z) {
        UtilPosition pos = new UtilPosition(x, y, z);
        return getBiomeName(pos);
    }
    
    public static String getBiomeName(UtilPosition pos) {
        return world.getWorldChunkManager().getBiomeGenAt(pos.xCoord, pos.zCoord).biomeName;
    }
}
