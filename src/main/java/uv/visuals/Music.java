package uv.visuals;
import javax.sound.sampled.*; import java.io.File; import java.nio.file.*; import java.util.*;
/** Играет ТВОИ .wav файлы из config/uvvisuals/music (без музыки из чужих проектов). */
public class Music {
  public static List<File> tracks=new ArrayList<>(); public static int idx=-1; static Clip clip; public static String now="";
  public static void scan(){ tracks.clear(); try{ Path d=Config.DIR.resolve("music"); Files.createDirectories(d);
    try(var s=Files.list(d)){ s.filter(p->p.toString().toLowerCase().endsWith(".wav")).sorted().forEach(p->tracks.add(p.toFile())); } }catch(Exception e){} }
  public static void play(int i){ if(tracks.isEmpty()) return; stop(); idx=Math.floorMod(i,tracks.size());
    try{ clip=AudioSystem.getClip(); clip.open(AudioSystem.getAudioInputStream(tracks.get(idx))); clip.loop(Clip.LOOP_CONTINUOUSLY); clip.start();
      String n=tracks.get(idx).getName(); now=n.substring(0,n.length()-4);}catch(Exception e){now="ошибка: "+e.getMessage();} }
  public static void stop(){ if(clip!=null){clip.stop();clip.close();clip=null;} now=""; }
}
