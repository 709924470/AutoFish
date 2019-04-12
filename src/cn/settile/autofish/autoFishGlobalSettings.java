package cn.settile.autofish;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class autoFishGlobalSettings {
	public static boolean globalSetting = false;
	public static Object minecraft = null;
	
	public static void isBobberSound(Object soundString_) {
		String soundString = soundString_.toString();
		globalSetting = soundString != null ? soundString.equals("minecraft:entity.fishing_bobber.splash") : false;
	}
	
	public static void setMinecraftInstance(Object o) {
		if(minecraft != null)
			return;
		minecraft = o;
		listenAndReel();
	}
	
	private static void listenAndReel() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(autoFishGlobalSettings.globalSetting) {
						autoFishGlobalSettings.globalSetting = false;
						Class<?> mc = autoFishGlobalSettings.minecraft.getClass();
						try {
							Field e = mc.getField("e");
							Class<?> crf = e.getType();
							for(Method ms: crf.getMethods()) {
								if(ms.getName().equals("a")) {
									Class<?>[] clz = ms.getParameterTypes();
									if(clz.length == 3 && ms.getReturnType().getName().contains("adm")) {
										String clzs = "aog axy adk";
										if(clzs.contains(clz[0].getName()) && clzs.contains(clz[1].getName()) && 
												clzs.contains(clz[2].getName())) {
											Field ctj = mc.getField("i");
											Object i = ctj.get(autoFishGlobalSettings.minecraft);
											Field crg = mc.getField("g");
											Object g = crg.get(autoFishGlobalSettings.minecraft);
											ms.invoke(e.get(autoFishGlobalSettings.minecraft), i, g, clz[2].getEnumConstants()[0]);
											ms.invoke(e.get(autoFishGlobalSettings.minecraft), i, g, clz[2].getEnumConstants()[0]);
										}
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//t.setDaemon(true);
		t.setName("ListenToIt");
		t.start();
	}
}
