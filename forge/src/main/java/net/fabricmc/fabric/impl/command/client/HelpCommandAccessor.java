/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.command.client;

import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

/**
 * This class is from <a target="_blank" href="https://github.com/FabricMC/fabric">Fabric-api</a>
 * under the <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0">Apache-2.0 license</a>
 * modified slightly to work on forge by removing net.fabricmc.api.Environment and changing how the mixin is applied.
 */
public interface HelpCommandAccessor {

    SimpleCommandExceptionType getFailedException();
}