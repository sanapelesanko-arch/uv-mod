package uv.visuals.ui;
import net.minecraft.client.MinecraftClient; import net.minecraft.client.gui.DrawContext; import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen; import net.minecraft.client.gui.PlayerSkinDrawer; import net.minecraft.text.Text;
import uv.visuals.*; import uv.visuals.module.*; import java.util.*;
/** Полупрозрачное "стеклянное" меню. Только fill/gradient через DrawContext => совместимо с VulkanMod. */
public class GlassScreen extends Screen {
  int px,py,pw=360,ph=230; List<int[]> hit=new ArrayList<>(); List<Runnable> act=new ArrayList<>();
  public GlassScreen(){ super(Text.literal("UV Visuals")); }
  @Override public boolean shouldPause(){ return false; }
  @Override public void renderBackground(DrawContext c,int mx,int my,float d){} // без размытия фона
  @Override public void render(DrawContext c,int mx,int my,float d){
    px=(width-pw)/2; py=(height-ph)/2; hit.clear(); act.clear();
    c.fillGradient(px,py,px+pw,py+ph,0x66FFFFFF,0x33B26BFF);              // стекло
    c.fill(px,py,px+pw,py+1,0xAAFFFFFF); c.fill(px,py,px+1,py+ph,0x88FFFFFF); // блик краёв
    c.fill(px,py+ph-1,px+pw,py+ph,0x55FFFFFF); c.fill(px+pw-1,py,px+pw,py+ph,0x55FFFFFF);
    var mc=MinecraftClient.getInstance();
    if(mc.player!=null) PlayerSkinDrawer.draw(c,mc.player.getSkinTextures(),px+8,py+6,16);   // скин Microsoft-аккаунта
    c.drawTextWithShadow(textRenderer,"UV Visuals  |  "+mc.getSession().getUsername(),px+30,py+11,0xFFD9B8FF);
    int y=py+30;
    for(Module m:Modules.ALL){
      int x=px+8; boolean on=m.enabled;
      c.fill(x,y,x+120,y+14,on?0x99B26BFF:0x44000000);
      c.drawTextWithShadow(textRenderer,m.name,x+4,y+3,0xFFFFFFFF);
      hit.add(new int[]{x,y,120,14}); act.add(m::toggle);
      int sx=px+135;
      for(var e:m.settings.entrySet()){ float[] v=e.getValue(); if(sx>px+pw-60) break;
        c.drawTextWithShadow(textRenderer,e.getKey()+" "+String.format("%.2f",v[0]),sx,y+3,0xFFDDDDDD);
        int bx=sx; float[] vv=v; hit.add(new int[]{bx,y,55,14}); act.add(()->{ vv[0]=Math.min(vv[2],vv[0]+(vv[2]-vv[1])/10f); if(vv[0]>=vv[2]-1e-4) vv[0]=vv[1]; });
        sx+=62; }
      y+=17; }
    // конфиги и музыка
    int by=py+ph-22; int bx=px+8;
    bx=btn(c,bx,by,"Save",()->Config.save("cfg1")); bx=btn(c,bx,by,"Load",()->Config.load("cfg1"));
    bx=btn(c,bx,by,"Prev",()->Music.play(Music.idx-1)); bx=btn(c,bx,by,"Play",()->Music.play(Math.max(0,Music.idx)));
    bx=btn(c,bx,by,"Next",()->Music.play(Music.idx+1)); bx=btn(c,bx,by,"Stop",Music::stop);
    c.drawTextWithShadow(textRenderer,"Tracks: "+Music.tracks.size()+"  cfgs: "+Config.list(),px+8,py+ph-36,0xFFCCCCCC);
  }
  int btn(DrawContext c,int x,int y,String t,Runnable r){ c.fill(x,y,x+48,y+14,0x66000000); c.drawCenteredTextWithShadow(textRenderer,t,x+24,y+3,0xFFFFFFFF);
    hit.add(new int[]{x,y,48,14}); act.add(r); return x+52; }
  @Override public boolean mouseClicked(Click click,boolean dbl){
    for(int i=0;i<hit.size();i++){ int[] h=hit.get(i); if(click.x()>=h[0]&&click.x()<h[0]+h[2]&&click.y()>=h[1]&&click.y()<h[1]+h[3]){ act.get(i).run(); return true; } }
    return super.mouseClicked(click,dbl); }
  @Override public void close(){ Config.save("default"); super.close(); }
}
