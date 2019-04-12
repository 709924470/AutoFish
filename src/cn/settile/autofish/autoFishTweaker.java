package cn.settile.autofish;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.launchwrapper.*;

public class autoFishTweaker implements ITweaker{

	private List<String> args;
	
	@Override
	public void acceptOptions(List<String> arg0, File arg1, File arg2, String arg3) {
		args = arg0 != null ? new ArrayList<String>(arg0) : new ArrayList<>();
	    if (arg1 != null)
	    {
	      this.args.add("--gameDir");
	      this.args.add(arg1.getAbsolutePath());
	    }
	    if (arg2 != null)
	    {
	      this.args.add("--assetsDir");
	      this.args.add(arg2.getAbsolutePath());
	    }
	    if (arg3 != null)
	    {
	      this.args.add("--version");
	      this.args.add(arg3);
	    }
	}

	@Override
	public String[] getLaunchArguments() {
	    return ((List<?>)Launch.blackboard.get("ArgumentList")).isEmpty() ? (String[])this.args.toArray(new String[this.args.size()]) : new String[0];
	}

	@Override
	public String getLaunchTarget() {
		return "net.minecraft.client.main.Main";
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader arg0) {
		arg0.addTransformerExclusion("cn.settile.");
		arg0.registerTransformer(autoFishTransformer.class.getName());
		LogWrapper.info("AutoFish 1.0 ", new String());
	}

}
