# UV Visuals (Fabric 1.21.11)
Сборка онлайн: залей папку на GitHub (репозиторий) -> вкладка Actions -> build -> скачай артефакт uv-visuals-jar. Локально: JDK 21 + gradle build.
Если нет gradle wrapper: создай шаблон на https://fabricmc.net/develop/template/ (1.21.11), и скопируй туда `src/` и `fabric.mod.json`.
Версии в gradle.properties проверь на fabricmc.net/develop.
Меню: Right Shift. Свап элитры: B (настройка Bind(GLFW) в меню, 66 = B).
Музыка: кладёшь свои .wav в config/uvvisuals/music, выбираешь Prev/Play/Next в меню.
Конфиги: config/uvvisuals/*.json. Код не компилировался в моей среде: возможны мелкие правки сигнатур Yarn (HandMixin, Click, KeyBinding.Category).
