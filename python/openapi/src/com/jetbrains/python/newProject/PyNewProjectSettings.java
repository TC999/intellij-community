/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.python.newProject;

import com.intellij.openapi.projectRoots.Sdk;
import org.jetbrains.annotations.Nullable;

/**
 * Project generation settings selected on the first page of the new project dialog.
 *
 * @author catherine
 * @deprecated Use {@link com.jetbrains.python.newProjectWizard}
 */
@Deprecated(forRemoval = true)
public class PyNewProjectSettings {
  private Sdk mySdk;
  /**
   * Path on remote server for remote project
   */
  @Nullable
  private String myRemotePath;

  @Nullable
  private Object myInterpreterInfoForStatistics;

  @Nullable
  public final Sdk getSdk() {
    return mySdk;
  }

  public final void setSdk(@Nullable final Sdk sdk) {
    mySdk = sdk;
  }

  public final void setRemotePath(@Nullable final String remotePath) {
      myRemotePath = remotePath;
  }

  public final void setInterpreterInfoForStatistics(@Nullable Object interpreterInfoForStatistics) {
    myInterpreterInfoForStatistics = interpreterInfoForStatistics;
  }

  @Nullable
  public final Object getInterpreterInfoForStatistics() {
    return myInterpreterInfoForStatistics;
  }

  @Nullable
  public final String getRemotePath() {
    return myRemotePath;
  }
}
