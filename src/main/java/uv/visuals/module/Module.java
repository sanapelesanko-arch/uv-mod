package uv.visuals.module;
import java.util.*;
public class Module {
  public final String name, category; public boolean enabled;
  public final Map<String, float[]> settings = new LinkedHashMap<>(); // name -> {value,min,max}
  public Module(String n, String c){ name=n; category=c; }
  public Module setting(String n, float v, float min, float max){ settings.put(n,new float[]{v,min,max}); return this; }
  public float get(String n){ return settings.get(n)[0]; }
  public void onEnable(){} public void onDisable(){} public void tick(net.minecraft.client.MinecraftClient mc){}
  public void toggle(){ enabled=!enabled; if(enabled) onEnable(); else onDisable(); }
}
