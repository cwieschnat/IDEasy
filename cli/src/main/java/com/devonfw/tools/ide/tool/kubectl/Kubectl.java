package com.devonfw.tools.ide.tool.kubectl;

import com.devonfw.tools.ide.common.Tag;
import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.log.IdeLogLevel;
import com.devonfw.tools.ide.tool.GlobalToolCommandlet;
import com.devonfw.tools.ide.tool.docker.Docker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class Kubectl extends GlobalToolCommandlet {
  /**
   * The constructor.
   *
   * @param context the {@link IdeContext}.
   */
  public Kubectl(IdeContext context) {

    super(context, "kubectl", Set.of(Tag.KUBERNETES));
  }

  @Override
  public boolean install(boolean silent) {

    // no installation for kubectl itself, delegate installation to Docker without calling super.install(silent)
    if (doIsKubernetesInstalled() && !this.context.isForceMode()) {
      IdeLogLevel level = silent ? IdeLogLevel.DEBUG : IdeLogLevel.INFO;
      this.context.level(level).log("{} is already installed at {}", this.tool, this.context.getPath().findBinary(Path.of(getBinaryName())));
      return false;
    }
    return getCommandlet(Docker.class).install();
  }

  private boolean doIsKubernetesInstalled() {

    Path binaryPath = this.context.getPath().findBinary(Path.of(getBinaryName()));
    return binaryPath != null && Files.exists(binaryPath);
  }
}
