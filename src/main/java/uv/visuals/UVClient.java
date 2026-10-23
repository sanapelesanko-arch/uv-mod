package uv.visuals;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding; import net.minecraft.client.util.InputUtil; import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import uv.visuals.module.Modules; import uv.visuals.ui.GlassScreen;
public class UVClient implements ClientModInitializer {
  public static KeyBinding MENU;
  public void onInitializeClient(){
    Modules.init(); Music.scan(); Config.load("default");
    MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.uvvisuals.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
      KeyBinding.Category.create(Identifier.of("uvvisuals","main"))));
    ClientTickEvents.END_CLIENT_TICK.register(mc->{ Modules.tick(mc);
      while(MENU.wasPressed()) mc.setScreen(new GlassScreen()); });
    HudRenderCallback.EVENT.register((ctx,tick)->{
      if(Modules.MUSIC_TEXT.enabled && !Music.now.isEmpty()){ var tr=net.minecraft.client.MinecraftClient.getInstance().textRenderer;
        String t="♪ "+Music.now; ctx.drawTextWithShadow(tr,t,ctx.getScaledWindowWidth()-tr.getWidth(t)-6,6,0xFFB26BFF); } });
    HudRenderCallback.EVENT.register((ctx,tick)->{ if(!Modules.INFO_HUD.enabled) return; var mc=net.minecraft.client.MinecraftClient.getInstance(); if(mc.player==null) return;
      var tr=mc.textRenderer; ctx.drawTextWithShadow(tr,"UV | "+mc.getCurrentFps()+" FPS",6,6,0xFFB26BFF);
      ctx.drawTextWithShadow(tr,String.format("XYZ %.0f %.0f %.0f",mc.player.getX(),mc.player.getY(),mc.player.getZ()),6,18,0xFFFFFFFF); });
    Runtime.getRuntime().addShutdownHook(new Thread(()->Config.save("default")));
  }
}
