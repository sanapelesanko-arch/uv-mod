package uv.visuals.mixin;
import net.minecraft.entity.LivingEntity; import org.spongepowered.asm.mixin.Mixin; import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*; import uv.visuals.module.Modules;
@Mixin(LivingEntity.class)
public class SwingMixin {
  @Inject(method="getHandSwingDuration", at=@At("RETURN"), cancellable=true)
  private void uv$swing(CallbackInfoReturnable<Integer> cir){
    if(Modules.SWING!=null && Modules.SWING.enabled && (Object)this instanceof net.minecraft.client.network.ClientPlayerEntity)
      cir.setReturnValue(Math.max(1,Math.round(cir.getReturnValue()/Modules.SWING.get("Speed")))); }
}
