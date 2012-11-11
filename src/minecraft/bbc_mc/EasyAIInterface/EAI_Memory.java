package bbc_mc.EasyAIInterface;

import java.util.Date;

import net.minecraft.src.Entity;
import net.minecraft.src.Vec3;

/**
 * AIが動作している Entity に生じたイベントの時刻や、ターゲット情報を保持するための管理クラス
 * 
 * TODO: EAI_Event のようなクラスが必要になるかもしれない。要検討
 * 
 * @author bbc_mc
 */
public class EAI_Memory {
    
    //
    private EAI_Target target;
    // Event record
    public Date EAI_event_onStart;
    public Date EAI_event_onDamege;
    public Date EAI_event_onEntityCollision;
    
    public EAI_Memory() {
        this.target = new EAI_Target();
    }
    
    public void setDateNow(Date eventDate) {
        eventDate = new Date();
    }
    
    public void setTarget(Entity targetEntity) {
        this.target.setTarget(targetEntity);
    }
    
    public void setTarget(double posX, double posY, double posZ) {
        this.target.setTarget(posX, posY, posZ);
    }
    
    public void clearTarget() {
        this.target.clearTarget();
    }
    
    public Entity getTargetEntity() {
        return this.target.getTargetEntity();
    }
    
    public Vec3 getTargetPos() {
        return this.target.getTargetPos();
    }
    
    public boolean hasTarget() {
        return this.target.hasTarget();
    }
    
    public boolean isEntity() {
        return this.target.isEntity();
    }
}
