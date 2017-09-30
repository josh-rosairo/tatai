package tatai;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SpeechHandler {
	// Speech Recognition Output
	private List<String> _maoriRecogOutput;
	// English Translations for each Maori Number from 1 to 10
	private static List<String> _maoriNumTranslations = Arrays.asList("tahi","rua", "toru", "whaa", "rima", "ono", "whitu", "waru", "iwa", "tekau");

	// Filename base.
	final static private String FILENAMEBASE = "recording";
	
	public SpeechHandler() {
		
	}
	
	// Executes some bash command received as an argument.
	private static List<String> executeCommand(String cmd) {
		// Read output from the bash.
		List<String> output = new ArrayList<>();
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			while ((line = stdoutBuffered.readLine()) != null ) {
				output.add(line);
			}
		} catch (Exception e) {
			
		}
		return output;
	}
	
	public static void recordAndProcess() {
		// Remove any previous recording files so overwrite prompt doesn't hold up thread
		executeCommand("rm "+FILENAMEBASE+".wav ");
		executeCommand("rm "+FILENAMEBASE+".mp3 ");
		// Record the user with ffmpeg.
		executeCommand("ffmpeg -f alsa -ar 22050 -i default -loglevel quiet -t 3 "+FILENAMEBASE+".wav ");
		// Run HVite and write output to file
		executeCommand("mv " + FILENAMEBASE + ".wav /home/se206/Documents/HTK/MaoriNumbers/");
		executeCommand("cd /home/se206/Documents/HTK/MaoriNumbers/; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList " + FILENAMEBASE + ".wav ");
		// convert wav file to mp3
		executeCommand("cd -; ffmpeg -loglevel quiet -i "+FILENAMEBASE+".wav -f mp3 "+FILENAMEBASE+".mp3");
	}
	
	public static boolean isRecordingCorrect(int numToSay) {
		
		boolean isCorrect = false;
		
    	// Get HVite Speech Recognition Output as a list of strings
    	List<String> _maoriRecogOutput = executeCommand("cd /home/se206/Documents/HTK/MaoriNumbers/; cat recout.mlf");
    	executeCommand("cd -");
    	Iterator<String> it = _maoriRecogOutput.iterator();
    	
    	
    	String result = "";
    	// Iterate through the Output Strings, determining if recording matches correct Maori Pronunciation of Number
    	while(it.hasNext()){
    		
    		try {
    			// Get pronunciation output between silences
    			String str = it.next();
	        	result = str;
        		while (!(str = it.next()).equals("sil")) {
        			result = result + " " + str;
        		}
        	}
        	catch (NoSuchElementException e) {
        		break;
        	}
        	
    		String maoriAnswer;
    		String prefix;
    		String suffix;
    		
    		// Converts a number to Maori.

    		if (numToSay <= 10) {
    			maoriAnswer = _maoriNumTranslations.get(numToSay-1);
    		}
    		else if (numToSay%10 == 0) {
    			prefix = _maoriNumTranslations.get((numToSay/10)-1);
    			maoriAnswer = prefix + " tekau";
    		}
    		else {
    			prefix = _maoriNumTranslations.get((numToSay/10)-1);
    			suffix = _maoriNumTranslations.get((numToSay%10)-1);
    			maoriAnswer = prefix + " tekau maa " + suffix;
    		}
    		
    		
    		if (result.equals(maoriAnswer)) {
    			return true;
    		}
    		
  		}
    	
    	return isCorrect;
	}

}
