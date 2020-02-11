package com;



import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

import java.io.IOException;

public class SpeechToText 
{
	public String getWord() throws IOException
	{
		String word = "Sorry Cant Recgnize";
		
		  //Configuration Object
        Configuration configuration = new Configuration();

        // Set path to the acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to the dictionary.
        configuration.setDictionaryPath("en_us.dic");
        // Set path to the language model.
        configuration.setLanguageModelPath("en-us.lm.bin");
        
        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);
        
        //Start Recognition Process (The bool parameter clears the previous cache if true)
        recognize.startRecognition(true);
        
        //Creating SpeechResult object
        SpeechResult result;
        
        //Check if recognizer recognized the speech
        while ((result = recognize.getResult()) != null) {
            
            //Get the recognized speech
            for(WordResult r:result.getWords())
            {
            		String w = ""+r;
            		String[] w1 = w.split(",");
                     System.out.println(""+w1[0]);
                     word = w1[0];
            }
         /*   for (String s : result.getNbest(1))
                System.out.println(s);
           */
        }
		
		
		return word;
	}

}
