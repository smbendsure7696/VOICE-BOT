package com;

import com.sun.speech.freetts.*;


public class TextToSpeech 
{
	
	public void speak(String word)
	{
		Voice v = VoiceManager.getInstance().getVoice("kevin16");
		v.allocate();
		
		v.speak(""+word);
	}
		
}
