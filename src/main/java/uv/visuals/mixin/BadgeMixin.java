package uv.visuals.mixin;
import net.minecraft.client.MinecraftClient; import net.minecraft.entity.player.PlayerEntity; import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Mixin; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uv.visuals.module.Modules;
@Mixin(PlayerEntity.class)
public class BadgeMixin {
  // Фиолетовый бейдж UV перед ником. Показывается у тебя; для других игроков с модом нужен свой сервер/список.
  @Inject(method="getDisplayName", at=@At("RETURN"), cancellable=true)
  private void uv$badge(CallbackInfoReturnable<Text> cir){
    if(Modules.BADGE==null||!Modules.BADGE.enabled) return;
    var me=MinecraftClient.getInstance().player; if(me==null||(Object)this!=me) return;
    cir.setReturnValue(Text.literal("UV ").setStyle(Style.EMPTY.withColor(0xB26BFF).withBold(true)).append(cir.getReturnValue())); }
}
