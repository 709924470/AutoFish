package cn.settile.autofish.Setup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;

import javax.swing.*;

public class Main {
	public static JTextField jta;
	public static JButton jb1;
	public static String pth;
	
	public static void main(String[] args) throws Exception {
		CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
		pth = new File(codeSource.getLocation().toURI().getPath()).getAbsolutePath();
		//JOptionPane.showMessageDialog(null, "����!\n" + pth,  "����",JOptionPane.ERROR_MESSAGE);
		JFrame f = new JFrame("AutoFish Installer");
		JPanel p = new JPanel(new GridLayout(0,1,0,1));
		p.add(new JLabel("ѡ��Ҫ��װ����Minecraft 1.13.2��.minecraft�ļ���: "));
		jta = new JTextField();
		jta.setEnabled(false);
		JButton jb = new JButton("ѡ���ļ���");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser f = new JFileChooser();
		        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		        f.showSaveDialog(null);
		        String pth = f.getSelectedFile().getAbsolutePath();
		        if(!Main.isValidFolder(pth)) {
		        	JOptionPane.showMessageDialog(null, "������Ч���ļ���!", "����", JOptionPane.ERROR_MESSAGE);
		        	return;
		        }
		        jta.setText(pth);
				jb1.setEnabled(true);
			}
		});
		jb1 = new JButton("��װ������汾");
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				launcher l = Main.launcherType(jta.getText());
				//JOptionPane.showMessageDialog(null, "l="+l.name(),  "����",JOptionPane.ERROR_MESSAGE);
				if(l == launcher.vanilla) {
					JOptionPane.showMessageDialog(null, "������ԭ��(����)MC��������װ�Զ�����MOD", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					Main.install(jta.getText(), l);
				}else if(l == launcher.multimc) {
					JOptionPane.showMessageDialog(null, "������MultiMC��������װ�Զ�����MOD", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					Main.install(jta.getText(), l);
				}else {
					JOptionPane.showMessageDialog(null, "������Ч���ļ���!",  "����",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		jb1.setEnabled(false);
		JPanel p2 = new JPanel(new BorderLayout(0, 6));
	    p2.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 12));
	    p2.add(p, "Center");
	    p2.add(jb1, "Last");
		p.add(jta);
		p.add(jb);
		f.add(p2);
		f.setResizable(false);
		f.setAlwaysOnTop(true);
		f.setContentPane(p2);
		f.setSize(700, 212);
		f.setVisible(true);
	}
	
	public static void install(String path, launcher l) {
		if(l == launcher.vanilla) {
			try {
				if(pth == null)
					throw new IOException("null");
				copy(pth, path.endsWith("/") || path.endsWith("\\") ? path + "libraries\\cn\\settile\\autofish\\1.0" : path + "\\libraries\\cn\\settile\\autofish\\1.0");
				File json = new File(path.endsWith("/") || path.endsWith("\\") ? path + "versions\\1.13.2-AutoFish\\1.13.2-AutoFish.json" : path + "\\versions\\1.13.2-AutoFish\\1.13.2-AutoFish.json");
				json.createNewFile();
				String s = "{\r\n" + 
						"  \"id\": \"1.13.2-AutoFish\",\r\n" + 
						"  \"time\": \"2019-04-03T14:05:56+01:00\",\r\n" + 
						"  \"releaseTime\": \"2019-04-08T06:32:00Z\",\r\n" + 
						"  \"type\": \"release\",\r\n" + 
						"  \"arguments\": {\r\n" + 
						"    \"game\": [\r\n" + 
						"      \"--tweakClass\",\r\n" + 
						"      \"cn.settile.autofish.autoFishTweaker\"\r\n" + 
						"    ]\r\n" + 
						"  },\r\n" + 
						"  \"libraries\": [\r\n" + 
						"    {\r\n" + 
						"      \"name\": \"cn.settile:autofish:1.0\"\r\n" + 
						"    },\r\n" + 
						"    {\r\n" + 
						"      \"name\": \"net.minecraft:launchwrapper:1.12\"\r\n" + 
						"    }\r\n" + 
						"  ],\r\n" + 
						"  \"mainClass\": \"net.minecraft.launchwrapper.Launch\",\r\n" + 
						"  \"minimumLauncherVersion\": 21,\r\n" + 
						"  \"jar\": \"1.13.2\",\r\n" + 
						"  \"inheritsFrom\": \"1.13.2\"\r\n" + 
						"}";
				FileOutputStream fos = new FileOutputStream(json);
				fos.write(s.getBytes());
				fos.close();
				JOptionPane.showMessageDialog(null, "��װ���",  "��ʾ",JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "����!\n" + e.getLocalizedMessage(),  "����",JOptionPane.ERROR_MESSAGE);
			}
		}else if(l == launcher.multimc) {
			path = new File(path).getParent();
			try {
				if(pth == null)
					throw new IOException("null");
				copy(pth, path.endsWith("/") || path.endsWith("\\") ? path + "libraries" : path + "\\libraries");
				File json = new File(path.endsWith("/") || path.endsWith("\\") ? path + "patches\\1.13.2-AutoFish.json" : path + "\\patches\\1.13.2-AutoFish.json");
				json.createNewFile();
				String s = "{\r\n" + 
						"    \"+tweakers\": [\r\n" + 
						"        \"cn.settile.autofish.autoFishTweaker\"\r\n" + 
						"    ],\r\n" + 
						"    \"formatVersion\": 1,\r\n" + 
						"    \"libraries\": [\r\n" + 
						"        {\r\n" + 
						"            \"name\": \"net.minecraft:launchwrapper:1.12\"\r\n" + 
						"        },\r\n" + 
						"        {\r\n" + 
						"            \"MMC-filename\": \"autofish-1.0.jar\",\r\n" + 
						"            \"MMC-hint\": \"local\",\r\n" + 
						"            \"name\": \"cn.settile:autofish:1.0\"\r\n" + 
						"        }\r\n" + 
						"    ],\r\n" + 
						"    \"mainClass\": \"net.minecraft.launchwrapper.Launch\",\r\n" + 
						"    \"name\": \"autoFish 1.0\",\r\n" + 
						"    \"requires\": [\r\n" + 
						"        {\r\n" + 
						"            \"equals\": \"1.13.2\",\r\n" + 
						"            \"uid\": \"net.minecraft\"\r\n" + 
						"        }\r\n" + 
						"    ],\r\n" + 
						"    \"uid\": \"cn.settile.autofish\",\r\n" + 
						"    \"version\": \"1.0\"\r\n" + 
						"}";
				FileOutputStream fos = new FileOutputStream(json);
				fos.write(s.getBytes());
				fos.close();
				JOptionPane.showMessageDialog(null, "��װ���",  "��ʾ",JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "����!\n" + e.getStackTrace(),  "����",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@SuppressWarnings("resource")
	private static void copy(String src, String dest) throws Exception {
		if(!Files.exists(Paths.get(src), NOFOLLOW_LINKS))
			throw new IOException("src is not valid"); 
		Path sourceDirectory = Paths.get(src);
        Path targetDirectory = Paths.get(dest);
        File dst = new File(dest);
        if(!dst.exists())
        	dst.mkdirs();
        Files.copy(sourceDirectory, targetDirectory.resolve(sourceDirectory.getFileName()), StandardCopyOption.REPLACE_EXISTING);

    }
	
	public static boolean isValidFolder(String path) {
		File f = new File(path);
		if(!f.getName().equals(".minecraft")) {
			return false;
		}
		return true;
	}
	
	public static launcher launcherType(String path){
		File f = new File(path);
		File pf = f.getParentFile();
		for(File fs: pf.listFiles()) {
			if(fs.getName().equals("instance.cfg"))
				return launcher.multimc;
		}
		for(File fs: f.listFiles()) {
			if(fs.getName().equals("versions")) {
				if(fs.isDirectory()) {
					return launcher.vanilla;
				}
			}
		}
		return launcher.other;
	}
	
	public enum launcher{
		vanilla,
		multimc,
		other;
	}
}
