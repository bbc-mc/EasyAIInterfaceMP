package bbc_mc.EasyAIInterface.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class EAISounds {
    @ForgeSubscribe
    public void loadSounds(SoundLoadEvent var1) {
        /*
        SoundManager var2 = var1.manager;
        this.addSound(var2, "mob/eai/sound01.ogg", "sound01.ogg");
        */
    }
    /*
    public void addSound(SoundManager var1, String var2, String var3) {
        var1.soundPoolSounds.addSound(var2, EasyAIInterface.class.getResource("/EasyAIInterface/sounds/" + var3));
    }
    */
}
