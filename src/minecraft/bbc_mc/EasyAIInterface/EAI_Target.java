package bbc_mc.EasyAIInterface;

import net.minecraft.src.Entity;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Vec3;

/**
 * 選択したターゲットの情報を保持するクラス
 * 
 * @author bbc_mc
 */
public class EAI_Target {
    
    private Vec3 targetPos;
    private Entity targetEntity;
    
    public EAI_Target() {
        this.targetPos = null;
        this.targetEntity = null;
    }
    
    public void setTarget(Entity targetEntity) {
        this.targetEntity = targetEntity;
        if (targetEntity != null) {
            this.targetPos = Vec3.createVectorHelper(targetEntity.posX, targetEntity.posY, targetEntity.posZ);
        } else {
            this.targetPos = null;
        }
    }
    
    public void setTarget(double posX, double posY, double posZ) {
        this.targetEntity = null;
        this.targetPos = Vec3.createVectorHelper(targetEntity.posX, targetEntity.posY, targetEntity.posZ);
    }
    
    public void clearTarget() {
        this.targetEntity = null;
        this.targetPos = null;
    }
    
    public Entity getTargetEntity() {
        return this.targetEntity;
    }
    
    public Vec3 getTargetPos() {
        return this.targetPos;
    }
    
    public boolean isEntity() {
        return (this.targetEntity != null && !this.targetEntity.isDead);
    }
    
    public boolean hasTarget() {
        return (this.targetEntity != null || this.targetPos != null);
    }
    
    public int getTargetPosBlockID() {
        if (!this.isEntity() && this.targetPos != null) {
            return ModLoader.getMinecraftInstance().theWorld.getBlockId((int) Math.round(this.targetPos.xCoord),
                    (int) Math.round(this.targetPos.yCoord), (int) Math.round(this.targetPos.zCoord));
        } else {
            return -1;
        }
    }
}
