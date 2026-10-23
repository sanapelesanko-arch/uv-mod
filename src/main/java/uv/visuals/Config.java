package uv.visuals;
import com.google.gson.*; import java.nio.file.*; import java.util.*;
import uv.visuals.module.*;
public class Config {
  public static final Path DIR = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("uvvisuals");
  static final Gson G = new GsonBuilder().setPrettyPrinting().create();
  public static void save(String name){ try{ Files.createDirectories(DIR); JsonObject o=new JsonObject();
    for(Module m:Modules.ALL){ JsonObject j=new JsonObject(); j.addProperty("on",m.enabled);
      m.settings.forEach((k,v)->j.addProperty(k,v[0])); o.add(m.name,j);}
    Files.writeString(DIR.resolve(name+".json"),G.toJson(o)); }catch(Exception e){e.printStackTrace();} }
  public static void load(String name){ try{ Path f=DIR.resolve(name+".json"); if(!Files.exists(f)) return;
    JsonObject o=JsonParser.parseString(Files.readString(f)).getAsJsonObject();
    for(Module m:Modules.ALL){ if(!o.has(m.name)) continue; JsonObject j=o.getAsJsonObject(m.name);
      boolean on=j.get("on").getAsBoolean(); if(on!=m.enabled) m.toggle();
      m.settings.forEach((k,v)->{ if(j.has(k)) v[0]=j.get(k).getAsFloat(); }); }
  }catch(Exception e){e.printStackTrace();} }
  public static List<String> list(){ try{ Files.createDirectories(DIR); try(var s=Files.list(DIR)){ return s.map(p->p.getFileName().toString()).filter(n->n.endsWith(".json")).map(n->n.substring(0,n.length()-5)).sorted().toList(); } }catch(Exception e){return List.of();} }
}
