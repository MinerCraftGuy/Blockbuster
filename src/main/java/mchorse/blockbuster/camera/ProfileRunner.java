package mchorse.blockbuster.camera;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Profile runner
 *
 * This class is responsible for running camera profiles (i.e. applying current's
 * fixture camera transformations on player).
 */
@SideOnly(Side.CLIENT)
public class ProfileRunner
{
    protected boolean isRunning = false;
    protected long startTime;
    protected long duration;

    protected CameraProfile profile;
    protected Position position = new Position(0, 0, 0, 0, 0);

    /* Profile access methods */

    public CameraProfile getProfile()
    {
        return this.profile;
    }

    public void setProfile(CameraProfile profile)
    {
        this.profile = profile;
    }

    public boolean isRunning()
    {
        return this.isRunning;
    }

    /* Playback methods (start/stop) */

    /**
     * Start the profile runner. This method also responsible for setting
     * important values before starting the run (like setting duration, and
     * setting the start time).
     */
    public void start()
    {
        if (!this.isRunning)
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/gamemode 3");
            MinecraftForge.EVENT_BUS.register(this);
        }

        this.isRunning = true;
        this.duration = this.profile.getDuration();
        this.startTime = System.currentTimeMillis();
    }

    public void stop()
    {
        if (this.isRunning)
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/gamemode 1");
            MinecraftForge.EVENT_BUS.unregister(this);
        }

        this.isRunning = false;
    }

    /**
     * The method that does the most exciting thing! This method is responsible
     * for applying interpolated fixture on position and apply the output from
     * fixture onto player.
     */
    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event)
    {
        if (event.phase == Phase.START) return;

        long progress = Math.min(System.currentTimeMillis() - this.startTime, this.duration);

        if (progress >= this.duration)
        {
            this.stop();
        }
        else
        {
            this.profile.applyProfile(progress, event.renderTickTime, this.position);

            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            Point point = this.position.point;
            Angle angle = this.position.angle;

            player.setLocationAndAngles(point.x, point.y, point.z, angle.yaw, angle.pitch);
            player.setPositionAndRotation(point.x, point.y, point.z, angle.yaw, angle.pitch);
            player.motionX = player.motionY = player.motionZ = 0;
        }
    }
}
