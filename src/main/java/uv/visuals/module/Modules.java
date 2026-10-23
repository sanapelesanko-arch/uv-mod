package uv.visuals.module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import java.util.*;
public class Modules {
  public static final List<Module> ALL = new ArrayList<>();
  public static Module FULLBRIGHT, INFO_HUD, HALO, AUTO_SPRINT, ELYTRA_SWAP, SWING, HANDS, TARGET, OPTIMIZE, BADGE, MUSIC_TEXT;
  static boolean bWas;
  public static void init(){
    AUTO_SPRINT = add(new Module("AutoSprint","Movement"){ public void tick(MinecraftClient mc){
      if(mc.player!=null && mc.player.forwardSpeed>0 && !mc.player.isSneaking() && !mc.player.horizontalCollision) mc.player.setSprinting(true);}});
    ELYTRA_SWAP = add(new Module("ElytraSwap","Movement").setting("Bind(GLFW)",66,32,348)); // 66 = B
    SWING = add(new Module("SwingAnimation","Visual").setting("Speed",1f,0.3f,3f));
    HANDS = add(new Module("Hands","Visual").setting("X",0f,-1f,1f).setting("Y",0f,-1f,1f).setting("Z",0f,-1f,1f).setting("Scale",1f,0.5f,1.5f));
    TARGET = add(new Module("TargetHighlight","Visual"));
    OPTIMIZE = add(new Module("Optimization","Performance"){ public void onEnable(){
      MinecraftClient mc=MinecraftClient.getInstance(); var o=mc.options; if(o==null) return;
      o.getParticles().setValue(ParticlesMode.MINIMAL); o.getBiomeBlendRadius().setValue(1);
      o.getEntityDistanceScaling().setValue(0.75); o.getFov(); o.getMipmapLevels().setValue(2); o.write(); }});
    BADGE = add(new Module("UV Badge","Cosmetic"));
    MUSIC_TEXT = add(new Module("MusicText","Music"));
    FULLBRIGHT = add(new Module("Fullbright","Visual"){ double old=1; public void onEnable(){ old=MinecraftClient.getInstance().options.getGamma().getValue(); MinecraftClient.getInstance().options.getGamma().setValue(15.0);} public void onDisable(){ MinecraftClient.getInstance().options.getGamma().setValue(old);} });
    INFO_HUD = add(new Module("InfoHUD","HUD"));
    HALO = add(new Module("HaloCosmetic","Cosmetic").setting("Radius",0.5f,0.2f,1.2f){ public void tick(MinecraftClient mc){
      if(mc.player==null||mc.world==null||mc.player.age%2!=0) return; double a=mc.player.age*0.35, r=get("Radius");
      mc.world.addParticleClient(net.minecraft.particle.ParticleTypes.END_ROD, mc.player.getX()+Math.cos(a)*r, mc.player.getY()+mc.player.getHeight()+0.25, mc.player.getZ()+Math.sin(a)*r, 0,0,0);} });
    AUTO_SPRINT.enabled=true; BADGE.enabled=true;
  }
  static Module add(Module m){ ALL.add(m); return m; }
  public static void tick(MinecraftClient mc){
    for(Module m:ALL) if(m.enabled) m.tick(mc);
    if(ELYTRA_SWAP.enabled && mc.player!=null && mc.currentScreen==null){
      boolean down = org.lwjgl.glfw.GLFW.glfwGetKey(mc.getWindow().getHandle(),(int)ELYTRA_SWAP.get("Bind(GLFW)"))==1;
      if(down && !bWas) swapElytra(mc); bWas=down; }
  }
  static void swapElytra(MinecraftClient mc){
    var p=mc.player; var inv=p.getInventory(); boolean wearing=p.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
    for(int i=0;i<36;i++){ var s=inv.getStack(i);
      boolean match = wearing ? (s.getItem().toString().contains("chestplate")) : s.isOf(Items.ELYTRA);
      if(match){ int slot = i<9? i+36 : i; // слот в контейнере игрока
        mc.interactionManager.clickSlot(p.playerScreenHandler.syncId,slot,0,SlotActionType.PICKUP,p);
        mc.interactionManager.clickSlot(p.playerScreenHandler.syncId,6,0,SlotActionType.PICKUP,p);
        mc.interactionManager.clickSlot(p.playerScreenHandler.syncId,slot,0,SlotActionType.PICKUP,p); return; } }
  }
}
