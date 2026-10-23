package uv.visuals.mixin;
import net.minecraft.client.render.item.HeldItemRenderer; import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uv.visuals.module.Modules;
@Mixin(HeldItemRenderer.class)
public class HandMixin {
  // Если сигнатура renderFirstPersonItem в 1.21.11 отличается, поправь параметры под Yarn (см. README).
  @ModifyVariable(method="renderFirstPersonItem", at=@At("HEAD"), argsOnly=true)
  private MatrixStack uv$hands(MatrixStack m){
    if(Modules.HANDS!=null && Modules.HANDS.enabled){ var h=Modules.HANDS;
      m.translate(h.get("X"),h.get("Y"),h.get("Z")); float s=h.get("Scale"); m.scale(s,s,s); }
    return m; }
}
