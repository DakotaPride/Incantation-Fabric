package net.dakotapride.incantation.config.modmenu;

/*
       Copyright 2022 nu11une
       Modified By 2022 DakotaPride

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.dakotapride.incantation.config.IncantationConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenu implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // Modified Below Line --> Edited Config Class Name File To Match With Incantation's Config File
        return screen -> AutoConfig.getConfigScreen(IncantationConfig.class, screen).get();
    }
}
