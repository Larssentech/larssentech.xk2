package org.larssentech.xkomm2.core.obj.version;

import org.larssentech.fx.embed.FxEmbedParams;
import org.larssentech.xkomm.core.obj.version.Version4Xk1;

public interface Version4Xk2 extends Version4Xk1 {

	String J_VERSION = "J3.0.7";

	String K_VERSION = "--";

	String ABOUT_VERSION = "XK2J: " + J_VERSION +

			" - XK2: v" + Version4Xk2.BASE_VERSION_STRING +

			" - FX: " + FxEmbedParams.VERSION + " #20251217";
}