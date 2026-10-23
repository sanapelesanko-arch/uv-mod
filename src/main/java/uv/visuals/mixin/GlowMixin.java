package uv.visuals.mixin;
import net.minecraft.client.MinecraftClient; import net.minecraft.entity.Entity; import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uv.visuals.module.Modules;
@Mixin(Entity.class)
public class GlowMixin {
  @Inject(method="isGlowing", at=@At("RETURN"), cancellable=true)
  private void uv$glow(CallbackInfoReturnable<Boolean> cir){
    if(Modules.TARGET==null||!Modules.TARGET.enabled) return;
    var t=MinecraftClient.getInstance().crosshairTarget;
    if(t instanceof EntityHitResult r && r.getEntity()==(Object)this) cir.setReturnValue(true); } // клиентская подсветка цели
}
