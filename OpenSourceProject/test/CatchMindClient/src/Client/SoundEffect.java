package Client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
	BUTTON("Button.wav"),  //버튼 효과음
	SUC_LOGIN("Suc_Login.wav"),	//로그인 성공시 효과음
	FAIL_LOGIN("Fail_Login.wav"), //로그인 실패시 효과음
	BRUSH("Brush.wav"),			//색을 고르는 버튼 효과음
	COR_TURN("Cor_Turn.wav"),			//정답을 맞추고 턴 넘어갈 때의 효과음
	FAIL_TURN("Fail_Turn.wav"),			//정답을 못 맞추고 턴 넘어갈 때의 효과음
	LOGINMUSIC("LoginMusic.wav"), 	//로그인 음악
	WAIT("Wait.wav"),		//대기실 음악
	BEFOREGAME("Beforegame.wav"), //게임 준비 중 음악
	INGAME("Ingame.wav");			//게임 시작 음악
	
	public static final String MUSIC_URL = "MUSIC/";
	public static final String SUFFIX_TOOLBAR_BTN = ".wav";
	


	public static enum BGM_Volume {
		MUTE, LOW, MEDIUM, HIGH
	}
	public static enum Effect_Volume{
		MUTE, LOW, MEDIUM, HIGH
	}

	public static BGM_Volume b_volume = BGM_Volume.LOW;
	public static Effect_Volume e_volume = Effect_Volume.LOW;
	private Clip clip;
	//private Clip but_effect;

	SoundEffect(String soundFileName) {
		try {
			URL url = this.getClass().getClassLoader().getResource(soundFileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void BGM_play() {
		if (b_volume != BGM_Volume.MUTE) {
			if (clip.isRunning())
				clip.stop(); 
			clip.setFramePosition(0);
			clip.start();
			clip.loop(100);
		}
	}
	public void Effect_play(){
		if(e_volume!=Effect_Volume.MUTE){
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
			clip.loop(0);
		}
	}
	public static void init() {
		values();
	}
	public void stop() {
		clip.stop();
		
	}
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		Component event = (Component) e.getSource();
		
	}
		
	

}


