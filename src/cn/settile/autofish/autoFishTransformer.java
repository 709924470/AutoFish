package cn.settile.autofish;

import java.io.IOException;
import java.net.URL;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LogWrapper;

public class autoFishTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		if(arg2 == null)
			return null;
		if(arg0.equals("dgr")) {
			LogWrapper.finest("Captured SoundClass: %s.class!", arg0);
			URL cls = getClass().getResource("/dgr_modified");
			try {
				byte[] b = new byte[cls.openStream().available()];
				cls.openStream().read(b);
				return b;
			} catch (IOException e) {
				LogWrapper.severe("Error while handling %s.class", arg0);
				e.printStackTrace();
			}
		}
		if(arg0.equals("cft$1")) {
			LogWrapper.finest("Captured minecraft main class: %s.class!", arg0);
			URL cls = getClass().getResource("/cft_modified");
			try {
				byte[] b = new byte[cls.openStream().available()];
				cls.openStream().read(b);
				return b;
			} catch (IOException e) {
				LogWrapper.severe("Error while handling %s.class", arg0);
				e.printStackTrace();
			}
		}
		return arg2;
	}
}
